package com.oceanview.resort.security;

import com.oceanview.resort.dao.UserDAO;
import com.oceanview.resort.model.User;
import java.io.IOException;

/**
 * Authentication Service - Handles user authentication
 */
public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();
    
    /**
     * Authenticate user with username and password
     */
    public boolean login(String username, String password) {
        try {
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                return false;
            }
            
            User user = userDAO.findByUsername(username);
            if (user != null && user.getPassword().equals(password)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Authentication error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get user by username
     */
    public User getUser(String username) {
        try {
            return userDAO.findByUsername(username);
        } catch (Exception e) {
            System.err.println("Error getting user: " + e.getMessage());
            return null;
        }
    }
}
