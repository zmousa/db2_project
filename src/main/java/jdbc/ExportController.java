package jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
 
 
public class ExportController {
	private Connection con = null;
	
    public void exportCSV(String folderLocation) {
    	DB2ConnectionManager connectionManager = new DB2ConnectionManager();
    	con = connectionManager.connect("DEV");
    	if (con != null) {
    		FileWriter fw ;
        	try {
        		Statement st = con.createStatement();
        		ResultSet res = st.executeQuery("SELECT TABNAME FROM SYSCAT.TABLES WHERE TABSCHEMA = 'DB2INST1'");
               
        		List <String> tableNameList = new ArrayList<String>();
        		while(res.next()) {
        			tableNameList.add(res.getString(1));
        		}
               
        		for(String tableName:tableNameList) {
                    System.out.println(tableName);
                    res = st.executeQuery("SELECT * FROM " + tableName);
                    int colunmCount = getColumnCount(res);
                    try {
                        fw = new FileWriter(folderLocation + "" + tableName + ".csv");
                        for(int i=1 ; i<= colunmCount ;i++) {
                            fw.append(res.getMetaData().getColumnName(i));
                            fw.append(",");
                        }
                        fw.append(System.getProperty("line.separator"));
                         
                        while(res.next()) {
                            for(int i=1;i<=colunmCount;i++) {
                                if(res.getObject(i) != null) {
	                                String data= res.getObject(i).toString();
	                                fw.append(data) ;
	                                fw.append(",");
                                }
                                else {
                                    String data= "null";
                                    fw.append(data) ;
                                    fw.append(",");
                                }
                            }
                            fw.append(System.getProperty("line.separator"));
                        }
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        		connectionManager.commitAndClose();
        	}
        	catch (Exception e){
        		e.printStackTrace();
        	}
    	}
    }
 
    private int getColumnCount(ResultSet res) throws SQLException {
        return res.getMetaData().getColumnCount();
    }
    
    public static void main(String[] args) {
    	ExportController exportController = new ExportController();
    	exportController.exportCSV("output/");
	}
}