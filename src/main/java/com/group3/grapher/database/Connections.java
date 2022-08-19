package com.group3.grapher.database;
import java.sql.*;

public class Connections {

    public static Connection conn = null;

    public Connections() {
    }

    public static Connection getConnection(){

        if(conn == null){
            String jdbcURL = "jdbc:postgresql://localhost:5432/grapher";
            String username = "postgres";
            String password = "2001";

            try {
                conn = DriverManager.getConnection(jdbcURL,username,password);
                System.out.println("Connected");

                return conn;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                System.out.println("Not connected");
                e.printStackTrace();
                return null;
            }

        }else{
            return conn;
        }
    }
}

//                Statement s = n.createStatement();
//                String fname = "ha";
//                String lname = "Haha";
//                String email = "haha.ha@gm.om";
//                String sql = String.format("INSERT INTO vot (fname,lname,email) VALUES ('%s', '%s', '%s')", fname, lname, email) ;
//                int rows = s.executeUpdate(sql);

//                Statement s = n.createStatement();
//
//                String sql = String.format("SELECT * FROM vot");
//                ResultSet result = s.executeQuery(sql);
//
//                while(result.next()) {
//                    String row = String.format("%d\t%s\t%s\t%s", result.getInt("id"), result.getString("fname"), result.getString("lname"), result.getString("email"));
//                    System.out.println(row);
//                }


//                n.close();
