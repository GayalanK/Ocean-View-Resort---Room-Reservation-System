package com.oceanview.resort.dao;

import com.oceanview.resort.database.DatabaseConnection;
import com.oceanview.resort.model.User;
import com.oceanview.resort.util.FileManager;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static final String FILENAME = "users.dat";
    private FileManager fileManager = FileManager.getInstance();
    private DatabaseConnection dbConnection = DatabaseConnection.getInstance();
    
    public void save(User user) throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                saveToDatabase(user);
                return;
            } catch (SQLException e) {
                System.err.println("Database save failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        saveToFile(user);
    }
    
    private void saveToDatabase(User user) throws SQLException {
        Connection conn = dbConnection.getConnection();
        
        // Check if user exists
        try (PreparedStatement check = conn.prepareStatement(
            "SELECT username FROM users WHERE username = ?")) {
            check.setString(1, user.getUsername());
            ResultSet rs = check.executeQuery();
            
            if (rs.next()) {
                // Update existing
                updateInDatabase(user);
            } else {
                // Insert new
                insertIntoDatabase(user);
            }
        }
    }
    
    private void insertIntoDatabase(User user) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO users (username, password, full_name, role) VALUES (?, ?, ?, ?)")) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getRole());
            
            ps.executeUpdate();
        }
    }
    
    private void updateInDatabase(User user) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE users SET password = ?, full_name = ?, role = ? WHERE username = ?")) {
            
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getUsername());
            
            ps.executeUpdate();
        }
    }
    
    private void saveToFile(User user) throws IOException, ClassNotFoundException {
        List<User> users = findAllFromFile();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user);
                fileManager.writeListToFile(users, FILENAME);
                return;
            }
        }
        users.add(user);
        fileManager.writeListToFile(users, FILENAME);
    }
    
    public User findByUsername(String username) throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findFromDatabase(username);
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        return findAllFromFile().stream()
            .filter(u -> u.getUsername().equals(username))
            .findFirst().orElse(null);
    }
    
    private User findFromDatabase(String username) throws SQLException {
        Connection conn = dbConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
            "SELECT * FROM users WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                return user;
            }
        }
        return null;
    }
    
    public List<User> findAll() throws IOException, ClassNotFoundException {
        // Try database first
        if (dbConnection.isDatabaseAvailable()) {
            try {
                return findAllFromDatabase();
            } catch (SQLException e) {
                System.err.println("Database query failed, using file storage: " + e.getMessage());
            }
        }
        
        // Fallback to file storage
        List<User> users = findAllFromFile();
        if (users.isEmpty()) {
            initializeDefaultUsers();
            users = findAllFromFile();
        }
        return users;
    }
    
    private List<User> findAllFromDatabase() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = dbConnection.getConnection();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users ORDER BY username")) {
            
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        }
        return users;
    }
    
    private List<User> findAllFromFile() throws IOException, ClassNotFoundException {
        return fileManager.readListFromFile(FILENAME);
    }
    
    private void initializeDefaultUsers() throws IOException, ClassNotFoundException {
        List<User> defaultUsers = new ArrayList<>();
        defaultUsers.add(new User("admin", "admin123", "Administrator", "ADMIN"));
        defaultUsers.add(new User("staff", "staff123", "Staff Member", "STAFF"));
        fileManager.writeListToFile(defaultUsers, FILENAME);
    }
}
