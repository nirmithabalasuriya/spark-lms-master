package com.spark.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spark.lms.model.Loan;
import com.spark.lms.model.IssuedLoan;

public interface IssuedLoanRepository extends JpaRepository<IssuedLoan, Long> {
    public Long countByLoanAndReturned(Loan loan, Integer returned);
}
