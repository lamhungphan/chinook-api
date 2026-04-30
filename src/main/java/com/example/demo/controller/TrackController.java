package com.example.demo.controller;

import com.example.demo.model.Track;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.TrackService;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {
	private final TrackService trackService;

	public TrackController(TrackService trackService) {
		this.trackService = trackService;
	}

	@GetMapping
	public Page<Track> getAllTrack(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "trackId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		return trackService.getAllTrack(pageRequest);
	}

	@GetMapping("/{id}")
	public Track getTrackById(@PathVariable Long id) {
		return trackService.getTrackById(id);
	}

	@PostMapping()
	public void createTrack(@RequestBody Track track) {
		trackService.createTrack(track);
	}

	@PutMapping("/{id}")
	public void updateTrack(@PathVariable Long id, @RequestBody Track track) {
		trackService.updateTrack(id, track);
	}

	@DeleteMapping("/{id}")
	public void deleteTrack(@PathVariable Long id) {
		trackService.deleteTrack(id);
	}
}
