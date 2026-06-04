package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dataitem.AlbumItem;
import com.example.demo.service.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {

	public final AlbumService service;

	@RequestMapping( method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<AlbumItem>> selectAlbumList() throws Exception {
		List<AlbumItem> albums = service.selectAlbumList();
		return ResponseEntity.status(HttpStatus.OK).body(albums);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AlbumItem> selectAlbumById(@PathVariable Long id) throws Exception {
		AlbumItem album = service.selectAlbumById(id);
		return ResponseEntity.status(HttpStatus.OK).body(album);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Void> insertAlbum(@RequestBody AlbumItem parm) throws Exception {
		service.insertAlbum(parm);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody AlbumItem parm) throws Exception {
		service.updateAlbum(id, parm);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteAlbum(@PathVariable("id") Long id) throws Exception {
		service.deleteAlbum(id);
		return ResponseEntity.ok().build();
	}

}
