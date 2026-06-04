package com.example.demo.controller;

import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {
	private final ArtistService artistService;

	public ArtistController(ArtistService artistService) {
		this.artistService = artistService;
	}

	@GetMapping
	public List<Artist> getAllArtist() {
		return artistService.getAllArtist();
	}

	@GetMapping("/{id}")
	public Artist getArtistById(@PathVariable Long id) {
		return artistService.getArtistById(id);
	}

	@PostMapping()
	public void createArtist(@RequestBody Artist artist) {
		artistService.createArtist(artist);
	}

	@PutMapping("/{id}")
	public void updateArtist(@PathVariable Long id, @RequestBody Artist artist) {
		artistService.updateArtist(id, artist);
	}

	@DeleteMapping("/{id}")
	public void deleteArtist(@PathVariable Long id) {
		artistService.deleteArtist(id);
	}
	
	@GetMapping("/search")
	public List<Artist> searchArtist(@RequestParam String keyword) {
		return artistService.searchArtist(keyword);
	}
	
	@GetMapping("/searchByGenre")
	public List<Artist> getArtistByGenre(@RequestParam String keyword) {
		return artistService.getArtistByGenre(keyword);
	}
}
