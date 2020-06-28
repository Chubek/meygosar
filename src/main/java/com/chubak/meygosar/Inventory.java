package com.chubak.meygosar;

import java.util.Date;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Inventory implements DatabaseObject {
    private Date deliveryDate;
    private Date serveDate;
    private String serialNumber;
    private Boolean served;
    public String uuid;

    public Inventory(Date deliveryDate, String serialNumber) {
        this.deliveryDate = deliveryDate;
        this.serialNumber = serialNumber;
        this.uuid = UUID.randomUUID().toString();
        this.served = false;


        insertIntoDatabase();
    }

    public Inventory(String uuid) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM Inventories WHERE UUID='%s'", uuid));
            while (rs.next()) {

                this.deliveryDate = rs.getDate("DeliveryDate");
                this.serveDate = rs.getDate("Date");
                this.serialNumber = rs.getString("SerialNumber");
                this.served = rs.getBoolean("Served");
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

    public void serveWine(Date date) {
        this.serveDate = date;
        this.served = true;

        updateServed();
    }

    private void updateServed() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);


            statement.executeUpdate(String.format("UPDATE Inventories SET DeliveryDate='%s' Served=1 WHERE UUID='%s'", this.deliveryDate, this.uuid));


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

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Inventories(" +
                    "UUID varchar(255)" +
                    "DeliveryDate Datetime" +
                    "ServeDate Datetime" +
                    "SerialNumber varchar(255)" +
                    "Served tinyint)");
            statement.executeUpdate(String.format("INSERT INTO Location (UUID, DeliveryDate, ServeDate, SerialNumber, Served) VALUES (%s, %s, %s, %s, %s)",
                    this.uuid, this.deliveryDate, null, this.serialNumber, 0));


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
