package edu.sjsu.cmpe282.dto;

import java.sql.*;

import edu.sjsu.cmpe282.domain.User;

public class UserDao {
		Connection conn = null;
	    Statement stmt = null;
	
	// Constructor with JDBC connection
	  public UserDao()
	  {
		   try{
		      try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      conn = DriverManager.getConnection("jdbc:mysql://localhost/CloudServices","root","abcd1234");
		   }
		   catch (SQLException e) {
					e.printStackTrace();
					
			}
	  }
	  
	  public boolean addUser(User user)
	  {
		  try {
		 stmt = conn.createStatement();
		 String query = "INSERT INTO `cloudservices`.`users` (`firstname`, `lastname`, `email`, `passwd`) VALUES ('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEmail() + "', '" + user.getPasswd() + "');";
		 stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return true;
	  }
	  
	  public boolean checkUser(User user){
		  ResultSet rs=null;
		  String origPasswd = null;
		  try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Exception thrown while creating statement.");
		}
		  String query = "Select passwd from cloudservices.users where email = '"+user.getEmail()+"';";
		  try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Exception thrown while executing query.");
		}
		  if(rs==null)
			  return false;
		  else{
		  		try {
					rs.next();
					origPasswd = rs.getString("passwd");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("Exception thrown while exploring resultset.");
				}
		  		
		  		System.out.println("Password from db : "+ origPasswd );
		  		System.out.println("Password entered : "+user.getPasswd());	
		  		return user.getPasswd().equals(origPasswd);
		  }
	  }
}