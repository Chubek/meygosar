package com.chubak.meygosar;

import java.sql.*;
import java.util.UUID;

public class IDHolder implements DatabaseObject {
    private String vineyardId;
    private String inventoryId;
    private String locationId;
    private String propsId;
    public String wineId;

    public IDHolder(String vineyardId, String inventoryId, String locationId, String propsId, String wineId) {

        this.vineyardId = vineyardId;
        this.inventoryId = inventoryId;
        this.locationId = locationId;
        this.propsId = propsId;
        this.wineId = UUID.randomUUID().toString();

        insertIntoDatabase();
    }

    public IDHolder(String wineId) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:meygosar.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery(String.format("SELECT * FROM IDs HERE WineID='%s'", wineId));
            while (rs.next()) {

                this.vineyardId = rs.getString("VineyardID");
                this.inventoryId = rs.getString("InventoryID");
                this.locationId = rs.getString("LocationID");
                this.propsId = rs.getString("PropsID");
                this.wineId = wineId;

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

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS IDs(" +
                    "WineID varchar(255)" +
                    "VineyardID varchar(255)" +
                    "InventoryID varchar(255)" +
                    "LocationID varchar(255)" +
                    "PropsID varchar(255)");
            statement.executeUpdate(String.format("INSERT INTO Location (WineID, VineyardID, InventoryID, LocationID, PropsID) VALUES (%s, %s, %s, %s, %s)",
                    this.wineId, this.vineyardId, this.inventoryId, this.locationId, this.propsId));


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


    public String getVineyardId() {
        return vineyardId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getPropsId() {
        return propsId;
    }

    public String getInventoryId() {
        return inventoryId;
    }
}
