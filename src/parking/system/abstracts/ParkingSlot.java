/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.abstracts;

public abstract class ParkingSlot {
    protected int slotNumber;
    protected boolean isOccupied;
    protected Vehicle parkedVehicle;
    
    public ParkingSlot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.isOccupied = false;
        this.parkedVehicle = null;
    }
    
    public abstract boolean parkVehicle(Vehicle vehicle);
    public abstract Vehicle removeVehicle();
    public abstract String getSlotStatus();
    
    public int getSlotNumber() { return slotNumber; }
    public boolean isOccupied() { return isOccupied; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
}
