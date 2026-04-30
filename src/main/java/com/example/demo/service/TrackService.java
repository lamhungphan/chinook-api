package com.example.demo.service;

import com.example.demo.model.Track;
import com.example.demo.repository.TrackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TrackService {
    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Page<Track> getAllTrack(Pageable pageable) {
        return trackRepository.findAll(pageable);
    }

    public Track getTrackById(Long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Track not found with id: " + id));
    }

    public void createTrack(Track track) {
        trackRepository.save(track);
    }
    
    public void updateTrack(Long id, Track track) {
        Track existingTrack = trackRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Track not found with id: " + id));

        existingTrack.setName(track.getName());
        existingTrack.setAlbumId(track.getAlbumId());
        existingTrack.setMediaTypeId(track.getMediaTypeId());
        existingTrack.setGenreId(track.getGenreId());
        existingTrack.setComposer(track.getComposer());
        existingTrack.setMilliseconds(track.getMilliseconds());
        existingTrack.setBytes(track.getBytes());
        existingTrack.setUnitPrice(track.getUnitPrice());

        trackRepository.save(existingTrack);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
}