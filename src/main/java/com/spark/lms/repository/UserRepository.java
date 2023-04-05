package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findAllByOrderByDisplayNameAsc();
	public List<User> findAllByActiveOrderByDisplayNameAsc(Integer active);
	public User findByUsername(String username);
}
