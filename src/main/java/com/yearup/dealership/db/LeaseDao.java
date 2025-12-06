package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        String query = """
                INSERT INTO lease_contracts
                (VIN, lease_start, lease_end, monthly_payment)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement leaseStatement = connection.prepareStatement(query)) {
            leaseStatement.setString(1, leaseContract.getVin());
            leaseStatement.setDate(2, Date.valueOf(leaseContract.getLeaseStart()));
            leaseStatement.setDate(3, Date.valueOf(leaseContract.getLeaseEnd()));
            leaseStatement.setDouble(4, leaseContract.getMonthlyPayment());
            leaseStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
