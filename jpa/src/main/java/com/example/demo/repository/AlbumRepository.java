package com.example.demo.repository;

import com.example.demo.model.Album;

import java.util.List;

public interface AlbumRepository {
    List<Album> getAllAlbum(int page, int size);
}
