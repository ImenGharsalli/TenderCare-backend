package com.tendercare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tendercare.model.Careservice;

public interface CareServiceRepository extends JpaRepository<Careservice, Long> {
}
