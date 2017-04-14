package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB2ConnectionManager {
	private Connection con = null;
	private String url = "jdbc:db2://localhost:50000/";
	private String driver = "com.ibm.db2.jcc.DB2Driver";
	private String user = "db2inst1";
	private String pass = "db2inst1";
	
	public Connection connect(String db){
		try {
			Class.forName(driver);                              
			System.out.println("**** Loaded the JDBC driver");
			con = DriverManager.getConnection (url + db, user, pass);
			con.setAutoCommit(false);
			System.out.println("**** Created a JDBC connection to the data source");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void commitAndClose() {
		try {
			con.commit();
			System.out.println ( "**** Transaction committed" );
		      
			con.close();                                                             
			System.out.println("**** Disconnected from data source");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
