package com.example.demo.repository;

import com.example.demo.dto.TrackWithAlbumDto;
import com.example.demo.model.Track;
import com.example.demo.repository.mapper.TrackRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class TrackRepository {
	private static final Map<String, String> TRACK_SORT_COLUMNS = new HashMap<>();

	static {
		TRACK_SORT_COLUMNS.put("trackId", "TrackId");
		TRACK_SORT_COLUMNS.put("name", "Name");
		TRACK_SORT_COLUMNS.put("albumId", "AlbumId");
		TRACK_SORT_COLUMNS.put("mediaTypeId", "MediaTypeId");
		TRACK_SORT_COLUMNS.put("genreId", "GenreId");
		TRACK_SORT_COLUMNS.put("composer", "Composer");
		TRACK_SORT_COLUMNS.put("milliseconds", "Milliseconds");
		TRACK_SORT_COLUMNS.put("bytes", "Bytes");
		TRACK_SORT_COLUMNS.put("unitPrice", "UnitPrice");
	}

	private final JdbcTemplate jdbcTemplate;
	private final TrackRowMapper trackRowMapper;

	public TrackRepository(JdbcTemplate jdbcTemplate, TrackRowMapper trackRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.trackRowMapper = trackRowMapper;
	}

	public long countTracks() {
		Long n = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Track", Long.class);
		return n != null ? n : 0L;
	}

	public List<Track> findTracksPage(int offset, int limit, String sortColumnSql, boolean ascending) {
		String dir = ascending ? "ASC" : "DESC";
		String sql = """
				SELECT TrackId, Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice
				FROM Track
				ORDER BY %s %s
				OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
				""".formatted(sortColumnSql, dir);
		return jdbcTemplate.query(sql, trackRowMapper, offset, limit);
	}

	public Track findById(Long id) {
		try {
			String sql = """
					SELECT TrackId, Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice
					FROM Track WHERE TrackId = ?
					""";
			return jdbcTemplate.queryForObject(sql, trackRowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public long insert(Track track) {
		String sql = """
				INSERT INTO Track (Name, AlbumId, MediaTypeId, GenreId, Composer, Milliseconds, Bytes, UnitPrice)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?)
				""";
		var keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, track.getName());
			ps.setObject(2, track.getAlbumId());
			ps.setObject(3, track.getMediaTypeId());
			ps.setObject(4, track.getGenreId());
			ps.setString(5, track.getComposer());
			ps.setObject(6, track.getMilliseconds());
			ps.setObject(7, track.getBytes());
			ps.setObject(8, track.getUnitPrice());
			return ps;
		}, keyHolder);
		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	public void update(Long id, Track track) {
		String sql = """
				UPDATE Track SET Name = ?, AlbumId = ?, MediaTypeId = ?, GenreId = ?, Composer = ?,
				Milliseconds = ?, Bytes = ?, UnitPrice = ?
				WHERE TrackId = ?
				""";
		jdbcTemplate.update(sql,
				track.getName(),
				track.getAlbumId(),
				track.getMediaTypeId(),
				track.getGenreId(),
				track.getComposer(),
				track.getMilliseconds(),
				track.getBytes(),
				track.getUnitPrice(),
				id);
	}

	public void deleteById(Long id) {
		jdbcTemplate.update("DELETE FROM Track WHERE TrackId = ?", id);
	}

	/** One query per call — used by the N+1 demo path. */
	public String findAlbumTitleByAlbumId(Long albumId) {
		if (albumId == null) {
			return null;
		}
		try {
			return jdbcTemplate.queryForObject("SELECT Title FROM Album WHERE AlbumId = ?", String.class, albumId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<TrackWithAlbumDto> findTracksWithAlbumJoin(int offset, int limit, String sortColumnSql, boolean ascending) {
		String dir = ascending ? "ASC" : "DESC";
		String orderExpr = "t." + sortColumnSql;
		String sql = """
				SELECT t.TrackId, t.Name, t.AlbumId, al.Title AS AlbumTitle, t.MediaTypeId, t.GenreId,
				       t.Composer, t.Milliseconds, t.Bytes, t.UnitPrice
				FROM Track t
				LEFT JOIN Album al ON t.AlbumId = al.AlbumId
				ORDER BY %s %s
				OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
				""".formatted(orderExpr, dir);
		return jdbcTemplate.query(sql, (rs, rowNum) -> new TrackWithAlbumDto(
				rs.getLong("TrackId"),
				rs.getString("Name"),
				getNullableLong(rs, "AlbumId"),
				rs.getString("AlbumTitle"),
				getNullableLong(rs, "MediaTypeId"),
				getNullableLong(rs, "GenreId"),
				rs.getString("Composer"),
				getNullableLong(rs, "Milliseconds"),
				getNullableLong(rs, "Bytes"),
				getNullableDouble(rs, "UnitPrice")), offset, limit);
	}

	public static String resolveTrackSortColumn(String sortProperty) {
		return TRACK_SORT_COLUMNS.getOrDefault(sortProperty, "TrackId");
	}

	private static Long getNullableLong(java.sql.ResultSet rs, String column) throws java.sql.SQLException {
		Object v = rs.getObject(column);
		if (v == null) {
			return null;
		}
		return ((Number) v).longValue();
	}

	private static Double getNullableDouble(java.sql.ResultSet rs, String column) throws java.sql.SQLException {
		Object v = rs.getObject(column);
		if (v == null) {
			return null;
		}
		return ((Number) v).doubleValue();
	}
}
