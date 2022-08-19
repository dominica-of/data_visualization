package com.group3.grapher.database.models;

import com.group3.grapher.database.Connections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    public static String email;

    Connection conn = Connections.getConnection();

    public User() {}

    public boolean createUser(String username, String email, String password){
        Statement statement;
        try {
            statement = conn.createStatement();
            String sql = String.format("INSERT INTO users (email, username, password) " +
                    "VALUES ('%s', '%s', '%s')", email, username, password);
            int rows = statement.executeUpdate(sql);
            if(rows>0){
                User.email = email;
                return true;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("Statement creation/execution failed");
        }
        return false;
    }

    public boolean login(String email, String password){
        try {
        Statement s = conn.createStatement();
        String sql = String.format("SELECT * FROM users WHERE email='%s'",email);
        ResultSet result = s.executeQuery(sql);
        if(result.next()){
            System.out.println(password);
            System.out.println(result.getString("password"));
            if(password.equals(result.getString("password"))){
                User.email = email;
                return true;
            }
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
