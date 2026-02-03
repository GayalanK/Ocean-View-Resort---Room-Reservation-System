package com.oceanview.resort.servlet;

import com.oceanview.resort.security.AuthenticationService;
import java.util.Map;
import java.util.HashMap;

/**
 * Login Servlet - Handles user authentication
 */
public class LoginServlet extends Servlet {
    private AuthenticationService authService = new AuthenticationService();
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        
        if ("POST".equals(method)) {
            doPost(request, response);
        } else {
            response.sendError(405, "Method " + method + " not allowed");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String body = request.getBody();
            if (body == null || body.isEmpty()) {
                response.setStatus(400);
                response.sendError(400, "Request body is required");
                return;
            }
            
            // Parse JSON body
            Map<String, String> jsonData = parseJSON(body);
            String username = jsonData.get("username");
            String password = jsonData.get("password");
            
            if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
                response.setStatus(400);
                response.sendError(400, "Username and password are required");
                return;
            }
            
            // Authenticate user
            if (authService.login(username, password)) {
                // Generate session ID
                String sessionId = "sess_" + System.currentTimeMillis() + "_" + username.hashCode();
                request.setSessionId(sessionId);
                
                // Success response
                response.setStatus(200);
                String json = String.format(
                    "{\"success\":true,\"session\":\"%s\",\"username\":\"%s\",\"message\":\"Login successful\"}",
                    sessionId, username
                );
                response.sendJSON(json);
            } else {
                // Authentication failed
                response.setStatus(401);
                response.sendError(401, "Invalid username or password");
            }
            
        } catch (Exception e) {
            response.setStatus(500);
            response.sendError(500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Simple JSON parser for request body
     */
    private Map<String, String> parseJSON(String json) {
        Map<String, String> result = new HashMap<>();
        if (json == null || json.trim().isEmpty()) {
            return result;
        }
        
        // Remove braces
        json = json.trim().replaceAll("^\\{", "").replaceAll("\\}$", "");
        
        // Split by comma
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("\"", "");
                String value = keyValue[1].trim().replaceAll("\"", "");
                result.put(key, value);
            }
        }
        return result;
    }
}
