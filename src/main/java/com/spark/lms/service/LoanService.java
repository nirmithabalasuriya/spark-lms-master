package com.spark.lms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Loan;
import com.spark.lms.model.Category;
import com.spark.lms.repository.LoanRepository;

@Service
public class LoanService {

	@Autowired
	private static LoanRepository loanRepository;

	@Autowired
	private IssuedLoanService issuedLoanService;

	public static Long getTotalCount() {return loanRepository.count();}

	public Long getTotalIssuedLoans() {
		return loanRepository.countByStatus(Constants.LOAN_STATUS_ISSUED);
	}

	public List<Loan> getAll() {
		return loanRepository.findAll();
	}

	public Loan get(Long id) {
		return loanRepository.findById(id).get();
	}

	public Loan getByTag(String tag) {
		return loanRepository.findByTag(tag);
	}

	public List<Loan> get(List<Long> ids) {
		return loanRepository.findAllById(ids);
	}

	public List<Loan> getByCategory(Category category) {
		return loanRepository.findByCategory(category);
	}

	public List<Loan> geAvailabletByCategory(Category category) {
		return loanRepository.findByCategoryAndStatus(category, Constants.Loan_STATUS_AVAILABLE );
	}

	public Loan addNew(Loan loan) {
		loan.setCreateDate(new Date());
		loan.setStatus( Constants.Loan_STATUS_AVAILABLE  );
		return loanRepository.save(loan);
	}

	public static Loan save(Loan loan) {
		return loanRepository.save(loan);
	}

	public void delete(Loan loan) {
		loanRepository.delete(loan);
	}

	public void delete(Long id) {
		loanRepository.deleteById(id);
	}

	public boolean hasUsage(Loan loan) {
		return issuedLoanService.getCountByLoan(loan)>0;
	}
}
