
package controller;

import java.sql.*;




public class ServerDAO {

    private Connection dbConnection;
    
    public ServerDAO() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ql"
//                    + "?useUnicode=true&characterEncoding=utf-8","root","123456");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/ql";
            String username = "root";
            String password = "123456";
            this.dbConnection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find database driver class: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Could not connect to database: " + e.getMessage());
        }
        System.out.println("Database connected");
    }
}