package com.oceanview.resort.dao;

import com.oceanview.resort.database.DatabaseConnection;
import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.model.Room;
import com.oceanview.resort.util.FileManager;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationDAO {
    private static final String FILENAME = "reservations.dat";
    private FileManager fileManager = FileManager.getInstance();
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    
    public void save(Reservation reservation) throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                saveToDatabase(reservation);
                return;
            } catch (SQLException e) {
                System.err.println("Database save failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        saveToFile(reservation);
    }
    
    private void saveToDatabase(Reservation reservation) throws SQLException {
        Connection conn = dbConnection.getConnection();
        
        // Check if reservation exists
        try (PreparedStatement check = conn.prepareStatement(
            "SELECT reservation_number FROM reservations WHERE reservation_number = ?")) {
            check.setString(1, reservation.getReservationNumber());
            ResultSet rs = check.executeQuery();
            
            if (rs.next()) {
                // Update existing
                updateInDatabase(reservation);
            } else {
                // Insert new
                insertIntoDatabase(reservation);
            }
        }
    }
    
    private void insertIntoDatabase(Reservation reservation) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO reservations (reservation_number, guest_name, guest_address, guest_contact, " +
            "guest_email, guest_nic, room_number, room_type, check_in_date, check_out_date, " +
            "number_of_nights, total_amount, status, reservation_date) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            
            Guest guest = reservation.getGuest();
            Room room = reservation.getRoom();
            
            ps.setString(1, reservation.getReservationNumber());
            ps.setString(2, guest != null ? guest.getName() : "");
            ps.setString(3, guest != null ? guest.getAddress() : "");
            ps.setString(4, guest != null ? guest.getContactNumber() : "");
            ps.setString(5, guest != null ? guest.getEmail() : "");
            ps.setString(6, guest != null ? guest.getNicNumber() : "");
            ps.setString(7, room != null ? room.getRoomNumber() : "");
            ps.setString(8, room != null ? room.getRoomType().name() : "");
            ps.setDate(9, reservation.getCheckInDate() != null ? Date.valueOf(reservation.getCheckInDate()) : null);
            ps.setDate(10, reservation.getCheckOutDate() != null ? Date.valueOf(reservation.getCheckOutDate()) : null);
            ps.setInt(11, reservation.getNumberOfNights());
            ps.setDouble(12, reservation.getTotalAmount());
            ps.setString(13, reservation.getStatus());
            ps.setDate(14, reservation.getReservationDate() != null ? Date.valueOf(reservation.getReservationDate()) : Date.valueOf(LocalDate.now()));
            
            ps.executeUpdate();
        }
    }
    
    private void updateInDatabase(Reservation reservation) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE reservations SET guest_name = ?, guest_address = ?, guest_contact = ?, " +
            "guest_email = ?, guest_nic = ?, room_number = ?, room_type = ?, check_in_date = ?, " +
            "check_out_date = ?, number_of_nights = ?, total_amount = ?, status = ? " +
            "WHERE reservation_number = ?")) {
            
            Guest guest = reservation.getGuest();
            Room room = reservation.getRoom();
            
            ps.setString(1, guest != null ? guest.getName() : "");
            ps.setString(2, guest != null ? guest.getAddress() : "");
            ps.setString(3, guest != null ? guest.getContactNumber() : "");
            ps.setString(4, guest != null ? guest.getEmail() : "");
            ps.setString(5, guest != null ? guest.getNicNumber() : "");
            ps.setString(6, room != null ? room.getRoomNumber() : "");
            ps.setString(7, room != null ? room.getRoomType().name() : "");
            ps.setDate(8, reservation.getCheckInDate() != null ? Date.valueOf(reservation.getCheckInDate()) : null);
            ps.setDate(9, reservation.getCheckOutDate() != null ? Date.valueOf(reservation.getCheckOutDate()) : null);
            ps.setInt(10, reservation.getNumberOfNights());
            ps.setDouble(11, reservation.getTotalAmount());
            ps.setString(12, reservation.getStatus());
            ps.setString(13, reservation.getReservationNumber());
            
            ps.executeUpdate();
        }
    }
    
    private void saveToFile(Reservation reservation) throws IOException, ClassNotFoundException {
        List<Reservation> reservations = findAllFromFile();
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getReservationNumber().equals(reservation.getReservationNumber())) {
                reservations.set(i, reservation);
                fileManager.writeListToFile(reservations, FILENAME);
                return;
            }
        }
        reservations.add(reservation);
        fileManager.writeListToFile(reservations, FILENAME);
    }
    
    public Reservation findByReservationNumber(String reservationNumber) throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findFromDatabase(reservationNumber);
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        return findAllFromFile().stream()
            .filter(r -> r.getReservationNumber().equals(reservationNumber))
            .findFirst().orElse(null);
    }
    
    private Reservation findFromDatabase(String reservationNumber) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM reservations WHERE reservation_number = ?")) {
            ps.setString(1, reservationNumber);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }
        }
        return null;
    }
    
    public List<Reservation> findAll() throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findAllFromDatabase();
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        return findAllFromFile();
    }
    
    private List<Reservation> findAllFromDatabase() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM reservations ORDER BY reservation_date DESC")) {
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    private List<Reservation> findAllFromFile() throws IOException, ClassNotFoundException {
        return fileManager.readListFromFile(FILENAME);
    }
    
    public List<Reservation> findByGuestName(String guestName) throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findByGuestNameFromDatabase(guestName);
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        return findAllFromFile().stream()
            .filter(r -> r.getGuest() != null && r.getGuest().getName().toLowerCase().contains(guestName.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    private List<Reservation> findByGuestNameFromDatabase(String guestName) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM reservations WHERE LOWER(guest_name) LIKE ? ORDER BY reservation_date DESC")) {
            ps.setString(1, "%" + guestName.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }
        }
        return reservations;
    }
    
    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationNumber(rs.getString("reservation_number"));
        
        // Create guest object
        Guest guest = new Guest();
        guest.setName(rs.getString("guest_name"));
        guest.setAddress(rs.getString("guest_address"));
        guest.setContactNumber(rs.getString("guest_contact"));
        guest.setEmail(rs.getString("guest_email"));
        guest.setNicNumber(rs.getString("guest_nic"));
        reservation.setGuest(guest);
        
        // Get room (need to fetch from RoomDAO)
        String roomNumber = rs.getString("room_number");
        if (roomNumber != null) {
            try {
                RoomDAO roomDAO = new RoomDAO();
                Room room = roomDAO.findByRoomNumber(roomNumber);
                reservation.setRoom(room != null ? room : createRoomFromResultSet(rs));
            } catch (Exception e) {
                reservation.setRoom(createRoomFromResultSet(rs));
            }
        }
        
        Date checkIn = rs.getDate("check_in_date");
        Date checkOut = rs.getDate("check_out_date");
        Date resDate = rs.getDate("reservation_date");
        
        if (checkIn != null) reservation.setCheckInDate(checkIn.toLocalDate());
        if (checkOut != null) reservation.setCheckOutDate(checkOut.toLocalDate());
        if (resDate != null) reservation.setReservationDate(resDate.toLocalDate());
        
        reservation.setNumberOfNights(rs.getInt("number_of_nights"));
        reservation.setTotalAmount(rs.getDouble("total_amount"));
        reservation.setStatus(rs.getString("status"));
        
        return reservation;
    }
    
    private Room createRoomFromResultSet(ResultSet rs) throws SQLException {
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
            true,
            2,
            ""
        );
    }
}
