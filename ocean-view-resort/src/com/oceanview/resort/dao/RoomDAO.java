package com.oceanview.resort.dao;

import com.oceanview.resort.database.DatabaseConnection;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.util.FileManager;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomDAO {
    private static final String FILENAME = "rooms.dat";
    private FileManager fileManager = FileManager.getInstance();
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    
    public void save(Room room) throws IOException, ClassNotFoundException {
        if (dbConnection.isDatabaseAvailable()) {
            try {
                saveToDatabase(room);
                return;
            } catch (SQLException e) {
                System.err.println("Database save failed, using file storage: " + e.getMessage());
            }
        }
        saveToFile(room);
    }
    
    private void saveToDatabase(Room room) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement check = conn.prepareStatement(
            "SELECT room_number FROM rooms WHERE room_number = ?")) {
            check.setString(1, room.getRoomNumber());
            ResultSet rs = check.executeQuery();
            
            if (rs.next()) {
                updateInDatabase(room);
            } else {
                insertIntoDatabase(room);
            }
        }
    }
    
    private void insertIntoDatabase(Room room) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO rooms (room_number, room_type, is_available, capacity, features, base_rate) " +
            "VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, room.getRoomNumber());
            ps.setString(2, room.getRoomType() != null ? room.getRoomType().name() : "SINGLE");
            ps.setBoolean(3, room.isAvailable());
            ps.setInt(4, room.getCapacity());
            ps.setString(5, room.getFeatures());
            ps.setDouble(6, room.getRate());
            ps.executeUpdate();
        }
    }
    
    private void updateInDatabase(Room room) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE rooms SET room_type = ?, is_available = ?, capacity = ?, features = ?, base_rate = ? " +
            "WHERE room_number = ?")) {
            ps.setString(1, room.getRoomType() != null ? room.getRoomType().name() : "SINGLE");
            ps.setBoolean(2, room.isAvailable());
            ps.setInt(3, room.getCapacity());
            ps.setString(4, room.getFeatures());
            ps.setDouble(5, room.getRate());
            ps.setString(6, room.getRoomNumber());
            ps.executeUpdate();
        }
    }
    
    private void saveToFile(Room room) throws IOException, ClassNotFoundException {
        List<Room> rooms = findAllFromFile();
        for (int i = 0; i < rooms.size(); i++) {
            if (rooms.get(i).getRoomNumber().equals(room.getRoomNumber())) {
                rooms.set(i, room);
                fileManager.writeListToFile(rooms, FILENAME);
                return;
            }
        }
        rooms.add(room);
        fileManager.writeListToFile(rooms, FILENAME);
    }
    
    public Room findByRoomNumber(String roomNumber) throws IOException, ClassNotFoundException {
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findFromDatabase(roomNumber);
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        return findAllFromFile().stream()
            .filter(r -> r.getRoomNumber().equals(roomNumber))
            .findFirst().orElse(null);
    }
    
    private Room findFromDatabase(String roomNumber) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM rooms WHERE room_number = ?")) {
            ps.setString(1, roomNumber);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToRoom(rs);
            }
        }
        return null;
    }
    
    public List<Room> findAll() throws IOException, ClassNotFoundException {
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findAllFromDatabase();
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        List<Room> rooms = findAllFromFile();
        if (rooms.isEmpty()) {
            initializeDefaultRooms();
            rooms = findAllFromFile();
        }
        return rooms;
    }
    
    private List<Room> findAllFromDatabase() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM rooms ORDER BY room_number")) {
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        }
        return rooms;
    }
    
    private List<Room> findAllFromFile() throws IOException, ClassNotFoundException {
        return fileManager.readListFromFile(FILENAME);
    }
    
    public List<Room> findAvailableRooms() throws IOException, ClassNotFoundException {
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findAvailableFromDatabase();
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        return findAll().stream().filter(Room::isAvailable).collect(Collectors.toList());
    }
    
    private List<Room> findAvailableFromDatabase() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM rooms WHERE is_available = true ORDER BY room_number")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        }
        return rooms;
    }
    
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        String roomTypeStr = rs.getString("room_type");
        Room.RoomType roomType = Room.RoomType.SINGLE;
        try {
            roomType = Room.RoomType.valueOf(roomTypeStr);
        } catch (Exception e) {
            // Use default
        }
        
        return new Room(
            rs.getString("room_number"),
            roomType,
            rs.getBoolean("is_available"),
            rs.getInt("capacity"),
            rs.getString("features")
        );
    }
    
    private void initializeDefaultRooms() throws IOException, ClassNotFoundException {
        List<Room> rooms = new ArrayList<>();
        for (int i = 101; i <= 110; i++) {
            rooms.add(new Room("R" + i, Room.RoomType.SINGLE, true, 1, "AC, TV, WiFi"));
        }
        for (int i = 201; i <= 210; i++) {
            rooms.add(new Room("R" + i, Room.RoomType.DOUBLE, true, 2, "AC, TV, WiFi, Mini Bar"));
        }
        for (int i = 301; i <= 305; i++) {
            rooms.add(new Room("R" + i, Room.RoomType.DELUXE, true, 2, "AC, TV, WiFi, Mini Bar, Balcony"));
        }
        for (int i = 401; i <= 403; i++) {
            rooms.add(new Room("R" + i, Room.RoomType.SUITE, true, 4, "AC, TV, WiFi, Mini Bar, Balcony, Living Room"));
        }
        fileManager.writeListToFile(rooms, FILENAME);
    }
}
