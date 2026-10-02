/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.abstracts;

import java.util.Date;

public abstract class Vehicle {
    protected String plateNumber;
    protected Date entryTime;
    protected Date exitTime;
    protected int parkingSpot;
    protected double fee;
    
    public Vehicle(String plateNumber) {
        this.plateNumber = plateNumber;
        this.entryTime = new Date();
    }
    
    public abstract String getVehicleType();
    public abstract double getHourlyRate();
    public abstract String getDescription();
    
    public double calculateFee(Date exitTime) {
        long durationMillis = exitTime.getTime() - entryTime.getTime();
        long hours = durationMillis / (1000 * 60 * 60);
        if (durationMillis % (1000 * 60 * 60) > 0) hours++;
        if (hours == 0) hours = 1;
        return hours * getHourlyRate();
    }
    
    // Getters and Setters
    public String getPlateNumber() { return plateNumber; }
    public Date getEntryTime() { return entryTime; }
    public void setEntryTime(Date entryTime) { this.entryTime = entryTime; }
    public Date getExitTime() { return exitTime; }
    public void setExitTime(Date exitTime) { this.exitTime = exitTime; }
    public int getParkingSpot() { return parkingSpot; }
    public void setParkingSpot(int parkingSpot) { this.parkingSpot = parkingSpot; }
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}
