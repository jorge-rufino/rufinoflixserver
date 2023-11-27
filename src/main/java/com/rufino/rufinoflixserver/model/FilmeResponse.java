package com.rufino.rufinoflixserver.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilmeResponse {

	@JsonProperty("results")
	private List<Filme> results;
}
