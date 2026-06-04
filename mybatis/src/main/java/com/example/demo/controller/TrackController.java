package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dataitem.TrackItem;
import com.example.demo.service.TrackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracks")
public class TrackController {

	private final TrackService service;

	@GetMapping
	public Page<TrackItem> selectTrackList(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) throws Exception {
		return service.selectTrackList(buildPageable(page, size));
	}

	@GetMapping("/{id}")
	public TrackItem selectTrackById(@PathVariable Long id) throws Exception {
		return service.selectTrackById(id);
	}

	@PostMapping()
	public void insertTrack(@RequestBody TrackItem parm) throws Exception {
		service.insertTrack(parm);
	}

	@PutMapping("/{id}")
	public void updateTrack(@PathVariable Long id, @RequestBody TrackItem parm) throws Exception {
		service.updateTrack(id, parm);
	}

	@DeleteMapping("/{id}")
	public void deleteTrack(@PathVariable Long id) {
		service.deleteTrack(id);
	}

	private Pageable buildPageable(int page, int size) {
		return PageRequest.of(page, size);
	}
}
