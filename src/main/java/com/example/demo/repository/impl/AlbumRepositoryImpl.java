package com.example.demo.repository.impl;

import com.example.demo.model.Album;
import com.example.demo.repository.AlbumRepository;
import com.example.demo.repository.mapper.AlbumRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumRepositoryImpl implements AlbumRepository {
    private final JdbcTemplate jdbcTemplate;
    private final AlbumRowMapper albumRowMapper;

    public AlbumRepositoryImpl(JdbcTemplate jdbcTemplate, AlbumRowMapper albumRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.albumRowMapper = albumRowMapper;
    }

    @Override
    public List<Album> getAllAlbum(int page, int size) {
        int sanitizedPage = Math.max(page, 0);
        int sanitizedSize = Math.max(size, 1);
        int offset = sanitizedPage * sanitizedSize;

        String sql = """
                SELECT AlbumId, Title, ArtistId
                FROM Album
                ORDER BY AlbumId
                OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                """;
        return jdbcTemplate.query(sql, albumRowMapper, offset, sanitizedSize);
    }
}
