package com.example.demo.service;

import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    private static final int ALBUM_PAGE_SIZE = 20;
    private final AlbumRepository albumRepository;

    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<Album> getAllAlbum(int page) {
        return albumRepository.getAllAlbum(page, ALBUM_PAGE_SIZE);
    }
}
