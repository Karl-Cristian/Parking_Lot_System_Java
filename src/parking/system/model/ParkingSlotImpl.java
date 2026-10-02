/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.model;

import parking.system.abstracts.ParkingSlot;
import parking.system.abstracts.Vehicle;

public class ParkingSlotImpl extends ParkingSlot {
    
    public ParkingSlotImpl(int slotNumber) {
        super(slotNumber);
    }
    
    @Override
    public boolean parkVehicle(Vehicle vehicle) {
        if (!isOccupied && vehicle != null) {
            this.parkedVehicle = vehicle;
            this.isOccupied = true;
            vehicle.setParkingSpot(slotNumber);
            return true;
        }
        return false;
    }
    
    @Override
    public Vehicle removeVehicle() {
        if (isOccupied) {
            Vehicle vehicle = this.parkedVehicle;
            this.parkedVehicle = null;
            this.isOccupied = false;
            return vehicle;
        }
        return null;
    }
    
    @Override
    public String getSlotStatus() {
        if (isOccupied) {
            return "OCCUPIED - " + parkedVehicle.getVehicleType() + 
                   " (" + parkedVehicle.getPlateNumber() + ")";
        }
        return "VACANT";
    }
}
