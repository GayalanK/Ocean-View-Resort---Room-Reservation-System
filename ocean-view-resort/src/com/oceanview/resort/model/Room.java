package com.oceanview.resort.model;

import java.io.Serializable;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum RoomType {
        SINGLE("Single Room", 5000.0),
        DOUBLE("Double Room", 8000.0),
        DELUXE("Deluxe Room", 12000.0),
        SUITE("Suite", 15000.0);
        
        private final String description;
        private final double baseRate;
        
        RoomType(String description, double baseRate) {
            this.description = description;
            this.baseRate = baseRate;
        }
        
        public String getDescription() { return description; }
        public double getBaseRate() { return baseRate; }
    }
    
    private String roomNumber;
    private RoomType roomType;
    private boolean isAvailable;
    private int capacity;
    private String features;
    
    public Room() {}
    
    public Room(String roomNumber, RoomType roomType, boolean isAvailable, int capacity, String features) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.capacity = capacity;
        this.features = features;
    }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    
    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }
    
    public double getRate() { return roomType != null ? roomType.getBaseRate() : 0.0; }
    
    @Override
    public String toString() {
        return "Room{number='" + roomNumber + "', type='" + (roomType != null ? roomType.getDescription() : "") + 
               "', available=" + isAvailable + ", rate=" + getRate() + "}";
    }
}
