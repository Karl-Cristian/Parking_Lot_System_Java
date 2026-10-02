/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.dao;

import parking.system.abstracts.Vehicle;
import parking.system.model.*;
import parking.system.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingDAO {
    
    public boolean saveVehicle(Vehicle vehicle) {
        String query = "INSERT INTO vehicles (plate_number, vehicle_type, parking_spot, entry_time, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, vehicle.getPlateNumber());
            pstmt.setString(2, vehicle.getVehicleType());
            pstmt.setInt(3, vehicle.getParkingSpot());
            // Convert java.util.Date to java.sql.Timestamp
            pstmt.setTimestamp(4, new Timestamp(vehicle.getEntryTime().getTime()));
            pstmt.setString(5, "PARKED");
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Vehicle getVehicleByPlate(String plateNumber) {
        String query = "SELECT * FROM vehicles WHERE plate_number = ? AND status = 'PARKED'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, plateNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String type = rs.getString("vehicle_type");
                Vehicle vehicle = createVehicleByType(type, plateNumber);
                vehicle.setParkingSpot(rs.getInt("parking_spot"));
                Timestamp timestamp = rs.getTimestamp("entry_time");
                if (timestamp != null) {
                    vehicle.setEntryTime(new Date(timestamp.getTime()));
                }
                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Vehicle createVehicleByType(String type, String plateNumber) {
        switch (type.toUpperCase()) {
            case "CAR": return new Car(plateNumber);
            case "MOTORCYCLE": return new Motorcycle(plateNumber);
            case "TRUCK": return new Truck(plateNumber);
            default: return new Car(plateNumber);
        }
    }
    
    public boolean updateVehicleExit(String plateNumber, Date exitTime, double fee) {
        String query = "UPDATE vehicles SET exit_time = ?, fee = ?, status = 'EXITED' WHERE plate_number = ? AND status = 'PARKED'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setTimestamp(1, new Timestamp(exitTime.getTime()));
            pstmt.setDouble(2, fee);
            pstmt.setString(3, plateNumber);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Vehicle> getAllParkedVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE status = 'PARKED'";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Vehicle vehicle = createVehicleByType(rs.getString("vehicle_type"), rs.getString("plate_number"));
                vehicle.setParkingSpot(rs.getInt("parking_spot"));
                Timestamp timestamp = rs.getTimestamp("entry_time");
                if (timestamp != null) {
                    vehicle.setEntryTime(new Date(timestamp.getTime()));
                }
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
}
