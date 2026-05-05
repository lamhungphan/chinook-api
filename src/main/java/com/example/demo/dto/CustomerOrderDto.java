package com.example.demo.dto;

import java.math.BigDecimal;

public record CustomerOrderDto(
		String fullName,
		String country,
		BigDecimal totalSpent) {
}
