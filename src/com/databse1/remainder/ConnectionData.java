package com.databse1.remainder;

import java.sql.*;


public class ConnectionData {
	public static Connection con=null;
	public static Connection dbCon(){
		try{
				if(con==null){
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookchefconsole","root","");
				System.out.println("Connection Established...!!!");
	}
}
catch(Exception ex){
	ex.printStackTrace();
}
return con;
}
}
