package com.example.demo.service;

import com.example.demo.model.Artist;
import com.example.demo.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistService {
	private final ArtistRepository artistRepository;

	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public List<Artist> getAllArtist() {
		return artistRepository.getAllArtist();
	}

	public Artist getArtistById(Long id) {
		return artistRepository.getArtistById(id);
	}

	public void createArtist(Artist artist) {
		artistRepository.createArtist(artist);
	}

	public void updateArtist(Long id, Artist artist) {
		artistRepository.updateArtist(id, artist);
	}

	public void deleteArtist(Long id) {
		artistRepository.deleteArtist(id);
	}
	

	public List<Artist> searchArtist(String keyword) {
		return artistRepository.searchArtist(keyword);
	}
	
	public List<Artist> getArtistByGenre(String keyword){
		return artistRepository.getArtistByGenre(keyword);
	}
}
