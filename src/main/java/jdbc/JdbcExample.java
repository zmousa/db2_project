package jdbc;

import java.sql.*;  

public class JdbcExample {
	private Connection con = null;
	private Statement stmt;
	private ResultSet rs;
	
	public void printTableData(String tableName) {
		DB2ConnectionManager connectionManager = new DB2ConnectionManager();
    	con = connectionManager.connect("SAMPLE");
    	if (con != null) {
    		try {                                                                        
		    	stmt = con.createStatement();                                            
		    	System.out.println("**** Created JDBC Statement object");
	
		    	rs = stmt.executeQuery("SELECT * FROM " + tableName);                    
		    	System.out.println("**** Created JDBC ResultSet object");
	
		    	String rowNo;
		    	while (rs.next()) {
		    		rowNo = rs.getString(1);
		    		System.out.println("ROW number = " + rowNo);
		    	}
		    	System.out.println("**** Fetched all rows from JDBC ResultSet");
		    	rs.close();
		    	System.out.println("**** Closed JDBC ResultSet");
		      
		    	stmt.close();
		    	System.out.println("**** Closed JDBC Statement");
		    	
		    	connectionManager.commitAndClose();
	    	}
		    catch (Exception e) {
		      e.printStackTrace();
		    }
    	}
	}
	
	public static void main(String[] args) {
		JdbcExample jdbcExample = new JdbcExample();
		jdbcExample.printTableData("ORG");
	}
}
