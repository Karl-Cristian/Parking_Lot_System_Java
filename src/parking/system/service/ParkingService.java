/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.service;

import parking.system.abstracts.Vehicle;
import parking.system.model.ParkingSlotImpl;
import java.util.HashMap;
import java.util.Map;

public class ParkingService {
    private Map<Integer, ParkingSlotImpl> slots;
    private static final int TOTAL_SLOTS = 5;
    
    public ParkingService() {
        slots = new HashMap<>();
        for (int i = 1; i <= TOTAL_SLOTS; i++) {
            slots.put(i, new ParkingSlotImpl(i));
        }
    }
    
    public int parkVehicle(Vehicle vehicle) {
        for (int i = 1; i <= TOTAL_SLOTS; i++) {
            if (!slots.get(i).isOccupied()) {
                slots.get(i).parkVehicle(vehicle);
                return i;
            }
        }
        return -1;
    }
    
    public Vehicle removeVehicle(int slotNumber) {
        if (slots.containsKey(slotNumber) && slots.get(slotNumber).isOccupied()) {
            return slots.get(slotNumber).removeVehicle();
        }
        return null;
    }
    
    public boolean isSlotAvailable(int slotNumber) {
        return slots.containsKey(slotNumber) && !slots.get(slotNumber).isOccupied();
    }
    
    public int getAvailableSlotCount() {
        int count = 0;
        for (int i = 1; i <= TOTAL_SLOTS; i++) {
            if (!slots.get(i).isOccupied()) count++;
        }
        return count;
    }
    
    public ParkingSlotImpl getSlot(int slotNumber) {
        return slots.get(slotNumber);
    }
    
    public Map<Integer, ParkingSlotImpl> getAllSlots() {
        return slots;
    }
    
    public int getTotalSlots() {
        return TOTAL_SLOTS;
    }
}
