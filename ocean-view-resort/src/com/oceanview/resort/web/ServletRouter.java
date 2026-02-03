package com.oceanview.resort.web;

import com.oceanview.resort.servlet.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet Router - Routes HTTP requests to appropriate servlets
 */
public class ServletRouter {
    private LoginServlet loginServlet = new LoginServlet();
    private ReservationServlet reservationServlet = new ReservationServlet();
    private RoomServlet roomServlet = new RoomServlet();
    private BillServlet billServlet = new BillServlet();
    
    /**
     * Route request to appropriate servlet
     */
    public void route(String method, String path, Map<String, String> params, 
                     Map<String, String> headers, String body, 
                     BufferedReader reader, PrintWriter writer) throws Exception {
        
        Servlet.HttpServletRequest request = createRequest(method, path, params, headers, body, reader);
        Servlet.HttpServletResponse response = createResponse(writer);
        
        try {
            if (path.equals("/login") || path.equals("/api/login")) {
                loginServlet.service(request, response);
            } else if (path.startsWith("/api/reservations") || path.startsWith("/reservations")) {
                reservationServlet.service(request, response);
            } else if (path.startsWith("/api/rooms") || path.startsWith("/rooms")) {
                roomServlet.service(request, response);
            } else if (path.startsWith("/api/bill") || path.startsWith("/bill")) {
                billServlet.service(request, response);
            } else {
                response.setStatus(404);
                response.sendError(404, "Endpoint not found");
            }
        } catch (Exception e) {
            response.setStatus(500);
            response.sendError(500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Servlet.HttpServletRequest createRequest(String method, String path, 
                                                     Map<String, String> params,
                                                     Map<String, String> headers,
                                                     String body,
                                                     BufferedReader reader) {
        return new Servlet.HttpServletRequest(method, path, params, headers, body, reader);
    }
    
    private Servlet.HttpServletResponse createResponse(PrintWriter writer) {
        return new Servlet.HttpServletResponse(writer);
    }
}
