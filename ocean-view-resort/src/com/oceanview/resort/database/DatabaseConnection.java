package com.oceanview.resort.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.File;

/**
 * Database Connection Manager
 * Uses embedded database (H2, Derby, or SQLite) with JDBC
 * Pure Java implementation - no external libraries required if using Java DB/Derby
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String DB_DIR = "data";
    private static final String DB_NAME = "oceanview_resort";
    
    // Try Java DB/Derby first (bundled with some JDK installations)
    private static final String DERBY_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DERBY_URL = "jdbc:derby:" + DB_DIR + "/" + DB_NAME + ";create=true";
    
    // H2 Database fallback (if available)
    private static final String H2_DRIVER = "org.h2.Driver";
    private static final String H2_URL = "jdbc:h2:" + DB_DIR + "/" + DB_NAME;
    
    // SQLite fallback (if driver is in JDK or classpath)
    private static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    private static final String SQLITE_URL = "jdbc:sqlite:" + DB_DIR + "/" + DB_NAME + ".db";
    
    private String activeDriver;
    private String activeUrl;
    
    private DatabaseConnection() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    /**
     * Initialize database connection and create tables if they don't exist
     */
    private void initializeDatabase() {
        try {
            // Create database directory
            new File(DB_DIR).mkdirs();
            
            // Try to connect using available database drivers
            if (tryConnect(DERBY_DRIVER, DERBY_URL)) {
                activeDriver = DERBY_DRIVER;
                activeUrl = DERBY_URL;
                System.out.println("Connected to Java DB (Derby) database");
            } else if (tryConnect(H2_DRIVER, H2_URL)) {
                activeDriver = H2_DRIVER;
                activeUrl = H2_URL;
                System.out.println("Connected to H2 database");
            } else if (tryConnect(SQLITE_DRIVER, SQLITE_URL)) {
                activeDriver = SQLITE_DRIVER;
                activeUrl = SQLITE_URL;
                System.out.println("Connected to SQLite database");
            } else {
                // Fallback: Use file-based storage message
                System.err.println("Warning: No JDBC driver found. Using file-based storage.");
                System.err.println("To use database, add one of: Derby, H2, or SQLite JDBC driver to classpath");
                return;
            }
            
            // Create tables if they don't exist
            createTables();
            
        } catch (Exception e) {
            System.err.println("Database initialization error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Try to connect to database using specified driver and URL
     */
    private boolean tryConnect(String driver, String url) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
            return connection != null && !connection.isClosed();
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
    }
    
    /**
     * Create database tables
     */
    private void createTables() {
        if (connection == null) return;
        
        try (Statement stmt = connection.createStatement()) {
            // Users table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(50) PRIMARY KEY, " +
                "password VARCHAR(100) NOT NULL, " +
                "full_name VARCHAR(100) NOT NULL, " +
                "role VARCHAR(20) NOT NULL DEFAULT 'STAFF')"
            );
            
            // Rooms table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS rooms (" +
                "room_number VARCHAR(10) PRIMARY KEY, " +
                "room_type VARCHAR(20) NOT NULL, " +
                "is_available BOOLEAN NOT NULL DEFAULT true, " +
                "capacity INTEGER NOT NULL, " +
                "features VARCHAR(500), " +
                "base_rate DOUBLE NOT NULL)"
            );
            
            // Guests table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS guests (" +
                "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "address VARCHAR(500), " +
                "contact_number VARCHAR(20), " +
                "email VARCHAR(100), " +
                "nic_number VARCHAR(20))"
            );
            
            // For Derby, use GENERATED ALWAYS AS IDENTITY instead of AUTO_INCREMENT
            try {
                stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS guests_derby (" +
                    "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "name VARCHAR(100) NOT NULL, " +
                    "address VARCHAR(500), " +
                    "contact_number VARCHAR(20), " +
                    "email VARCHAR(100), " +
                    "nic_number VARCHAR(20), " +
                    "PRIMARY KEY (id))"
                );
            } catch (SQLException e) {
                // Ignore if already exists or not Derby
            }
            
            // Reservations table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS reservations (" +
                "reservation_number VARCHAR(20) PRIMARY KEY, " +
                "guest_id INTEGER, " +
                "guest_name VARCHAR(100) NOT NULL, " +
                "guest_address VARCHAR(500), " +
                "guest_contact VARCHAR(20), " +
                "guest_email VARCHAR(100), " +
                "guest_nic VARCHAR(20), " +
                "room_number VARCHAR(10) NOT NULL, " +
                "room_type VARCHAR(20) NOT NULL, " +
                "check_in_date DATE NOT NULL, " +
                "check_out_date DATE NOT NULL, " +
                "number_of_nights INTEGER NOT NULL, " +
                "total_amount DOUBLE NOT NULL, " +
                "status VARCHAR(20) NOT NULL DEFAULT 'PENDING', " +
                "reservation_date DATE NOT NULL, " +
                "FOREIGN KEY (room_number) REFERENCES rooms(room_number))"
            );
            
            // Initialize default data
            initializeDefaultData();
            
            System.out.println("Database tables created successfully");
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Initialize default data (users and rooms)
     */
    private void initializeDefaultData() {
        try {
            // Check if users exist
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM users")) {
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    // Insert default admin user
                    try (PreparedStatement ins = connection.prepareStatement(
                        "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?)")) {
                        ins.setString(1, "admin");
                        ins.setString(2, "admin123"); // In production, should be hashed
                        ins.setString(3, "Administrator");
                        ins.setString(4, "ADMIN");
                        ins.executeUpdate();
                    }
                }
            }
            
            // Check if rooms exist
            try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM rooms")) {
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    // Insert default rooms
                    insertDefaultRooms();
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error initializing default data: " + e.getMessage());
        }
    }
    
    /**
     * Insert default rooms into database
     */
    private void insertDefaultRooms() throws SQLException {
        String[][] rooms = {
            {"R101", "SINGLE", "5000", "1", "AC, TV, WiFi"},
            {"R102", "SINGLE", "5000", "1", "AC, TV, WiFi"},
            {"R103", "SINGLE", "5000", "1", "AC, TV, WiFi"},
            {"R201", "DOUBLE", "8000", "2", "AC, TV, WiFi, Mini Bar"},
            {"R202", "DOUBLE", "8000", "2", "AC, TV, WiFi, Mini Bar"},
            {"R203", "DOUBLE", "8000", "2", "AC, TV, WiFi, Mini Bar"},
            {"R301", "DELUXE", "12000", "3", "AC, TV, WiFi, Mini Bar, Balcony"},
            {"R302", "DELUXE", "12000", "3", "AC, TV, WiFi, Mini Bar, Balcony"},
            {"R401", "SUITE", "15000", "4", "AC, TV, WiFi, Mini Bar, Balcony, Jacuzzi"}
        };
        
        try (PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO rooms (room_number, room_type, base_rate, capacity, features, is_available) " +
            "VALUES (?, ?, ?, ?, ?, true)")) {
            
            for (String[] room : rooms) {
                ps.setString(1, room[0]);
                ps.setString(2, room[1]);
                ps.setDouble(3, Double.parseDouble(room[2]));
                ps.setInt(4, Integer.parseInt(room[3]));
                ps.setString(5, room[4]);
                ps.executeUpdate();
            }
        }
    }
    
    /**
     * Get database connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            if (activeDriver != null && activeUrl != null) {
                try {
                    Class.forName(activeDriver);
                    connection = DriverManager.getConnection(activeUrl);
                } catch (ClassNotFoundException e) {
                    throw new SQLException("Database driver not found: " + activeDriver, e);
                }
            } else {
                throw new SQLException("Database not initialized");
            }
        }
        return connection;
    }
    
    /**
     * Check if database is available
     */
    public boolean isDatabaseAvailable() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Close database connection
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                // For Derby, need to shutdown
                if (activeDriver != null && activeDriver.contains("derby")) {
                    try {
                        DriverManager.getConnection("jdbc:derby:;shutdown=true");
                    } catch (SQLException e) {
                        // Expected exception for shutdown
                        if (e.getSQLState().equals("XJ015")) {
                            System.out.println("Derby database shut down normally");
                        }
                    }
                } else {
                    connection.close();
                }
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Get the active database type
     */
    public String getDatabaseType() {
        if (activeDriver == null) return "File-based (No database driver available)";
        if (activeDriver.contains("derby")) return "Java DB (Apache Derby)";
        if (activeDriver.contains("h2")) return "H2 Database";
        if (activeDriver.contains("sqlite")) return "SQLite";
        return "Unknown";
    }
}
