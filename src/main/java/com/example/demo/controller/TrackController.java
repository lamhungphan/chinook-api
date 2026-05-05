package com.example.demo.controller;

import com.example.demo.dto.TimedResponse;
import com.example.demo.dto.TrackWithAlbumDto;
import com.example.demo.model.Track;
import com.example.demo.service.TrackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		return trackService.getAllTrack(buildPageable(page, size, sortBy, sortDir));
	}

	@GetMapping("/n-plus-one")
	public Page<TrackWithAlbumDto> getTracksNPlusOne(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "trackId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		return trackService.getTracksNPlusOne(buildPageable(page, size, sortBy, sortDir));
	}

	@GetMapping("/optimized")
	public Page<TrackWithAlbumDto> getTracksOptimized(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "trackId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		return trackService.getTracksOptimized(buildPageable(page, size, sortBy, sortDir));
	}

	@GetMapping("/n-plus-one/single-thread")
	public TimedResponse<Page<TrackWithAlbumDto>> getTracksNPlusOneSingleThread(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "trackId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		return trackService.getTracksNPlusOneSingleThread(buildPageable(page, size, sortBy, sortDir));
	}

	@GetMapping("/n-plus-one/multi-thread")
	public TimedResponse<Page<TrackWithAlbumDto>> getTracksNPlusOneMultiThread(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size,
			@RequestParam(defaultValue = "trackId") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir) {
		return trackService.getTracksNPlusOneMultiThread(buildPageable(page, size, sortBy, sortDir));
	}

	@GetMapping("/{id:\\d+}")
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

	private Pageable buildPageable(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc")
				? Sort.by(sortBy).descending()
				: Sort.by(sortBy).ascending();
		return PageRequest.of(page, size, sort);
	}
}
