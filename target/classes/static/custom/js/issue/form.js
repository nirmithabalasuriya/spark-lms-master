$(document).ready(function() {

	var members = [];
	function initMembers() {
		$.get('/rest/member/list', function(data) {
			if( data ) {
				members = data;
			}
		});
	}
	initMembers();

	function getMembersByType(memberType) {
		var filteredMembers = [];
		for(var k=0 ; k<members.length ; k++) {
			if( members[k].type==memberType ) {
				filteredMembers.push( members[k] );
			}
		}
		return filteredMembers;
	}

	function populateMembersList( membersList ) {
		$('#memberSel').empty().append('<option selected="selected" value="">-- Select Member --</option>');
		$.each(membersList, function(k, v) {
		     $('#memberSel').append($("<option></option>")
		                    .attr("value",v.id).text(v.firstName + ' ' + v.middleName + (v.lastName?' '+v.lastName:'') ));
		});
	}

	$('#memberTypeSel').on('change', function() {
		var value = $(this).val();
		if( value ) {
			var fiteredMembers = getMembersByType( value );
			populateMembersList( fiteredMembers );
		} else {
			populateMembersList( [] );
		}
	});


	function getLoansByCategory(value) {
		$.get('/rest/loan/' + value + '/available', function(data) {
			if( data ) {
				populateLoansList( data );
			}
		});
	}

	function populateLoansList( loansList ) {
		$('#loansSel').empty().append('<option selected="selected" value="">-- Select Loan --</option>');
		$.each(loansList, function(k, v) {
		     $('#loansSel').append($("<option></option>")
		                    .attr("value",v.id).text(v.title)
		                    .attr("data-authors", v.authors)
		                    .attr("data-tag", v.tag)
		                    .attr("data-publisher", v.publisher));
		});
	}

	$('#categorySel').on('change', function(){
		var value = $(this).val();
		if( value ) {
			var loans = getLoansByCategory( value );
		} else {
			populateLoansList( [] );
		}
	});


	$('#addLoanBtn').on('click', function() {
		var id = $('#loansSel').val();
		var title = $("#loansSel option:selected").text();
		var tag = $("#loansSel option:selected").attr("data-tag");
		var authors = $("#loansSel option:selected").attr("data-authors");

		if( id && !loanAlreadyExist(id) && title && tag && authors ) {
			var loan = { id: id, title: title, tag: tag, authors: authors };
			loansToIssue.push(loan);
			$('#loansSel').val('');
			initLoansInTable();
		}
	});

	function loanAlreadyExist(id) {
		for(var k=0 ; k<loansToIssue.length ; k++) {
			if( loansToIssue[k].id == id ) {
				return true;
			}
		}

}