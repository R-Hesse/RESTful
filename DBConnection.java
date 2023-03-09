package org.ac.cst8277.hesse.ryan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String dbUser = "root";
    private static final String dbPassword = "password"; //change this to be your MySQL Password
    private static final String dbString = "jdbc:mysql://localhost:3306/";


 


	public static Connection getConnectionToDatabase() {
		Connection connection = null;
        
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbString, dbUser, dbPassword);
        }catch(ClassNotFoundException cnf){
            cnf.printStackTrace();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }
        if(connection != null) {
        }
        return connection;
	}

}
