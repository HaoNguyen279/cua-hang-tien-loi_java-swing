/*
 * @ (#) ConnectDB.java   1.0     4/21/2025
 * Copyright (c) 2025 IUH, All rights reserved.
 */


package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static Connection getConnection(){
        return con;
    }
    public void connect() {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=CHTL;encrypt=true;trustServerCertificate=true;";
        String user = "sa1";
        String pwd = "password";
        try {
            con = DriverManager.getConnection(url,user,pwd);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void disconnect(){
        if(con != null)
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}
