package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CustomerOrderDto(
		@JsonProperty("full_name") String fullName,
		@JsonProperty("country") String country,
		@JsonProperty("total_spent") BigDecimal totalSpent) {
}
