$(document).ready(function() {
	$('#gotoListBtn').on('click', function() {
		window.location = "/loan/list";
	});
	
	$('#category-selectable').on('change', function() {
		updateTagField();
	});
	
	$('#resetBtn').on('click', function() {
		setTimeout(function(){
			updateTagField();
		}, 10);
		
	});
	
	function updateTagField() {
		var shortName =  $("#category-selectable option:selected").attr("short-name");
		if( shortName ) {
			$('#tag').val( shortName + '-' );
		}
	}
	
	if( !$('#id').val() ) {
		updateTagField();
	}
});