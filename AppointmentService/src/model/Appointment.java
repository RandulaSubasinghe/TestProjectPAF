package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {
	
	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/appointmentdb", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertApointment(String doctor_id, String patient_id, String prescription, String doc_notes) {

		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into appointmentdetails(app_id, doctor_id, patient_id, prescription, doc_notes)"
					+ " values ( ?,  ?,  ?,  ?,  ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);


			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, doctor_id);
			preparedStmt.setString(3, patient_id);
			preparedStmt.setString(4, prescription);
			preparedStmt.setString(5, doc_notes);
			
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	   
			String newAppo = readAppo(); 
			output =  "{\"status\":\"success\", \"data\": \""
			+ newAppo + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Appointment.\"}";  
			System.err.println(e.getMessage());   
		} 
		
	  return output;  
	} 
	
	public String readAppo()  

	{   
		String output = ""; 
	
		try   
		{    
			Connection con = connect(); 
		
			if (con == null)    
			{
				return "Error while connecting to the database for reading."; 
			} 
	 
			// Prepare the html table to be displayed    
			output = "<table border='1'><tr><th>Doctor Id</th><th>Patient Id</th><th>Prescription</th><th>Doctor Notes</th><th>Update</th><th>Remove</th></tr>"; 
	 
			String query = "select * from appointmentdetails";    
			Statement stmt = con.createStatement();    
			ResultSet rs = stmt.executeQuery(query); 
	 
			// iterate through the rows in the result set    
			while (rs.next())    
			{     
				String app_id = Integer.toString(rs.getInt("app_id"));
				String doctor_id = rs.getString("doctor_id");
				String patient_id = rs.getString("patient_id");
				String prescription = rs.getString("prescription");
				String doc_notes = rs.getString("doc_notes");
			
	 
				// Add into the html table 
				output += "<tr><td><input id=\'hidItemIDUpdate\' name=\'hidItemIDUpdate\' type=\'hidden\' value=\'" + app_id + "'>" 
							+ doctor_id + "</td>";      
				output += "<td>" + patient_id + "</td>";     
				output += "<td>" + prescription + "</td>";
				output += "<td>" + doc_notes + "</td>"; 
	 
				// buttons     
//				output += "<td><input name=\'btnUpdate\' type=\'button\' value=\'Update\' class=\' btnUpdate btn btn-secondary\'></td>"
//						//+ "<td><form method=\"post\" action=\"items.jsp\">      "
//						+ "<input name=\'btnRemove\' type=\'submit\' value=\'Remove\' class=\'btn btn-danger\'> "
//						+ "<input name=\"hidItemIDDelete\" type=\"hidden\" value=\"" + itemID + "\">" + "</form></td></tr>"; 
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-docid='" + app_id + "'>" + "</td></tr>"; 
			} 
	 
			con.close(); 
	 
			// Complete the html table    
			output += "</table>";   
		}   
		catch (Exception e)   
		{    
			output = "Error while reading the Appointment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	
	public String updateAppointment(String app_id, String doctor_id, String patient_id, String prescription, String doc_notes) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE appointmentdetails SET doctor_id=?,patient_id=?,prescription=?,doc_notes=? WHERE app_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, doctor_id);
			preparedStmt.setString(2, patient_id);
			preparedStmt.setString(3, prescription);
			preparedStmt.setString(4, doc_notes);
			preparedStmt.setInt(5, Integer.parseInt(app_id));	 
			
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newAppo = readAppo();    
			output = "{\"status\":\"success\", \"data\": \"" +        
					newAppo + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";   
			System.err.println(e.getMessage());   
		} 
	 
	  return output;  
	} 
	
	public String deleteAppor(String app_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from appointmentdetails where app_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(app_id));
	 
			// execute the statement    
			preparedStmt.execute();    
			con.close(); 
	 
			String newAppo = readAppo();    
			output = "{\"status\":\"success\", \"data\": \"" +       
					newAppo + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Appointment.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}
	 
	 
}
