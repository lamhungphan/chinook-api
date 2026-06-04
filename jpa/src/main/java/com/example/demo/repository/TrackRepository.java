package com.example.demo.repository;

import com.example.demo.model.Track;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    @Query("select t from Track t")
    Page<Track> findPageForNPlusOne(Pageable pageable);

    @EntityGraph(attributePaths = "album")
    @Query("select t from Track t")
    Page<Track> findPageWithAlbum(Pageable pageable);
}