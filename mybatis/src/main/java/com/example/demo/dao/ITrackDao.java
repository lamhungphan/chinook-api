package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dataitem.TrackItem;

@Mapper
public interface ITrackDao {

	long countTrackItems();

	List<TrackItem> selectTrackList(@Param("offset") long offset, @Param("limit") int limit);

	TrackItem selectTrackById(@Param("id") Long id);

	void insertTrack(TrackItem TrackItem);

	void updateTrack(@Param("id") Long id, @Param("TrackItem") TrackItem TrackItem);

	void deleteTrack(@Param("id") Long id);
}
