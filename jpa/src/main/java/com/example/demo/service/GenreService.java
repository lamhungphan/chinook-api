//package com.example.demo.service;
//
//import com.example.demo.model.Genre;
//import com.example.demo.repository.GenreRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class GenreService {
//	private final GenreRepository genreRepository;
//
//	public GenreService(GenreRepository genreRepository) {
//		this.genreRepository = genreRepository;
//	}
//
//	public List<Genre> getAllGenre() {
//		return genreRepository.getAllGenre();
//	}
//
//	public Genre getGenreById(Long id) {
//		return genreRepository.getGenreById(id);
//	}
//
//	public void createGenre(Genre Genre) {
//		genreRepository.createGenre(Genre);
//	}
//
//	public void updateGenre(Long id, Genre Genre) {
//		genreRepository.updateGenre(id, Genre);
//	}
//
//	public void deleteGenre(Long id) {
//		genreRepository.deleteGenre(id);
//	}
//
//	public List<Genre> searchGenre(String keyword) {
//		return genreRepository.searchGenre(keyword);
//	}
//}
