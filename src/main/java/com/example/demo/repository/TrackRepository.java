package com.example.demo.repository;

import com.example.demo.dto.TrackWithAlbumDto;
import com.example.demo.model.Track;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrackRepository {

	long countTracks();

	List<Track> selectTracksPage(
			@Param("offset") long offset,
			@Param("limit") int limit,
			@Param("sortKey") String sortKey,
			@Param("sortAscending") boolean sortAscending);

	Track selectTrackById(@Param("id") Long id);

	void insertTrack(Track track);

	void updateTrack(@Param("id") Long id, @Param("track") Track track);

	void deleteTrack(@Param("id") Long id);

	String selectAlbumTitleByAlbumId(@Param("albumId") Long albumId);

	List<TrackWithAlbumDto> selectTracksWithAlbumJoin(
			@Param("offset") long offset,
			@Param("limit") int limit,
			@Param("sortKey") String sortKey,
			@Param("sortAscending") boolean sortAscending);
}
