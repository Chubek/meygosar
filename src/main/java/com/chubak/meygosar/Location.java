package com.chubak.meygosar;

import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Location implements DatabaseObject {
    private int shelf;
    private int row;
    private int column;
    public String uuid;

    public Location(int shelf, int row, int column) {
        this.shelf = shelf;
        this.row = row;
        this.column = column;
        this.uuid = UUID.randomUUID().toString();
        insertIntoDatabase();
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
                this.row = rs.getInt("Row");
                this.column = rs.getInt("Column");
                this.uuid = uuid;

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

    @Override
    public void insertIntoDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Location(" +
                    "UUID varchar(255)" +
                    "Shelf int" +
                    "Row int" +
                    "Column int)");
            statement.executeUpdate(String.format("INSERT INTO Location (UUID, Shelf, Row, Column) VALUES (%s, %s, %s, %s)", this.uuid, this.shelf, this.row, this.column));


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

    public int getShelf() {
        return this.shelf;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
