package com.example.demo.service;

import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GenreService {
	private final GenreRepository genreRepository;

	public GenreService(GenreRepository genreRepository) {
		this.genreRepository = genreRepository;
	}

	public List<Genre> getAllGenre() {
		return genreRepository.getAllGenre();
	}

	public Genre getGenreById(Long id) {
		Genre genre = genreRepository.getGenreById(id);
		if (genre == null) {
			throw new NoSuchElementException("Genre not found with id: " + id);
		}
		return genre;
	}

	public void createGenre(Genre genre) {
		genreRepository.createGenre(genre);
	}

	public void updateGenre(Long id, Genre genre) {
		if (genreRepository.getGenreById(id) == null) {
			throw new NoSuchElementException("Genre not found with id: " + id);
		}
		genreRepository.updateGenre(id, genre);
	}

	public void deleteGenre(Long id) {
		genreRepository.deleteGenre(id);
	}

	public List<Genre> searchGenre(String keyword) {
		return genreRepository.searchGenre(keyword);
	}
}
