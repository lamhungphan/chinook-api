package com.example.demo.controller;

import com.example.demo.dto.ScheduleRequest;
import com.example.demo.service.QuartzScheduleService;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedules/genre-price")
public class ScheduleController {

	private final QuartzScheduleService scheduleService;

	public ScheduleController(QuartzScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	/** Đăng ký hoặc reschedule CronTrigger cho việc cập nhật giá theo thể loại. */
	@PostMapping
	public ResponseEntity<Map<String, Object>> schedule(@RequestBody ScheduleRequest request) throws SchedulerException {
		Map<String, Object> result = scheduleService.schedule(
				request.genreName(), request.percentage(), request.cron());
		return ResponseEntity.ok(result);
	}

	/** Liệt kê tất cả CronTrigger hiện tại. */
	@GetMapping
	public List<Map<String, Object>> list() throws SchedulerException {
		return scheduleService.list();
	}

	/** Kích hoạt job ngay lập tức (ngoài lịch). */
	@PostMapping("/{genreName}/trigger-now")
	public ResponseEntity<Map<String, Object>> triggerNow(@PathVariable String genreName) throws SchedulerException {
		boolean fired = scheduleService.triggerNow(genreName);
		if (!fired) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(Map.of("triggered", true, "genreName", genreName));
	}

	/** Huỷ schedule. */
	@DeleteMapping("/{genreName}")
	public ResponseEntity<Map<String, Object>> unschedule(@PathVariable String genreName) throws SchedulerException {
		boolean removed = scheduleService.unschedule(genreName);
		return ResponseEntity.ok(Map.of("removed", removed, "genreName", genreName));
	}
}
