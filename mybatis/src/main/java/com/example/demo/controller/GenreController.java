package com.example.demo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dataitem.GenreItem;
import com.example.demo.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
	private final GenreService service;

	public GenreController(GenreService genreService) {
		this.service = genreService;
	}

	@GetMapping
	public List<GenreItem> getAllGenre() {
		return service.getAllGenre();
	}

	@GetMapping("/{id}")
	public GenreItem getGenreById(@PathVariable Long id) {
		return service.getGenreById(id);
	}

	@PostMapping()
	public void createGenre(@RequestBody GenreItem genre) {
		service.createGenre(genre);
	}

	@PutMapping("/{id}")
	public void updateGenre(@PathVariable Long id, @RequestBody GenreItem genre) {
		service.updateGenre(id, genre);
	}

	@DeleteMapping("/{id}")
	public void deletGenre(@PathVariable Long id) {
		service.deleteGenre(id);
	}

	@GetMapping("/search")
	public List<GenreItem> searchgenre(@RequestParam String keyword) {
		return service.searchGenre(keyword);
	}
}
