/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parking.system.gui;

import parking.system.abstracts.Vehicle;
import parking.system.model.*;
import parking.system.service.*;
import parking.system.dao.ParkingDAO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ParkingSystemGUI extends JFrame {
    private ParkingService parkingService;
    private FeeCalculatorService feeService;
    private ParkingDAO parkingDAO;
    
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JLabel[] slotCards;
    private JTextField plateNumberField;
    private JComboBox<String> vehicleTypeCombo;
    private JTextField colorField;
    private JTextField brandField;
    private JTextField exitPlateField;
    private JTextArea receiptArea;
    private JTable activeTable;
    private DefaultTableModel tableModel;
    private JLabel statusBar;
    
    public ParkingSystemGUI() {
        parkingService = new ParkingService();
        feeService = new FeeCalculatorService();
        parkingDAO = new ParkingDAO();
        
        initUI();
        loadExistingVehicles();
        refreshDisplay();
    }
    
    private void initUI() {
        setTitle("Parking Lot Management System | OOP Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 242, 245));
        
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabbedPane.addTab("Parking Slots", createSlotsTab());
        tabbedPane.addTab("Vehicle Registration", createRegistrationTab());
        tabbedPane.addTab("Active Vehicles", createActiveVehiclesTab());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        JPanel receiptPanel = createReceiptPanel();
        mainPanel.add(receiptPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 150, 243));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel title = new JLabel("PARKING LOT MANAGEMENT SYSTEM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        
        JLabel subtitle = new JLabel("OOP Project");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(title, BorderLayout.NORTH);
        textPanel.add(subtitle, BorderLayout.SOUTH);
        
        header.add(textPanel, BorderLayout.WEST);
        
        return header;
    }
    
    private JPanel createSlotsTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 242, 245));
        
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gridPanel.setBackground(new Color(240, 242, 245));
        
        slotCards = new JLabel[6];
        
        for (int i = 1; i <= 5; i++) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            
            JLabel slotNum = new JLabel("SLOT " + i, SwingConstants.CENTER);
            slotNum.setFont(new Font("Segoe UI", Font.BOLD, 18));
            
            JLabel status = new JLabel("VACANT", SwingConstants.CENTER);
            status.setFont(new Font("Segoe UI", Font.BOLD, 14));
            status.setForeground(new Color(76, 175, 80));
            
            card.add(slotNum, BorderLayout.NORTH);
            card.add(status, BorderLayout.CENTER);
            
            slotCards[i] = status;
            gridPanel.add(card);
        }
        
        panel.add(gridPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createRegistrationTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 242, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(8, 8, 8, 8);
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel formTitle = new JLabel("REGISTER NEW VEHICLE");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(new Color(33, 150, 243));
        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.gridwidth = 2;
        formPanel.add(formTitle, fgbc);
        
        fgbc.gridy = 1;
        fgbc.gridwidth = 1;
        formPanel.add(new JLabel("Plate Number:"), fgbc);
        fgbc.gridx = 1;
        plateNumberField = new JTextField(15);
        formPanel.add(plateNumberField, fgbc);
        
        fgbc.gridx = 0;
        fgbc.gridy = 2;
        formPanel.add(new JLabel("Vehicle Type:"), fgbc);
        fgbc.gridx = 1;
        vehicleTypeCombo = new JComboBox<>(new String[]{"CAR", "MOTORCYCLE", "TRUCK"});
        formPanel.add(vehicleTypeCombo, fgbc);
        
        fgbc.gridx = 0;
        fgbc.gridy = 3;
        formPanel.add(new JLabel("Color:"), fgbc);
        fgbc.gridx = 1;
        colorField = new JTextField(15);
        formPanel.add(colorField, fgbc);
        
        fgbc.gridx = 0;
        fgbc.gridy = 4;
        formPanel.add(new JLabel("Brand:"), fgbc);
        fgbc.gridx = 1;
        brandField = new JTextField(15);
        formPanel.add(brandField, fgbc);
        
        fgbc.gridx = 0;
        fgbc.gridy = 5;
        fgbc.gridwidth = 2;
        JButton registerBtn = new JButton("REGISTER VEHICLE");
        registerBtn.setBackground(new Color(76, 175, 80));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.addActionListener(e -> registerVehicle());
        formPanel.add(registerBtn, fgbc);
        
        fgbc.gridy = 6;
        JLabel exitTitle = new JLabel("EXIT VEHICLE");
        exitTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitTitle.setForeground(new Color(244, 67, 54));
        formPanel.add(exitTitle, fgbc);
        
        fgbc.gridy = 7;
        JPanel exitPanel = new JPanel(new FlowLayout());
        exitPlateField = new JTextField(12);
        JButton exitBtn = new JButton("PROCESS EXIT");
        exitBtn.setBackground(new Color(244, 67, 54));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.addActionListener(e -> processExit());
        exitPanel.add(exitPlateField);
        exitPanel.add(exitBtn);
        formPanel.add(exitPanel, fgbc);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(formPanel, gbc);
        
        return panel;
    }
    
    private JPanel createActiveVehiclesTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 242, 245));
        
        String[] columns = {"Plate Number", "Vehicle Type", "Slot", "Entry Time"};
        tableModel = new DefaultTableModel(columns, 0);
        activeTable = new JTable(tableModel);
        activeTable.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(activeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JButton refreshBtn = new JButton("REFRESH");
        refreshBtn.addActionListener(e -> refreshDisplay());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReceiptPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 200));
        panel.setBorder(BorderFactory.createTitledBorder("RECEIPT"));
        
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        statusBar = new JLabel("System Ready");
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        panel.add(statusBar, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void registerVehicle() {
        String plateNumber = plateNumberField.getText().trim().toUpperCase();
        String vehicleType = (String) vehicleTypeCombo.getSelectedItem();
        String color = colorField.getText().trim();
        String brand = brandField.getText().trim();
        
        if (plateNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter plate number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Vehicle vehicle;
        switch (vehicleType) {
            case "CAR":
                vehicle = new Car(plateNumber, color.isEmpty() ? "Unknown" : color, brand.isEmpty() ? "Unknown" : brand);
                break;
            case "MOTORCYCLE":
                vehicle = new Motorcycle(plateNumber);
                break;
            case "TRUCK":
                vehicle = new Truck(plateNumber);
                break;
            default:
                vehicle = new Car(plateNumber);
        }
        
        int slotNumber = parkingService.parkVehicle(vehicle);
        
        if (slotNumber == -1) {
            JOptionPane.showMessageDialog(this, "No available parking slots!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (parkingDAO.saveVehicle(vehicle)) {
            showReceipt("ENTRY", vehicle, slotNumber, null, 0, 0);
            refreshDisplay();
            plateNumberField.setText("");
            colorField.setText("");
            brandField.setText("");
            JOptionPane.showMessageDialog(this, "Vehicle registered!\nSlot: " + slotNumber, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void processExit() {
        String plateNumber = exitPlateField.getText().trim().toUpperCase();
        
        if (plateNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter plate number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Vehicle vehicle = parkingDAO.getVehicleByPlate(plateNumber);
        
        if (vehicle == null) {
            JOptionPane.showMessageDialog(this, "Vehicle not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Date exitTime = new Date();
        double fee = feeService.calculateFee(vehicle, exitTime);
        long duration = feeService.getDurationMinutes(vehicle, exitTime);
        
        if (parkingDAO.updateVehicleExit(plateNumber, exitTime, fee)) {
            parkingService.removeVehicle(vehicle.getParkingSpot());
            showReceipt("EXIT", vehicle, vehicle.getParkingSpot(), exitTime, fee, duration);
            refreshDisplay();
            exitPlateField.setText("");
            JOptionPane.showMessageDialog(this, "Vehicle exited!\nFee: PHP " + String.format("%.2f", fee), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showReceipt(String type, Vehicle vehicle, int slot, Date exitTime, double fee, long duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        receiptArea.setText("");
        receiptArea.append("╔════════════════════════════════════════════╗\n");
        receiptArea.append("║     PARKING " + type + " RECEIPT                ║\n");
        receiptArea.append("╠════════════════════════════════════════════╣\n");
        receiptArea.append(String.format("║ Plate: %-32s ║\n", vehicle.getPlateNumber()));
        receiptArea.append(String.format("║ Type: %-33s ║\n", vehicle.getVehicleType()));
        receiptArea.append(String.format("║ Slot: %-34s ║\n", "SLOT " + slot));
        receiptArea.append(String.format("║ Entry: %-33s ║\n", sdf.format(vehicle.getEntryTime())));
        
        if (type.equals("EXIT")) {
            receiptArea.append(String.format("║ Exit: %-34s ║\n", sdf.format(exitTime)));
            receiptArea.append(String.format("║ Duration: %-31s ║\n", feeService.formatDuration(duration)));
            receiptArea.append("╠════════════════════════════════════════════╣\n");
            receiptArea.append(String.format("║ FEE: PHP %-32s ║\n", String.format("%.2f", fee)));
        }
        
        receiptArea.append("╚════════════════════════════════════════════╝\n");
    }
    
    private void refreshDisplay() {
        Map<Integer, parking.system.model.ParkingSlotImpl> slots = parkingService.getAllSlots();
        for (int i = 1; i <= 5; i++) {
            if (slotCards[i] != null) {
                if (slots.get(i).isOccupied()) {
                    slotCards[i].setText("OCCUPIED");
                    slotCards[i].setForeground(new Color(244, 67, 54));
                } else {
                    slotCards[i].setText("VACANT");
                    slotCards[i].setForeground(new Color(76, 175, 80));
                }
            }
        }
        
        tableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        for (Vehicle v : parkingDAO.getAllParkedVehicles()) {
            tableModel.addRow(new Object[]{v.getPlateNumber(), v.getVehicleType(), "Slot " + v.getParkingSpot(), sdf.format(v.getEntryTime())});
        }
        
        statusBar.setText("Active: " + parkingDAO.getAllParkedVehicles().size() + " | Available: " + parkingService.getAvailableSlotCount());
    }
    
    private void loadExistingVehicles() {
        for (Vehicle v : parkingDAO.getAllParkedVehicles()) {
            parkingService.parkVehicle(v);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ParkingSystemGUI().setVisible(true);
        });
    }
}
