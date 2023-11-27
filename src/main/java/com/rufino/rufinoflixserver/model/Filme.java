package com.rufino.rufinoflixserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"id", "title", "posterPath", "keyYoutube"})
public class Filme {

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("poster_path")
	private String posterPath;
	
	private String urlTrailer;
	
}
