/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// on click listner for the save button
$(document).on("click", "#btnSave", function(event) {

	// 1 - clear the alert boxes (success and error)
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();

	$("#alertError").text("");
	$("#alertError").hide();

	// then we need to call the validate method
	var status = validateItemform();
	
	console.log(status);

	// if not properly validated
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// if valid.....submit the form
	$("#formItem").submit();

});

// implementing the update button handler
$(document).on(
		"click",
		".btnUpdate",
		function(event) {

			// getting the hidden column value of which the clicked update
			// button exist
			$("#hidItemIDSave").val(
					$(this).closest("tr").find('#hidItemIDUpdate').val());
			// loading the data to the form again
			$("#itemCode").val($(this).closest("tr").find('td:eq(0)').text());
			$("#itemName").val($(this).closest("tr").find('td:eq(1)').text());
			$("#itemPrice").val($(this).closest("tr").find('td:eq(2)').text());
			$("#itemDesc").val($(this).closest("tr").find('td:eq(3)').text());

		});

function validateItemform() {
	// CODE
	if ($("#itemCode").val().trim() == "") {
		return "Insert Item Code.";
	}
	// NAME
	if ($("#itemName").val().trim() == "") {
		return "Insert Item Name.";
	}
	// PRICE-------------------------------
	if ($("#itemPrice").val().trim() == "") {
		return "Insert Item Price.";
	}
	// is numerical value
	var tmpPrice = $("#itemPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Item Price.";
	}
	// convert to decimal price
	$("#itemPrice").val(parseFloat(tmpPrice).toFixed(2));
	// DESCRIPTION------------------------
	if ($("#itemDesc").val().trim() == "") {
		return "Insert Item Description.";
	}
	return true;
}
