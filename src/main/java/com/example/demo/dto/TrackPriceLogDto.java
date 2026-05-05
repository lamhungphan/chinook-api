package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TrackPriceLogDto(
		Long logId,
		Long trackId,
		BigDecimal oldPrice,
		BigDecimal newPrice,
		LocalDateTime changeDate) {
}
