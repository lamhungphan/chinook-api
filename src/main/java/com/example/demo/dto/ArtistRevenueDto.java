package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ArtistRevenueDto(
		@JsonProperty("artist_name") String artistName,
		@JsonProperty("total_revenue") BigDecimal totalRevenue,
		@JsonProperty("tracks_sold") Long tracksSold,
		@JsonProperty("avg_revenue_per_track") BigDecimal avgRevenuePerTrack) {
}
