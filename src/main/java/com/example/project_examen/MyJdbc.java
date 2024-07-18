package com.example.project_examen;

import java.sql.*;

public class MyJdbc {
    public static Connection conn() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_examen",
                    "sosza",
                    "OxyContin"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
