package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        String query = """
                INSERT INTO vehicles
                (VIN, make, model, year, SOLD, color, vehicleType, odometer, price)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement addVehicleStatement = connection.prepareStatement(query)) {

            addVehicleStatement.setString(1, vehicle.getVin());
            addVehicleStatement.setString(2, vehicle.getMake());
            addVehicleStatement.setString(3, vehicle.getModel());
            addVehicleStatement.setInt(4, vehicle.getYear());
            addVehicleStatement.setBoolean(5, vehicle.isSold());
            addVehicleStatement.setString(6, vehicle.getColor());
            addVehicleStatement.setString(7, vehicle.getVehicleType());
            addVehicleStatement.setInt(8, vehicle.getOdometer());
            addVehicleStatement.setDouble(9, vehicle.getPrice());

            addVehicleStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicle(String VIN) {
        String query = """
            DELETE FROM vehicles
            WHERE VIN = ?
            """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, VIN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE price BETWEEN
                ? AND ?
                """;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement searchByPriceStatement = connection.prepareStatement(q)) {
            searchByPriceStatement.setDouble(1,minPrice);
            searchByPriceStatement.setDouble(2,maxPrice);
            try(ResultSet resultSet = searchByPriceStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE make = ?
                AND
                model = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement searchByMakeModelStatement = connection.prepareStatement(q)) {
            searchByMakeModelStatement.setString(1,make);
            searchByMakeModelStatement.setString(2,model);
            try(ResultSet resultSet = searchByMakeModelStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE year BETWEEN ? AND ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement searchByYearRangeStatement = connection.prepareStatement(q)) {
            searchByYearRangeStatement.setInt(1,minYear);
            searchByYearRangeStatement.setInt(2,maxYear);
            try(ResultSet resultSet = searchByYearRangeStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE color = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement searchByColorStatement = connection.prepareStatement(q)) {
            searchByColorStatement.setString(1,color);
            try(ResultSet resultSet = searchByColorStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE odometer BETWEEN
                ? AND ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement searchByMileageStatement = connection.prepareStatement(q)) {
            searchByMileageStatement.setInt(1,minMileage);
            searchByMileageStatement.setInt(2,maxMileage);
            try(ResultSet resultSet = searchByMileageStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    public List<Vehicle> searchByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String q = """
                Select * FROM vehicles
                WHERE vehicleType = ?
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement searchByTypeStatement = connection.prepareStatement(q)) {
            searchByTypeStatement.setString(1,type);
            try(ResultSet resultSet = searchByTypeStatement.executeQuery()){
                while(resultSet.next()){
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
