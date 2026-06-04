package com.example.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.demo.dao.IAlbumDao;
import com.example.demo.dataitem.AlbumItem;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
	private final IAlbumDao dao;

	public List<AlbumItem> selectAlbumList() {
		return dao.selectAlbumList();
	}

	public AlbumItem selectAlbumById(Long id) {
		AlbumItem item = dao.selectAlbumById(id);
		if (item == null) {
			throw new NoSuchElementException("Album not found with id: " + id);
		}
		return item;
	}

	public void insertAlbum(AlbumItem parm) {
		dao.insertAlbum(parm);
	}

	public void updateAlbum(Long id, AlbumItem parm) {
		if (dao.selectAlbumById(id) == null) {
			throw new NoSuchElementException("Album not found with id: " + id);
		}
		dao.updateAlbum(id, parm);
	}

	public void deleteAlbum(Long id) {
		dao.deleteAlbum(id);
	}

}
