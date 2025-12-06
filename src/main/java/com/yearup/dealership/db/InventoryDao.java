package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        String query = """
                INSERT INTO inventory
                (dealership_id, VIN)
                VALUES (?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement addStatement = connection.prepareStatement(query)) {
            addStatement.setInt(1, dealershipId);
            addStatement.setString(2, vin);
            addStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicleFromInventory(String vin) {
        String query = """
                DELETE FROM inventory
                WHERE VIN = ?
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement removeStatement = connection.prepareStatement(query)) {
            removeStatement.setString(1, vin);
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
