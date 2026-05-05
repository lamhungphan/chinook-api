package com.example.demo.controller;

import com.example.demo.dto.ArtistRevenueDto;
import com.example.demo.dto.CustomerOrderDto;
import com.example.demo.dto.TrackDetailInfoDto;
import com.example.demo.dto.TrackPriceLogDto;
import com.example.demo.dto.UpdateGenrePriceRequest;
import com.example.demo.service.DbInteractService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/db-interact")
public class DbInteractController {

	private final DbInteractService service;

	public DbInteractController(DbInteractService service) {
		this.service = service;
	}

	/** View vw_CustomerOrders: tổng chi tiêu theo khách hàng. */
	@GetMapping("/customer-orders")
	public List<CustomerOrderDto> getCustomerOrders() {
		return service.getCustomerOrders();
	}

	/** View vw_TrackDetailInfo: thời lượng và xếp hạng track theo thể loại. */
	@GetMapping("/track-details")
	public List<TrackDetailInfoDto> getTrackDetailInfo(
			@RequestParam(required = false) String genreName,
			@RequestParam(required = false) Integer limit) {
		return service.getTrackDetailInfo(genreName, limit);
	}

	/** Procedure sp_UpdateGenrePrice: cập nhật giá theo thể loại. */
	@PostMapping("/update-genre-price")
	public ResponseEntity<Map<String, Object>> updateGenrePrice(@RequestBody UpdateGenrePriceRequest request) {
		service.updateGenrePrice(request.genreName(), request.percentage());
		return ResponseEntity.ok(Map.of(
				"message", "Đã cập nhật giá thành công",
				"genreName", request.genreName(),
				"percentage", request.percentage()));
	}

	/** Function fn_FormatDuration: định dạng milliseconds → mm:ss. */
	@GetMapping("/format-duration")
	public Map<String, Object> formatDuration(@RequestParam Long ms) {
		String formatted = service.formatDuration(ms);
		return Map.of(
				"milliseconds", ms,
				"formatted", formatted);
	}

	/** Bảng TrackPriceLog do trigger trg_AfterPriceUpdate ghi mỗi khi UnitPrice thay đổi. */
	@GetMapping("/track-price-logs")
	public List<TrackPriceLogDto> getTrackPriceLogs(
			@RequestParam(required = false, defaultValue = "50") Integer limit) {
		return service.getTrackPriceLogs(limit);
	}

	/** Truy vấn CTE: phân tích doanh thu trung bình của các nghệ sĩ. */
	@GetMapping("/artist-revenue")
	public List<ArtistRevenueDto> getArtistRevenue(
			@RequestParam(required = false, defaultValue = "10") Long minTracksSold) {
		return service.getArtistRevenue(minTracksSold);
	}
}
