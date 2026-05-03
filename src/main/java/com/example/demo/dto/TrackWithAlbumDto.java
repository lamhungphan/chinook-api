package com.example.demo.dto;

public record TrackWithAlbumDto(
		Long trackId,
		String name,
		Long albumId,
		String albumTitle,
		Long mediaTypeId,
		Long genreId,
		String composer,
		Long milliseconds,
		Long bytes,
		Double unitPrice) {
}
