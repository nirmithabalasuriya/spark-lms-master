var issuedLoans = [];
var selectedIssueId = undefined;

function returnLoan(issueId) {
	selectedIssueId = issueId;
	issuedLoans = [];
	jQuery('#issue_'+issueId).find('li').each(function (i){
		var loan = {id: $(this).attr('data-id'), title: $(this).text()}
		issuedLoans.push( loan );
	});

	populateReturnLoanTable();
	jQuery('#select-all').prop('checked', false);
	jQuery('.returnLoanChk').prop('checked', false);
	$('.return-loan-modal').modal('show');

}

function populateReturnLoanTable() {
	var trs = '';
	for(var k=0 ; k<issuedLoans.length ; k++) {
		trs += '<tr>';
		trs += '<td><input type="checkbox" value="'+issuedLoans[k].id+'" class="returnLoanChk" onclick="returnLoanChkClicked()" /></td>';
		trs += '<td>'+issuedLoans[k].title+'</td>';
		trs += '</tr>';
	}
	jQuery('#returnLoanTable tr:gt(0)').remove();
	jQuery('#returnLoanTable').append(trs);
}

function returnLoanChkClicked() {
	var total = jQuery('.returnLoanChk').length;
	var checked = jQuery('.returnLoanChk:checkbox:checked').length;
	if( total == checked ) {
		jQuery('#select-all').prop('checked', true);
	} else {
		jQuery('#select-all').prop('checked', false);
	}
}

function sellectAll() {
	if( jQuery('#select-all').prop('checked') ) {
		jQuery('.returnLoanChk').prop('checked', true);
	} else {
		jQuery('.returnLoanChk').prop('checked', false);
	}
}

function returnLoanConfirm() {
	var checked = jQuery('.returnLoanChk:checkbox:checked').length;
	if( checked > 0 ) {
		var total = jQuery('.returnLoanChk').length;
		if( total == checked ) {
			$.get('/rest/issue/'+ selectedIssueId + '/return/all', function(msg) {
				if( msg == 'successful' ) {
					window.location = '/issue/list';
				}
			});
		} else {
			var ids = [];
			jQuery('.returnLoanChk:checkbox:checked').each(function (i){
				ids.push( $(this).val() );
			});
			$.post( '/rest/issue/'+selectedIssueId+'/return' , {ids: ids.join(',')} ).done(function (msg){
				if( msg=='successful' ) {
					window.location = '/issue/list';
				}
			});
		}
	}
}

