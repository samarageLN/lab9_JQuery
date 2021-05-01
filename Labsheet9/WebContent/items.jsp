<%@page import="com.Item"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%
	//Save---------------------------------
if (request.getParameter("itemCode") != null) {
	Item itemObj = new Item();
	String stsMsg = "";
	//Insert--------------------------
	if (request.getParameter("hidItemIDSave") == "") {
		stsMsg = itemObj.insertItem(request.getParameter("itemCode"), request.getParameter("itemName"),
		request.getParameter("itemPrice"), request.getParameter("itemDesc"));
	} else//Update----------------------
	{
		stsMsg = itemObj.updateItem(request.getParameter("hidItemIDSave"), request.getParameter("itemCode"),
		request.getParameter("itemName"), request.getParameter("itemPrice"), request.getParameter("itemDesc"));
	}
	session.setAttribute("statusMsg", stsMsg);
}
//Delete-----------------------------
if (request.getParameter("hidItemIDDelete") != null) {
	Item itemObj = new Item();
	String stsMsg = itemObj.removeItems(request.getParameter("hidItemIDDelete"));
	session.setAttribute("statusMsg", stsMsg);
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/items.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Items Management</h1>
				<div class="col-md-6">
					<div class="form-group">


						<form id="formItem" name="formItem" method="post"
							action="items.jsp">
							Item code: <input id="itemCode" name="itemCode" type="text"
								class="form-control""><br> Item name: <input
								id="itemName"
								name="itemName" type="text" class="form-control"><br>
							Item price: <input id="itemPrice" name="itemPrice" type="text"
								class="form-control"><br> Item description: <input
								id="itemDesc" name="itemDesc" type="text" class="form-control"><br>
							<input id="hidItemIDSave" name="hidItemIDSave" type="hidden">

							<div id="alertSuccess" class="alert alert-success"></div>
							<div id="alertError" class="alert alert-danger"></div>

							<input id="btnSave" name="btnSave" type="button" value="Save"
								" class="btn btn-primary">
						</form>

					</div>
				</div>

				<div class="alert alert-success">
					<%
						out.print(session.getAttribute("statusMsg"));
					%>
				</div>


				<br>

				<%
					// call the readItems method

				Item itemObj = new Item();
				out.print(itemObj.readItems());
				%>


				<br>

			</div>
		</div>
	</div>



</body>
</html>