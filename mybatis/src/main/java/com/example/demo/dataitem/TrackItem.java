package com.example.demo.dataitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackItem {
	private Long trackId;
	private String name;
	private Long albumId;
	private Long mediaTypeId;
	private Long genreId;
	private String composer;
	private Long milliseconds;
	private Long bytes;
	private Double unitPrice;
}
