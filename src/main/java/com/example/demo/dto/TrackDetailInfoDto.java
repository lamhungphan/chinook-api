package com.example.demo.dto;

public record TrackDetailInfoDto(
		String trackName,
		String genreName,
		Long milliseconds,
		Long durationRank,
		String formattedDuration) {
}
