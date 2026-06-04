package com.example.demo.repository.mapper;

import com.example.demo.model.Album;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Album(
                rs.getLong("AlbumId"),
                rs.getString("Title"),
                rs.getLong("ArtistId")
        );
    }
}
