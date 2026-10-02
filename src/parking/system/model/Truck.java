/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.model;

import parking.system.abstracts.Vehicle;

public class Truck extends Vehicle {
    private static final double HOURLY_RATE = 50.0;
    private double weight;
    private String cargoType;
    
    public Truck(String plateNumber) {
        super(plateNumber);
        this.weight = 0;
        this.cargoType = "General";
    }
    
    public Truck(String plateNumber, double weight, String cargoType) {
        super(plateNumber);
        this.weight = weight;
        this.cargoType = cargoType;
    }
    
    @Override
    public String getVehicleType() {
        return "TRUCK";
    }
    
    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }
    
    @Override
    public String getDescription() {
        return weight + " tons " + cargoType + " Truck";
    }
}
