/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.model;

import parking.system.abstracts.Vehicle;

public class Car extends Vehicle {
    private static final double HOURLY_RATE = 30.0;
    private String color;
    private String brand;
    
    public Car(String plateNumber) {
        super(plateNumber);
        this.color = "Unknown";
        this.brand = "Unknown";
    }
    
    public Car(String plateNumber, String color, String brand) {
        super(plateNumber);
        this.color = color;
        this.brand = brand;
    }
    
    @Override
    public String getVehicleType() {
        return "CAR";
    }
    
    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }
    
    @Override
    public String getDescription() {
        return brand + " " + color + " Car";
    }
}
