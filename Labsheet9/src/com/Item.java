package com;

import java.sql.*;

public class Item {

	
	private int itemId;
	private String itemName;
	private String itemCode;
	private double itemPrice;
	private String itemDesc;

	public Item() {
	}

	public Item(int itemId, String itemCode, String itemName, double itemPrice, String itemDesc) {
		super();
		this.itemId = itemId;
		this.itemCode = itemCode;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDesc = itemDesc;
	}

	public int getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", itemCode=" + itemCode + ", itemPrice="
				+ itemPrice + ", itemDesc=" + itemDesc + "]";
	}

	// connect method to connect to the mysql DB

	public Connection connect() {

		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_lab_3", "root", "");

			System.out.println("Successfully connected");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return con;

	}

	// insert method

	public String insertItem(String code, String name, String price, String desc) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database";

			}

			// insert query

			String query = "insert into items (`itemID`,`itemCode`,`itemName`,`itemPrice`,`itemDesc`)"
					+ " values(?, ?, ?, ?, ?)";

			// create a prepared statement

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);

			// execute the statement

			preparedStmt.execute();

			// close the connection

			con.close();

			output = "Inserted Successfully";

		} catch (Exception e) {

			output = "Error while inserting";
			System.out.println(e.getMessage());

		}

		return output;
	}// end of insert method

	// read items

	public String readItems() {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for reading";

			}

			// Prepare the HTML table to be displayed
			output = "<table border='1'><tr><th>Item Code</th>" + "<th>Item Name</th><th>Item Price</th>"
					+ "<th>Item Description</th>" + "<th>Update</th><th>Remove</th></tr>";

			// read items from DB and assign values for resultset variable

			String query = "select* from items";
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set

			while (rs.next()) {

				String itemID = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");

				// Add into the html table
				 output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate'  value='" + itemID + "'>"
				 + itemCode + "</td>";
				 output += "<td>" + itemName + "</td>";
				 output += "<td>" + itemPrice + "</td>";
				 output += "<td>" + itemDesc + "</td>";
				 // buttons
				 
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
				 		+ "<td><form method='post' action='items.jsp'>"
				 		+ " <input name='btnRemove' type='submit' "
				 		+ "value='Remove' class='btn btn-danger'> "
				 		+ "<input name='hidItemIDDelete' type='hidden' "
				 		+ "value='" + itemID + "'>" + "</form></td></tr>"; 
			}
			con.close();

			// complete the HTMl table

			output += "</table>";

		} catch (Exception e) {

			output = "Error while reading items";
			System.out.println(e.getMessage());

		}
		return output;
	}// end of read method

	// remove items

	public String removeItems(String itemID) {

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				return "Error while connecting to the database for removing";

			}

			// delete query

			String query = "delete from items where itemId= ?";

			// create a prepared statement

			PreparedStatement stmt = con.prepareStatement(query);

			// binding values

			stmt.setInt(1, Integer.parseInt(itemID));

			// execute the statement

			stmt.executeUpdate();
			output = "Removed successfully";
			con.close();

		} catch (Exception e) {

			output = "Error while removing items";
			System.out.println(e.getMessage());
		}
		return output;
	}// end of remove method

	// read one item

	public Item readOneItem(String id) {

		Item resultItem = null;

		String output = "";

		try {

			Connection con = connect();

			if (con == null) {

				System.out.println("Error while connecting to the database for reading an item");

			}

			String query = "select * from items where itemID = " + id;

			PreparedStatement stmt = con.prepareStatement(query);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				String itemId = Integer.toString(rs.getInt("itemID"));
				String itemCode = rs.getString("itemCode");
				String itemName = rs.getString("itemName");
				String itemPrice = Double.toString(rs.getDouble("itemPrice"));
				String itemDesc = rs.getString("itemDesc");

				resultItem = new Item(Integer.parseInt(itemId), itemCode, itemName, Double.parseDouble(itemPrice),
						itemDesc);

			}
			con.close();

		} catch (Exception e) {

			output = "Error while reading items";
			System.out.println(e.getMessage());

		}
		return resultItem;

	}

	// update items

	public String updateItem(String id, String code, String name, String price, String description) {

		String output = "";

		try {

			Connection con = connect();
			if (con == null) {

				output = "Error while connecting to the database for updating an item";

			}

			// update query

			String query = "update items set itemCode = ?,itemName = ?,itemPrice = ?,itemDesc= ? where itemID = ?";

			// prepare statement

			PreparedStatement stmt = con.prepareStatement(query);

			// binding values

			stmt.setString(1, code);
			stmt.setString(2, name);
			stmt.setDouble(3, Double.parseDouble(price));
			stmt.setString(4, description);

			int iid = Integer.parseInt(id);

			stmt.setInt(5, iid);

			stmt.execute();

			output = "Updated successfully";

			// close the connection

			con.close();

		} catch (Exception e) {

			output = "Error while updating items";
			System.out.println(e.getMessage());
			e.printStackTrace();

		}

		return output;

	}// end of update method
}
