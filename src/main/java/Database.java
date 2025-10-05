package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {
    private static final String url = "jdbc:sqlite:data/petShop.db";
    private Database (){};

    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(url);
        return con;
    }
}