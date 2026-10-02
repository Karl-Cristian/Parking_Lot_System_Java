/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.service;

import parking.system.abstracts.Vehicle;
import java.util.Date;

public class FeeCalculatorService {
    
    public double calculateFee(Vehicle vehicle, Date exitTime) {
        if (vehicle == null) return 0;
        return vehicle.calculateFee(exitTime);
    }
    
    public long getDurationMinutes(Vehicle vehicle, Date exitTime) {
        if (vehicle == null) return 0;
        long durationMillis = exitTime.getTime() - vehicle.getEntryTime().getTime();
        return durationMillis / (1000 * 60);
    }
    
    public String formatDuration(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        if (hours > 0) {
            return hours + " hour(s) and " + remainingMinutes + " minute(s)";
        }
        return minutes + " minute(s)";
    }
}