package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TimedResponse<T>(
		@JsonProperty("data") T data,
		@JsonProperty("elapsed_ms") long elapsedMs,
		@JsonProperty("mode") String mode,
		@JsonProperty("thread_count") int threadCount) {
}
