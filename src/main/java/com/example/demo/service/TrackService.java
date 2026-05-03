package com.example.demo.service;

import com.example.demo.dto.TrackWithAlbumDto;
import com.example.demo.model.Track;
import com.example.demo.repository.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TrackService {
	private final TrackRepository trackRepository;

	public TrackService(TrackRepository trackRepository) {
		this.trackRepository = trackRepository;
	}

	public Page<Track> getAllTrack(Pageable pageable) {
		long total = trackRepository.countTracks();
		var sortOrder = resolveSort(pageable);
		int offset = (int) pageable.getOffset();
		int limit = pageable.getPageSize();
		List<Track> content = trackRepository.findTracksPage(offset, limit, sortOrder.column(), sortOrder.ascending());
		return new PageImpl<>(content, pageable, total);
	}

	public Page<TrackWithAlbumDto> getTracksNPlusOne(Pageable pageable) {
		long total = trackRepository.countTracks();
		var sortOrder = resolveSort(pageable);
		int offset = (int) pageable.getOffset();
		int limit = pageable.getPageSize();
		List<Track> tracks = trackRepository.findTracksPage(offset, limit, sortOrder.column(), sortOrder.ascending());

		List<TrackWithAlbumDto> dtos = new ArrayList<>(tracks.size());
		for (Track t : tracks) {
			String albumTitle = trackRepository.findAlbumTitleByAlbumId(t.getAlbumId());
			dtos.add(toDto(t, albumTitle));
		}
		return new PageImpl<>(dtos, pageable, total);
	}

	public Page<TrackWithAlbumDto> getTracksOptimized(Pageable pageable) {
		long total = trackRepository.countTracks();
		var sortOrder = resolveSort(pageable);
		int offset = (int) pageable.getOffset();
		int limit = pageable.getPageSize();
		List<TrackWithAlbumDto> content = trackRepository.findTracksWithAlbumJoin(
				offset, limit, sortOrder.column(), sortOrder.ascending());
		return new PageImpl<>(content, pageable, total);
	}

	public Track getTrackById(Long id) {
		Track track = trackRepository.findById(id);
		if (track == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		return track;
	}

	public void createTrack(Track track) {
		trackRepository.insert(track);
	}

	public void updateTrack(Long id, Track track) {
		if (trackRepository.findById(id) == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		trackRepository.update(id, track);
	}

	public void deleteTrack(Long id) {
		trackRepository.deleteById(id);
	}

	private static TrackSort resolveSort(Pageable pageable) {
		Sort sort = pageable.getSort();
		Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.asc("trackId"));
		String sqlColumn = TrackRepository.resolveTrackSortColumn(order.getProperty());
		boolean asc = order.getDirection().isAscending();
		return new TrackSort(sqlColumn, asc);
	}

	private static TrackWithAlbumDto toDto(Track t, String albumTitle) {
		return new TrackWithAlbumDto(
				t.getTrackId(),
				t.getName(),
				t.getAlbumId(),
				albumTitle,
				t.getMediaTypeId(),
				t.getGenreId(),
				t.getComposer(),
				t.getMilliseconds(),
				t.getBytes(),
				t.getUnitPrice());
	}

	private record TrackSort(String column, boolean ascending) {
	}
}
