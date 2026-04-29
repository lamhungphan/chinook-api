package com.example.demo.repository;

import com.example.demo.model.Genre;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GenreRepository {
	List<Genre> getAllGenre();

	Genre getGenreById(@Param("id") Long id);

	void createGenre(Genre genre);

	void updateGenre(@Param("id") Long id, @Param("genre") Genre genre);

	void deleteGenre(@Param("id") Long id);

	List<Genre> searchGenre(@Param("keyword") String keyword);
}
