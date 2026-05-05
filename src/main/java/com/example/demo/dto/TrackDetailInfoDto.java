package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrackDetailInfoDto(
		@JsonProperty("track_name") String trackName,
		@JsonProperty("genre_name") String genreName,
		@JsonProperty("milliseconds") Long milliseconds,
		@JsonProperty("duration_rank") Long durationRank,
		@JsonProperty("formatted_duration") String formattedDuration) {
}
