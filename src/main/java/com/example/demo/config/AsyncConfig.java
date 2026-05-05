package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

	public static final String TRACK_ENRICH_EXECUTOR = "trackEnrichExecutor";

	@Bean(name = TRACK_ENRICH_EXECUTOR)
	public Executor trackEnrichExecutor(
			@Value("${app.async.track-enrich.core-pool-size:4}") int corePoolSize,
			@Value("${app.async.track-enrich.max-pool-size:16}") int maxPoolSize,
			@Value("${app.async.track-enrich.queue-capacity:100}") int queueCapacity) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("track-enrich-");
		executor.initialize();
		return executor;
	}
}
