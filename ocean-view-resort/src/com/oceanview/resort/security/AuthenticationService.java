package com.oceanview.resort.security;

import com.oceanview.resort.dao.UserDAO;
import com.oceanview.resort.model.User;
import java.io.IOException;

public class AuthenticationService {
    private UserDAO userDAO = new UserDAO();
    private User currentUser;
    
    public boolean login(String username, String password) throws IOException, ClassNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }
    
    public void logout() {
        currentUser = null;
    }
    
    public boolean isAuthenticated() {
        return currentUser != null;
    }
    
    public User getCurrentUser() {
        return currentUser;
    }
}
