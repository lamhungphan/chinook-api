package com.example.demo.repository;

import com.example.demo.model.Artist;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistRepository {
    private final JdbcTemplate jdbcTemplate;

    public ArtistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Artist> getAllArtist() {
        String sql = "SELECT ArtistId, Name FROM Artist ORDER BY ArtistId";
        RowMapper<Artist> artistRowMapper = (rs, rowNum) ->
                new Artist(rs.getLong("ArtistId"), rs.getString("Name"));

        return jdbcTemplate.query(sql, artistRowMapper);
    }
}
