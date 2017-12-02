package com.tendercare.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tendercare.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
