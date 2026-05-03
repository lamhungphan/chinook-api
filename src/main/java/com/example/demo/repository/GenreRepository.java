package com.example.demo.repository;

import com.example.demo.model.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepository {
	private final JdbcTemplate jdbcTemplate;

	private static final RowMapper<Genre> ROW_MAPPER = (rs, rowNum) -> new Genre(
			rs.getLong("GenreId"),
			rs.getString("Name"));

	public GenreRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Genre> getAllGenre() {
		return jdbcTemplate.query("SELECT GenreId, Name FROM Genre ORDER BY GenreId", ROW_MAPPER);
	}

	public Genre getGenreById(Long id) {
		try {
			return jdbcTemplate.queryForObject(
					"SELECT GenreId, Name FROM Genre WHERE GenreId = ?", ROW_MAPPER, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void createGenre(Genre genre) {
		jdbcTemplate.update("INSERT INTO Genre (Name) VALUES (?)", genre.getName());
	}

	public void updateGenre(Long id, Genre genre) {
		jdbcTemplate.update("UPDATE Genre SET Name = ? WHERE GenreId = ?", genre.getName(), id);
	}

	public void deleteGenre(Long id) {
		jdbcTemplate.update("DELETE FROM Genre WHERE GenreId = ?", id);
	}

	public List<Genre> searchGenre(String keyword) {
		return jdbcTemplate.query(
				"SELECT GenreId, Name FROM Genre WHERE Name LIKE ? ORDER BY GenreId",
				ROW_MAPPER,
				"%" + keyword + "%");
	}
}
