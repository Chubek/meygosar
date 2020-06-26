package com.chubak.meygosar;

import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Location {
    public int shelf;
    public int row;
    public int column;
    private String uuid;

    public Location(int shelf, int row, int column) {
        this.shelf = shelf;
        this.row = row;
        this.column = column;
        this.uuid = UUID.randomUUID().toString();
    }

    public Location(String uuid) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM Locations WHERE UUID='%s'", uuid));
            while (rs.next()) {

                this.shelf = rs.getInt("Shelf");
                this.shelf = rs.getInt("Row");
                this.shelf = rs.getInt("Column");

            }
        } catch (SQLException e) {

            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }
}
