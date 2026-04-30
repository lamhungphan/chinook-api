package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "MediaType")
public class MediaType {
    @Id
    @Column(name = "MediaTypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaTypeId;

    @Column(name = "Name")
    private String name;

	public Long getMediaTypeId() {
		return mediaTypeId;
	}

	public void setMediaTypeId(Long mediaTypeId) {
		this.mediaTypeId = mediaTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
