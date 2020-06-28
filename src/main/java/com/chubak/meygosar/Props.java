package com.chubak.meygosar;


import java.sql.*;
import java.util.UUID;

public class Props implements DatabaseObject {
    private String name;
    private WineType wineType;
    private int year;
    public String uuid;

    public Props(String name, int year, WineType wineType) {

        this.name = name;
        this.year = year;
        this.wineType = wineType;
        this.uuid = UUID.randomUUID().toString();

        insertIntoDatabase();
    }

    public Props(String uuid) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM Props WHERE UUID='%s'", uuid));
            while (rs.next()) {

                this.year = rs.getInt("Year");
                this.name = rs.getString("Name");
                this.wineType = WineType.valueOf(rs.getString("WineType"));
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

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Props(" +
                    "UUID varchar(255)" +
                    "Name varchar(255)" +
                    "Year int" +
                    "WineType varchar(255)");
            statement.executeUpdate(String.format("INSERT INTO Location (UUID, Name, Year, WineType) VALUES (%s, %s, %s, %s)",
                    this.uuid, this.name, this.year, this.wineType.name()));


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
