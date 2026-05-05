package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TrackPriceLogDto(
		@JsonProperty("log_id") Long logId,
		@JsonProperty("track_id") Long trackId,
		@JsonProperty("old_price") BigDecimal oldPrice,
		@JsonProperty("new_price") BigDecimal newPrice,
		@JsonProperty("change_date") LocalDateTime changeDate) {
}
