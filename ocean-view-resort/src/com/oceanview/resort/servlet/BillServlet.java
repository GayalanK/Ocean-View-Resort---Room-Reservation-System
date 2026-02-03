package com.oceanview.resort.servlet;

import com.oceanview.resort.service.ReservationService;

/**
 * Bill Servlet - Handles bill calculation and generation
 */
public class BillServlet extends Servlet {
    private ReservationService reservationService = new ReservationService();
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        
        if ("GET".equals(method)) {
            doGet(request, response);
        } else {
            response.sendError(405, "Method " + method + " not allowed");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String path = request.getPath();
            
            // GET /api/bill/{reservationNumber} - Get bill for reservation
            if (path.startsWith("/api/bill/") || path.startsWith("/bill/")) {
                String reservationNumber = extractPathParameter(path, "/api/bill/", "/bill/");
                
                if (reservationNumber == null || reservationNumber.isEmpty()) {
                    response.setStatus(400);
                    response.sendError(400, "Reservation number is required");
                    return;
                }
                
                // Calculate bill
                String bill = reservationService.calculateBill(reservationNumber);
                
                if (bill != null && !bill.contains("not found")) {
                    response.setStatus(200);
                    response.setContentType("text/plain");
                    
                    // Convert to JSON for consistency with API
                    String jsonBill = String.format(
                        "{\"reservationNumber\":\"%s\",\"bill\":\"%s\"}",
                        reservationNumber,
                        escape(bill.replace("\n", "\\n").replace("\r", "\\r"))
                    );
                    response.sendJSON(jsonBill);
                } else {
                    response.setStatus(404);
                    response.sendError(404, "Reservation not found");
                }
            } else {
                response.setStatus(404);
                response.sendError(404, "Not found");
            }
            
        } catch (Exception e) {
            response.setStatus(500);
            response.sendError(500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Extract path parameter from URL
     */
    private String extractPathParameter(String path, String... prefixes) {
        for (String prefix : prefixes) {
            if (path.startsWith(prefix)) {
                return path.substring(prefix.length());
            }
        }
        return "";
    }
    
    /**
     * Escape JSON string
     */
    private String escape(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\t", "\\t");
    }
}
