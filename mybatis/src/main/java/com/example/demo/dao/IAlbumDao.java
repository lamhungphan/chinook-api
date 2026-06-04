package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.dataitem.AlbumItem;

@Mapper
public interface IAlbumDao {
	List<AlbumItem> selectAlbumList();

	AlbumItem selectAlbumById(@Param("id") Long id);

	void insertAlbum(AlbumItem albumItem);

	void updateAlbum(@Param("id") Long id, @Param("AlbumItem") AlbumItem albumItem);

	void deleteAlbum(@Param("id") Long id);

}
