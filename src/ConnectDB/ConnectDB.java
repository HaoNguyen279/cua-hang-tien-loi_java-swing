/*
 * @ (#) ConnectDB.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @description:
 * @author:
 * @date:  4/21/2025
 * @version:    1.0
 */
public class ConnectDB {
    public static Connection con = null;
    private static ConnectDB instance =  new ConnectDB();
    public static ConnectDB getInstance() {
        return instance;
    }
    public void connect() {
        String url =  "jdbc:sqlserver://localhost:1433;databasename=CHTL";
        String user = "sa1";
        String pwd = "password";
        try {
            con = DriverManager.getConnection(url,user,pwd);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void disconnect() {
    	if(con!=null) {
    		try {
    			con.close();
    		}catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    public static Connection getConnection() {
    	return con;
    }

}
