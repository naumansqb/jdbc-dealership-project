package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        String q= """
                INSERT INTO sales_contracts
                (VIN, sale_date, price)
                VALUES (?, ?, ?)
                """;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement salesStatement = connection.prepareStatement(q))
        {
            salesStatement.setString(1, salesContract.getVin());
            salesStatement.setDate(2, Date.valueOf(salesContract.getSaleDate()));
            salesStatement.setDouble(3, salesContract.getPrice());
            salesStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
