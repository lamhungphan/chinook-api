package com.example.demo.service;

import com.example.demo.dataitem.GenreItem;
import com.example.demo.dao.IGenreDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
	private final IGenreDao dao;

	public GenreService(IGenreDao dao) {
		this.dao = dao;
	}

	public List<GenreItem> getAllGenre() {
		return dao.getAllGenre();
	}

	public GenreItem getGenreById(Long id) {
		return dao.getGenreById(id);
	}

	public void createGenre(GenreItem genre) {
		dao.createGenre(genre);
	}

	public void updateGenre(Long id, GenreItem genre) {
		dao.updateGenre(id, genre);
	}

	public void deleteGenre(Long id) {
		dao.deleteGenre(id);
	}

	public List<GenreItem> searchGenre(String keyword) {
		return dao.searchGenre(keyword);
	}
}
