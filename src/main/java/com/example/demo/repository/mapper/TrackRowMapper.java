package com.example.demo.repository.mapper;

import com.example.demo.model.Track;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TrackRowMapper implements RowMapper<Track> {
	@Override
	public Track mapRow(ResultSet rs, int rowNum) throws SQLException {
		Track t = new Track();
		t.setTrackId(rs.getLong("TrackId"));
		t.setName(rs.getString("Name"));
		t.setAlbumId(getNullableLong(rs, "AlbumId"));
		t.setMediaTypeId(getNullableLong(rs, "MediaTypeId"));
		t.setGenreId(getNullableLong(rs, "GenreId"));
		t.setComposer(rs.getString("Composer"));
		t.setMilliseconds(getNullableLong(rs, "Milliseconds"));
		t.setBytes(getNullableLong(rs, "Bytes"));
		Object price = rs.getObject("UnitPrice");
		if (price != null) {
			t.setUnitPrice(((Number) price).doubleValue());
		}
		return t;
	}

	private static Long getNullableLong(ResultSet rs, String column) throws SQLException {
		Object v = rs.getObject(column);
		if (v == null) {
			return null;
		}
		return ((Number) v).longValue();
	}
}
