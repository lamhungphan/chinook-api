package com.example.demo.job;

import com.example.demo.service.DbInteractService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Quartz Job. Lịch chạy được quản lý bằng CronTrigger trong QuartzScheduleService.
 * SpringBeanJobFactory (mặc định của spring-boot-starter-quartz) sẽ tự inject DbInteractService.
 */
public class GenrePriceUpdateJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(GenrePriceUpdateJob.class);

	public static final String DATA_GENRE_NAME = "genreName";
	public static final String DATA_PERCENTAGE = "percentage";

	@Autowired
	private DbInteractService dbInteractService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap data = context.getMergedJobDataMap();
		String genreName = data.getString(DATA_GENRE_NAME);
		double percentage = data.getDouble(DATA_PERCENTAGE);
		log.info("[GenrePriceUpdateJob] fired key={} genre={} percentage={}",
				context.getJobDetail().getKey(), genreName, percentage);
		try {
			dbInteractService.updateGenrePrice(genreName, percentage);
			log.info("[GenrePriceUpdateJob] success genre={}", genreName);
		} catch (Exception ex) {
			log.error("[GenrePriceUpdateJob] failed genre={}", genreName, ex);
			throw new JobExecutionException(ex, false);
		}
	}
}
