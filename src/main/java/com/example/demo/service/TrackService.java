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
		var sort = resolveSort(pageable);
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<Track> content = trackRepository.selectTracksPage(offset, limit, sort.sortKey(), sort.ascending());
		return new PageImpl<>(content, pageable, total);
	}

	public Page<TrackWithAlbumDto> getTracksNPlusOne(Pageable pageable) {
		long total = trackRepository.countTracks();
		var sort = resolveSort(pageable);
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<Track> tracks = trackRepository.selectTracksPage(offset, limit, sort.sortKey(), sort.ascending());

		List<TrackWithAlbumDto> dtos = new ArrayList<>(tracks.size());
		for (Track t : tracks) {
			String albumTitle = trackRepository.selectAlbumTitleByAlbumId(t.getAlbumId());
			dtos.add(toDto(t, albumTitle));
		}
		return new PageImpl<>(dtos, pageable, total);
	}

	public Page<TrackWithAlbumDto> getTracksOptimized(Pageable pageable) {
		long total = trackRepository.countTracks();
		var sort = resolveSort(pageable);
		long offset = pageable.getOffset();
		int limit = pageable.getPageSize();
		List<TrackWithAlbumDto> content = trackRepository.selectTracksWithAlbumJoin(
				offset, limit, sort.sortKey(), sort.ascending());
		return new PageImpl<>(content, pageable, total);
	}

	public Track getTrackById(Long id) {
		Track track = trackRepository.selectTrackById(id);
		if (track == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		return track;
	}

	public void createTrack(Track track) {
		trackRepository.insertTrack(track);
	}

	public void updateTrack(Long id, Track track) {
		if (trackRepository.selectTrackById(id) == null) {
			throw new NoSuchElementException("Track not found with id: " + id);
		}
		trackRepository.updateTrack(id, track);
	}

	public void deleteTrack(Long id) {
		trackRepository.deleteTrack(id);
	}

	private static TrackSort resolveSort(Pageable pageable) {
		Sort sort = pageable.getSort();
		Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.asc("trackId"));
		String prop = order.getProperty();
		boolean ascending = order.getDirection().isAscending();
		String sortKey = mapSortPropertyToKey(prop);
		return new TrackSort(sortKey, ascending);
	}

	private static String mapSortPropertyToKey(String property) {
		return switch (property) {
			case "trackId", "name", "albumId", "mediaTypeId", "genreId",
					"composer", "milliseconds", "bytes", "unitPrice" -> property;
			default -> "trackId";
		};
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

	private record TrackSort(String sortKey, boolean ascending) {
	}
}
