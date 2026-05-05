package com.example.demo.job;

import com.example.demo.repository.DbInteractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Cron job dùng Spring @Scheduled. Định kỳ dọn các bản ghi TrackPriceLog cũ
 * do trigger trg_AfterPriceUpdate ghi.
 */
@Component
public class PriceLogCleanupJob {

	private static final Logger log = LoggerFactory.getLogger(PriceLogCleanupJob.class);

	private final DbInteractRepository repository;
	private final int retentionDays;
	private final boolean heartbeatEnabled;

	public PriceLogCleanupJob(
			DbInteractRepository repository,
			@Value("${app.jobs.price-log-cleanup.retention-days:90}") int retentionDays,
			@Value("${app.jobs.price-log-cleanup.heartbeat-enabled:true}") boolean heartbeatEnabled) {
		this.repository = repository;
		this.retentionDays = retentionDays;
		this.heartbeatEnabled = heartbeatEnabled;
	}

	@Scheduled(cron = "${app.jobs.price-log-cleanup.cron:0 0 2 * * *}")
	public void cleanup() {
		LocalDateTime cutoff = LocalDateTime.now().minusDays(retentionDays);
		log.info("[PriceLogCleanupJob] start cleanup, cutoff={}, retentionDays={}", cutoff, retentionDays);
		int deleted = repository.deleteOldPriceLogs(cutoff);
		log.info("[PriceLogCleanupJob] done, deleted={} rows", deleted);
	}

	@Scheduled(fixedRateString = "${app.jobs.price-log-cleanup.heartbeat-rate-ms:60000}")
	public void heartbeat() {
		if (!heartbeatEnabled) {
			return;
		}
		log.info("[PriceLogCleanupJob] heartbeat - scheduler is alive at {}", LocalDateTime.now());
	}
}
