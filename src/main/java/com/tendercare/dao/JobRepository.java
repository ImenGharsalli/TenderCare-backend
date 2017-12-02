package com.tendercare.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tendercare.model.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

	public Iterable<Job> findByPostdate(String date);

	public Iterable<Job> findByexpirationdate(String date);

}
