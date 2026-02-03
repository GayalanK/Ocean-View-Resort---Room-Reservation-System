package com.oceanview.resort.servlet;

import com.oceanview.resort.model.Guest;
import com.oceanview.resort.model.Reservation;
import com.oceanview.resort.service.ReservationService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Reservation Servlet - Handles reservation operations
 */
public class ReservationServlet extends Servlet {
    private ReservationService reservationService = new ReservationService();
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getMethod();
        
        switch (method) {
            case "GET":
                doGet(request, response);
                break;
            case "POST":
                doPost(request, response);
                break;
            default:
                response.sendError(405, "Method " + method + " not allowed");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String path = request.getPath();
            
            if (path.equals("/api/reservations") || path.equals("/reservations")) {
                List<Reservation> reservations = reservationService.getAllReservations();
                response.setStatus(200);
                response.sendJSON(toJSONArray(reservations));
            } else if (path.startsWith("/api/reservations/") || path.startsWith("/reservations/")) {
                String number = extractPathParameter(path, "/api/reservations/", "/reservations/");
                Reservation reservation = reservationService.getReservationByNumber(number);
                
                if (reservation != null) {
                    response.setStatus(200);
                    response.sendJSON(toJSON(reservation));
                } else {
                    response.setStatus(404);
                    response.sendError(404, "Reservation not found");
                }
            } else if (path.startsWith("/api/reservations/search") || path.startsWith("/reservations/search")) {
                String name = request.getParameter("name");
                if (name == null || name.isEmpty()) {
                    response.setStatus(400);
                    response.sendError(400, "Query parameter 'name' is required");
                    return;
                }
                
                List<Reservation> results = reservationService.searchByGuestName(name);
                response.setStatus(200);
                response.sendJSON(toJSONArray(results));
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
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String body = request.getBody();
            if (body == null || body.isEmpty()) {
                response.setStatus(400);
                response.sendError(400, "Request body is required");
                return;
            }
            
            Map<String, String> jsonData = parseJSON(body);
            
            String name = jsonData.get("name");
            String address = jsonData.get("address");
            String contact = jsonData.get("contact");
            String email = jsonData.get("email");
            String nic = jsonData.get("nic");
            String roomNumber = jsonData.get("roomNumber");
            String checkInDate = jsonData.get("checkInDate");
            String checkOutDate = jsonData.get("checkOutDate");
            
            if (name == null || roomNumber == null || checkInDate == null || checkOutDate == null) {
                response.setStatus(400);
                response.sendError(400, "Missing required fields: name, roomNumber, checkInDate, checkOutDate");
                return;
            }
            
            Guest guest = new Guest(name, address, contact, email, nic);
            
            String reservationNumber = reservationService.createReservation(
                guest, roomNumber, checkInDate, checkOutDate
            );
            
            response.setStatus(201);
            String json = String.format(
                "{\"success\":true,\"reservationNumber\":\"%s\",\"message\":\"Reservation created successfully\"}",
                reservationNumber
            );
            response.sendJSON(json);
            
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
            response.sendError(400, e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.sendError(500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String extractPathParameter(String path, String... prefixes) {
        for (String prefix : prefixes) {
            if (path.startsWith(prefix)) {
                return path.substring(prefix.length());
            }
        }
        return "";
    }
    
    private Map<String, String> parseJSON(String json) {
        Map<String, String> result = new HashMap<>();
        if (json == null || json.trim().isEmpty()) {
            return result;
        }
        
        json = json.trim().replaceAll("^\\{", "").replaceAll("\\}$", "");
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
    
    private String toJSON(Reservation r) {
        if (r == null) return "{}";
        
        return String.format(
            "{\"reservationNumber\":\"%s\"," +
            "\"guestName\":\"%s\"," +
            "\"guestAddress\":\"%s\"," +
            "\"guestContact\":\"%s\"," +
            "\"guestEmail\":\"%s\"," +
            "\"roomNumber\":\"%s\"," +
            "\"roomType\":\"%s\"," +
            "\"checkInDate\":\"%s\"," +
            "\"checkOutDate\":\"%s\"," +
            "\"numberOfNights\":%d," +
            "\"totalAmount\":%.2f," +
            "\"status\":\"%s\"," +
            "\"reservationDate\":\"%s\"}",
            escape(r.getReservationNumber()),
            escape(r.getGuest() != null ? r.getGuest().getName() : ""),
            escape(r.getGuest() != null ? r.getGuest().getAddress() : ""),
            escape(r.getGuest() != null ? r.getGuest().getContactNumber() : ""),
            escape(r.getGuest() != null ? r.getGuest().getEmail() : ""),
            escape(r.getRoom() != null ? r.getRoom().getRoomNumber() : ""),
            r.getRoom() != null ? r.getRoom().getRoomType().getDescription() : "",
            r.getCheckInDate() != null ? r.getCheckInDate().toString() : "",
            r.getCheckOutDate() != null ? r.getCheckOutDate().toString() : "",
            r.getNumberOfNights(),
            r.getTotalAmount(),
            escape(r.getStatus()),
            r.getReservationDate() != null ? r.getReservationDate().toString() : ""
        );
    }
    
    private String toJSONArray(List<Reservation> reservations) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < reservations.size(); i++) {
            if (i > 0) json.append(",");
            json.append(toJSON(reservations.get(i)));
        }
        json.append("]");
        return json.toString();
    }
    
    private String escape(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
