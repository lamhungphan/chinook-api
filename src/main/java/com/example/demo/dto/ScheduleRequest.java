package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleRequest(
		@JsonProperty(value = "genre_name", required = true)
		@JsonAlias({"genreName"})
		String genreName,

		@JsonProperty(value = "percentage", required = true)
		Double percentage,

		@JsonProperty(value = "cron", required = true)
		String cron) {
}
