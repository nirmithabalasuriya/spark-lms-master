package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	public List<Category> findAllByOrderByNameAsc();
}
