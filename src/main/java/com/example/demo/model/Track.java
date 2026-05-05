package com.example.demo.model;

public class Track {
	private Long trackId;
	private String name;
	private Long albumId;
	private Long mediaTypeId;
	private Long genreId;
	private String composer;
	private Long milliseconds;
	private Long bytes;
	private Double unitPrice;

	public Track() {
	}

	public Long getTrackId() {
		return trackId;
	}

	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Long getMediaTypeId() {
		return mediaTypeId;
	}

	public void setMediaTypeId(Long mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}

	public Long getGenreId() {
		return genreId;
	}

	public void setGenreId(Long genreId) {
		this.genreId = genreId;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public Long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public Long getBytes() {
		return bytes;
	}

	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
}
