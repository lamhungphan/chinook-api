package com.example.demo.dto;

import java.math.BigDecimal;

public record ArtistRevenueDto(
		String artistName,
		BigDecimal totalRevenue,
		Long tracksSold,
		BigDecimal avgRevenuePerTrack) {
}
