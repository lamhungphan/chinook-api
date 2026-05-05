package com.example.demo.dto;

public record UpdateGenrePriceRequest(
		String genreName,
		Double percentage) {
}
