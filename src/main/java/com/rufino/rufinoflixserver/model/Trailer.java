package com.rufino.rufinoflixserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Trailer {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("key")
	private String key;
	
	@JsonProperty("site")
	private String site;
	
	@JsonProperty("type")
	private String type;
}
