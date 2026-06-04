//package com.example.demo.controller;
//
//import com.example.demo.model.Genre;
//import com.example.demo.service.GenreService;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/genres")
//public class GenreController {
//	private final GenreService genreService;
//
//	public GenreController(GenreService genreService) {
//		this.genreService = genreService;
//	}
//
//	@GetMapping
//	public List<Genre> getAllGenre() {
//		return genreService.getAllGenre();
//	}
//
//	@GetMapping("/{id}")
//	public Genre getGenreById(@PathVariable Long id) {
//		return genreService.getGenreById(id);
//	}
//
//	@PostMapping()
//	public void createGenre(@RequestBody Genre genre) {
//		genreService.createGenre(genre);
//	}
//
//	@PutMapping("/{id}")
//	public void updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
//		genreService.updateGenre(id, genre);
//	}
//
//	@DeleteMapping("/{id}")
//	public void deletGenre(@PathVariable Long id) {
//		genreService.deleteGenre(id);
//	}
//
//	@GetMapping("/search")
//	public List<Genre> searchgenre(@RequestParam String keyword) {
//		return genreService.searchGenre(keyword);
//	}
//
//}
