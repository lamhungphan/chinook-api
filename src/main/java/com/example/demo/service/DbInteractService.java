package com.example.demo.service;

import com.example.demo.dto.ArtistRevenueDto;
import com.example.demo.dto.CustomerOrderDto;
import com.example.demo.dto.TrackDetailInfoDto;
import com.example.demo.dto.TrackPriceLogDto;
import com.example.demo.repository.DbInteractRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DbInteractService {

	private final DbInteractRepository repository;

	public DbInteractService(DbInteractRepository repository) {
		this.repository = repository;
	}

	public List<CustomerOrderDto> getCustomerOrders() {
		return repository.selectCustomerOrders();
	}

	public List<TrackDetailInfoDto> getTrackDetailInfo(String genreName, Integer limit) {
		return repository.selectTrackDetailInfo(genreName, limit);
	}

	@Transactional
	public void updateGenrePrice(String genreName, Double percentage) {
		if (genreName == null || genreName.isBlank()) {
			throw new IllegalArgumentException("genreName không được để trống");
		}
		if (percentage == null) {
			throw new IllegalArgumentException("percentage là bắt buộc");
		}
		repository.callUpdateGenrePrice(genreName, percentage);
	}

	public String formatDuration(Long milliseconds) {
		if (milliseconds == null || milliseconds < 0) {
			throw new IllegalArgumentException("milliseconds phải >= 0");
		}
		return repository.callFormatDuration(milliseconds);
	}

	public List<TrackPriceLogDto> getTrackPriceLogs(Integer limit) {
		return repository.selectTrackPriceLogs(limit);
	}

	public List<ArtistRevenueDto> getArtistRevenue(Long minTracksSold) {
		long threshold = minTracksSold == null ? 10L : minTracksSold;
		return repository.selectArtistRevenue(threshold);
	}
}
