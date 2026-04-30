package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "Track")
public class Track {
    @Id
    @Column(name = "TrackId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    @Column(name = "Name")
    private String name;

    @Column(name = "AlbumId")
    private Long albumId;

    @Column(name = "MediaTypeId")
    private Long mediaTypeId;

    @Column(name = "GenreId")
    private Long genreId;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "Milliseconds")
    private Long milliseconds;

    @Column(name = "Bytes")
    private Long bytes;

    @Column(name = "UnitPrice")
    private Double unitPrice;

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
