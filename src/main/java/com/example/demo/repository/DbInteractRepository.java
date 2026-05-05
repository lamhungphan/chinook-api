package com.example.demo.repository;

import com.example.demo.dto.ArtistRevenueDto;
import com.example.demo.dto.CustomerOrderDto;
import com.example.demo.dto.TrackDetailInfoDto;
import com.example.demo.dto.TrackPriceLogDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DbInteractRepository {

	List<CustomerOrderDto> selectCustomerOrders();

	List<TrackDetailInfoDto> selectTrackDetailInfo(
			@Param("genreName") String genreName,
			@Param("limit") Integer limit);

	void callUpdateGenrePrice(
			@Param("genreName") String genreName,
			@Param("percentage") Double percentage);

	String callFormatDuration(@Param("ms") Long ms);

	List<TrackPriceLogDto> selectTrackPriceLogs(@Param("limit") Integer limit);

	List<ArtistRevenueDto> selectArtistRevenue(@Param("minTracksSold") Long minTracksSold);
}
