package com.rufino.rufinoflixserver.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rufino.rufinoflixserver.model.Filme;
import com.rufino.rufinoflixserver.model.FilmeResponse;
import com.rufino.rufinoflixserver.model.Trailer;
import com.rufino.rufinoflixserver.model.TrailerResponse;

@Service
public class FilmeService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment environment;
	
	private final String PREFIXO_YOUTUBE = "https://www.youtube.com/watch?v=";

	public ResponseEntity<List<Filme>> carregarDadosDaApi() {
		String url = environment.getProperty("url.api.token");

		// create headers
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		var token = "Bearer %s".formatted(environment.getProperty("token"));
		headers.set("Authorization",token);

		// build the request
		HttpEntity request = new HttpEntity(headers);

		// make the GET request
		ResponseEntity<FilmeResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, FilmeResponse.class);
		
		// response
        if (response.getStatusCode().is2xxSuccessful()) {
            
            List<Filme> listaDeFilmes = response.getBody().getResults();
            carregarTrailersDaApi(listaDeFilmes);
            
            return ResponseEntity.ok(listaDeFilmes);
        } else {
            return ResponseEntity.notFound().build();
        }
	}
	
	public void carregarTrailersDaApi(List<Filme> filmes) {
		
		for (int i = 0 ; i < filmes.size(); i++) {
			int idTrailer = filmes.get(i).getId();
			
			String url = "https://api.themoviedb.org/3/movie/" +idTrailer + "/videos?language=pt-BR";
			
			// create headers
			HttpHeaders headers = new HttpHeaders();
			
			// set `Content-Type` and `Accept` headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			// example of custom header
			var token = "Bearer %s".formatted(environment.getProperty("token"));
			headers.set("Authorization",token);
			
			// build the request
			HttpEntity request = new HttpEntity(headers);
			
			// make an HTTP GET request with headers
			ResponseEntity<TrailerResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, TrailerResponse.class);
			
			// check response
			if (response.getStatusCode().is2xxSuccessful()) {	
				
				List<Trailer> listaDeTrailers = response.getBody().getResults();
				//filmes.get(i).setKeysYoutube(listaDeTrailers);
				
				for (int j = 0; j < listaDeTrailers.size(); j++) {
					if (listaDeTrailers.get(j).getType().equals("Trailer")) {
						
						String urlTrailer = PREFIXO_YOUTUBE.concat(listaDeTrailers.get(j).getKey());
						
						filmes.get(i).setUrlTrailer(urlTrailer);
					}
				}
				
			} else {
				throw new RuntimeException("Problema no servidor da API.");
			}			
		}
	}
}
