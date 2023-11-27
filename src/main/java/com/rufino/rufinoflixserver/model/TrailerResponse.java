package com.rufino.rufinoflixserver.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrailerResponse {

	@JsonProperty("results")
	private List<Trailer> results;
}
