package com.tendercare.service;

import org.springframework.stereotype.Controller;

import com.tendercare.business.DateUtility;
import com.tendercare.dao.JobRepository;
import com.tendercare.model.Job;

/**
 * 
 * @author Imen Gharsalli
 *
 */
@Controller
public class JobService {

	JobRepository jobRepository;
	private String midnightToday = DateUtility.midnightToday();
	private String midnightyesterday = DateUtility.midnightYesterday();

	/**
	 * 
	 * @param jobRepository
	 */
	public JobService(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Job> readJobs() {
		return jobRepository.findAll();
	}

	/**
	 * 
	 * @param job
	 * @return
	 */
	public Job createJob(Job job) {
		return jobRepository.save(job);
	}

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	public Job readJob(Long jobId) {
		return jobRepository.findOne(jobId);
	}

	/**
	 * 
	 * @param jobId
	 */
	public void deleteJob(Long jobId) {
		jobRepository.delete(jobId);
	}

	/**
	 * 
	 * @param job
	 * @param jobId
	 */
	public void updateJob(Job job, long jobId) {

	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Job> getexpireTodayJobs() {
		return jobRepository.findByexpirationdate(midnightToday);
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Job> getPostedTodayJobs() {
		return jobRepository.findByPostdate(midnightyesterday);
	}
}
