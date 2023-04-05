package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.Loan;
import com.spark.lms.model.Category;

public interface LoanRepository extends JpaRepository<Loan, Long> {
	public Loan findByTag(String tag);
	public List<Loan> findByCategory(Category category);
	public List<Loan> findByCategoryAndStatus(Category category, Integer status);
	public Long countByStatus(Integer status);
}
