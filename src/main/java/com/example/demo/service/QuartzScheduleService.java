package com.example.demo.service;

import com.example.demo.job.GenrePriceUpdateJob;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Đăng ký / cập nhật / huỷ Quartz CronTrigger cho GenrePriceUpdateJob.
 */
@Service
public class QuartzScheduleService {

	public static final String JOB_GROUP = "genre-price";
	public static final String TRIGGER_GROUP = "genre-price-trigger";

	private final Scheduler scheduler;

	public QuartzScheduleService(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Map<String, Object> schedule(String genreName, Double percentage, String cron) throws SchedulerException {
		validate(genreName, percentage, cron);

		JobKey jobKey = new JobKey(genreName, JOB_GROUP);
		TriggerKey triggerKey = new TriggerKey(genreName, TRIGGER_GROUP);

		JobDataMap data = new JobDataMap();
		data.put(GenrePriceUpdateJob.DATA_GENRE_NAME, genreName);
		data.put(GenrePriceUpdateJob.DATA_PERCENTAGE, percentage);

		JobDetail jobDetail = JobBuilder.newJob(GenrePriceUpdateJob.class)
				.withIdentity(jobKey)
				.usingJobData(data)
				.storeDurably()
				.build();

		CronTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(triggerKey)
				.forJob(jobKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cron))
				.usingJobData(data)
				.build();

		boolean rescheduled = false;
		if (scheduler.checkExists(jobKey)) {
			scheduler.addJob(jobDetail, true);
			if (scheduler.checkExists(triggerKey)) {
				scheduler.rescheduleJob(triggerKey, trigger);
			} else {
				scheduler.scheduleJob(trigger);
			}
			rescheduled = true;
		} else {
			scheduler.scheduleJob(jobDetail, trigger);
		}

		return describe(jobKey, rescheduled);
	}

	public boolean unschedule(String genreName) throws SchedulerException {
		JobKey jobKey = new JobKey(genreName, JOB_GROUP);
		return scheduler.deleteJob(jobKey);
	}

	public boolean triggerNow(String genreName) throws SchedulerException {
		JobKey jobKey = new JobKey(genreName, JOB_GROUP);
		if (!scheduler.checkExists(jobKey)) {
			return false;
		}
		scheduler.triggerJob(jobKey);
		return true;
	}

	public List<Map<String, Object>> list() throws SchedulerException {
		List<Map<String, Object>> result = new ArrayList<>();
		for (JobKey jobKey : scheduler.getJobKeys(org.quartz.impl.matchers.GroupMatcher.jobGroupEquals(JOB_GROUP))) {
			result.add(describe(jobKey, false));
		}
		return result;
	}

	private Map<String, Object> describe(JobKey jobKey, boolean rescheduled) throws SchedulerException {
		Map<String, Object> info = new LinkedHashMap<>();
		info.put("jobKey", jobKey.getName());
		info.put("group", jobKey.getGroup());
		info.put("rescheduled", rescheduled);

		List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
		List<Map<String, Object>> triggerInfo = new ArrayList<>();
		for (Trigger t : triggers) {
			Map<String, Object> ti = new LinkedHashMap<>();
			ti.put("triggerKey", t.getKey().toString());
			if (t instanceof CronTrigger ct) {
				ti.put("cron", ct.getCronExpression());
			}
			ti.put("nextFireTime", t.getNextFireTime());
			ti.put("previousFireTime", t.getPreviousFireTime());
			triggerInfo.add(ti);
		}
		info.put("triggers", triggerInfo);

		JobDetail detail = scheduler.getJobDetail(jobKey);
		if (detail != null) {
			JobDataMap data = detail.getJobDataMap();
			info.put("genreName", data.getString(GenrePriceUpdateJob.DATA_GENRE_NAME));
			info.put("percentage", data.get(GenrePriceUpdateJob.DATA_PERCENTAGE));
		}
		return info;
	}

	private static void validate(String genreName, Double percentage, String cron) {
		if (genreName == null || genreName.isBlank()) {
			throw new IllegalArgumentException("genre_name không được để trống");
		}
		if (percentage == null) {
			throw new IllegalArgumentException("percentage là bắt buộc");
		}
		if (cron == null || cron.isBlank()) {
			throw new IllegalArgumentException("cron là bắt buộc");
		}
		try {
			new CronExpression(cron);
		} catch (ParseException e) {
			throw new IllegalArgumentException("cron không hợp lệ: " + e.getMessage(), e);
		}
	}
}
