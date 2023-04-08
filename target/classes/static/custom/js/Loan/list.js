var loanId, loanName;

function removeLoanDialog(el) {
	loanId = $(el).attr('data-loan-id');
	loanName = $(el).attr('data-loan-name');
	$('.remove-loan-modal').find('#loan-name').text(loanName);
}

function removeLoan() {
	$('.remove-loan-modal').modal('hide');
	window.location = "/loan/remove/" + loanId;
}