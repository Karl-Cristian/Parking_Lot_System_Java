/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.model;

import parking.system.abstracts.Vehicle;

public class Motorcycle extends Vehicle {
    private static final double HOURLY_RATE = 20.0;
    private String engineCapacity;
    
    public Motorcycle(String plateNumber) {
        super(plateNumber);
        this.engineCapacity = "125cc";
    }
    
    public Motorcycle(String plateNumber, String engineCapacity) {
        super(plateNumber);
        this.engineCapacity = engineCapacity;
    }
    
    @Override
    public String getVehicleType() {
        return "MOTORCYCLE";
    }
    
    @Override
    public double getHourlyRate() {
        return HOURLY_RATE;
    }
    
    @Override
    public String getDescription() {
        return engineCapacity + " Motorcycle";
    }
}
