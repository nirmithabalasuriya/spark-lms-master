package com.spark.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Loan;
import com.spark.lms.model.IssuedLoan;
import com.spark.lms.repository.IssuedLoanRepository;

@Service
public class IssuedLoanService {

	@Autowired
	private IssuedLoanRepository issuedLoanRepository;

	public static Long getTotalCount() {
		return null;
	}

	public List<IssuedLoan> getAll() {
		return issuedLoanRepository.findAll();
	}

	public boolean get(Long id) {
		return issuedLoanRepository.findById(id).isPresent();
	}

	public Long getCountByLoan(Loan loan) {
		return issuedLoanRepository.countByLoanAndReturned(loan, Constants.LOAN_NOT_RETURNED);
	}

	public IssuedLoan save(IssuedLoan issuedLoan) {
		return issuedLoanRepository.save(issuedLoan);
	}

	public IssuedLoan addNew(IssuedLoan issuedLoan) {
		issuedLoan.setReturned( Constants.LOAN_NOT_RETURNED );
		return issuedLoanRepository.save(issuedLoan);
	}

}
