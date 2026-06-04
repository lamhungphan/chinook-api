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
		RowMapper<Artist> artistRowMapper = (rs, rowNum) -> new Artist(rs.getLong("ArtistId"), rs.getString("Name"));

		return jdbcTemplate.query(sql, artistRowMapper);
	}

	public Artist getArtistById(Long id) {
		String sql = "SELECT ArtistId, Name FROM Artist WHERE ArtistId = ?";
		RowMapper<Artist> mapper = (rs, rowNum) -> new Artist(rs.getLong("ArtistId"), rs.getString("Name"));
		return jdbcTemplate.queryForObject(sql, mapper, id);
	}

	public void createArtist(Artist artist) {
		String sql = "INSERT INTO Artist (Name) VALUES (?)";
		jdbcTemplate.update(sql, artist.getName());
	}

	public void updateArtist(Long id, Artist artist) {
		String sql = "UPDATE Artist SET Name = ? WHERE ArtistId = ?";
		jdbcTemplate.update(sql, artist.getName(), id);
	}

	public void deleteArtist(Long id) {
		String sql = "DELETE FROM Artist WHERE ArtistId = ?";
		jdbcTemplate.update(sql, id);
	}

	public List<Artist> searchArtist(String keyword) {
		String sql = "SELECT ArtistId, Name FROM Artist WHERE Name LIKE ? ORDER BY ArtistId";
		RowMapper<Artist> artistRowMapper = (rs, rowNum) -> new Artist(rs.getLong("ArtistId"), rs.getString("Name"));

		return jdbcTemplate.query(sql, artistRowMapper, "%" + keyword + "%");
	}
	
	public List<Artist> getArtistByGenre(String keyword) {
		String sql = "SELECT DISTINCT a2.ArtistId, a2.Name\n"
				+ "FROM Album a \n"
				+ "JOIN Artist a2 ON a.ArtistId = a2.ArtistId \n"
				+ "JOIN Track t ON t.AlbumId  = a.AlbumId \n"
				+ "JOIN Genre g ON t.GenreId = g.GenreId \n"
				+ "WHERE g.Name = ?"
				+ "";
		RowMapper<Artist> mapper = (rs, rowNum) -> new Artist(rs.getLong("ArtistId"), rs.getString("Name"));

		return jdbcTemplate.query(sql, mapper, keyword);
	}

}
