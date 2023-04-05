package com.spark.lms.restcontroller;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Issue;
import com.spark.lms.model.IssuedLoan;
import com.spark.lms.model.Loan;
import com.spark.lms.model.Member;
import com.spark.lms.service.IssueService;
import com.spark.lms.service.IssuedLoanService;
import com.spark.lms.service.LoanService;
import com.spark.lms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/issue")
public class IssueRestController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private IssueService issueService;

	@Autowired
	private IssuedLoanService issuedLoanService;

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@RequestParam Map<String, String> payload) {

		String memberIdStr = (String)payload.get("member");
		String[] loanIdsStr = payload.get("Loans").toString().split(",");

		Long memberId = null;
		List<Long> loanIds = new ArrayList<Long>();
		try {
			memberId = Long.parseLong( memberIdStr );
			for(int k=0 ; k<loanIdsStr.length ; k++) {
				loanIds.add( Long.parseLong(loanIdsStr[k]) );
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return "invalid number format";
		}

		Member member = memberService.get( memberId );
		List<Loan> Loans = loanService.get(loanIds);

		Issue issue = new Issue();
		issue.setMember(member);
		issue = issueService.addNew(issue);

		for( int k=0 ; k<Loans.size() ; k++ ) {
			Loan loan = Loans.get(k);
			loan.setStatus( Constants.LOAN_STATUS_ISSUED );
			loan = LoanService.save(loan);

			IssuedLoan ib = new IssuedLoan();
			ib.setLoan( loan );
			ib.setIssue( issue );
			issuedLoanService.addNew( ib );

		}

		return "success";
	}

	@RequestMapping(value = "/{id}/return/all", method = RequestMethod.GET)
	public String returnAll(@PathVariable(name = "id") Long id) {
		Issue issue = issueService.get(id);
		if( issue != null ) {
			List<IssuedLoan> issuedLoans = issue.getIssuedLoans();
			for( int k=0 ; k<issuedLoans.size() ; k++ ) {
				IssuedLoan ib = issuedLoans.get(k);
				ib.setReturned( Constants.Loan_RETURNED );
				issuedLoanService.save( ib );

				Loan loan = ib.getLoan();
				loan.setStatus( Constants.Loan_STATUS_AVAILABLE  );
				LoanService.save(loan);
			}

			issue.setReturned( Constants.Loan_RETURNED );
			issueService.save(issue);

			return "successful";
		} else {
			return "unsuccessful";
		}
	}

	@RequestMapping(value="/{id}/return", method = RequestMethod.POST)
	public String returnSelected(@RequestParam Map<String, String> payload, @PathVariable(name = "id") Long id) {
		Issue issue = issueService.get(id);
		String[] issuedLoanIds = payload.get("ids").split(",");
		if( issue != null ) {

			List<IssuedLoan> issuedLoans = issue.getIssuedLoans();
			for( int k=0 ; k<issuedLoans.size() ; k++ ) {
				IssuedLoan ib = issuedLoans.get(k);
				if( Arrays.asList(issuedLoanIds).contains( ib.getId().toString() ) ) {
					ib.setReturned( Constants.Loan_RETURNED );
					issuedLoanService.save( ib );

					Loan loan = ib.getLoan();
					loan.setStatus( Constants.Loan_STATUS_AVAILABLE  );
					LoanService.save(loan);
				}
			}

			return "successful";
		} else {
			return "unsuccessful";
		}
	}

}
