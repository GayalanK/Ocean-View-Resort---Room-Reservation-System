package com.oceanview.resort.servlet;

import com.oceanview.resort.dao.RoomDAO;
import com.oceanview.resort.model.Room;
import java.util.List;

/**
 * Room Servlet - Handles room-related operations
 */
public class RoomServlet extends Servlet {
    private RoomDAO roomDAO = new RoomDAO();
    
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("GET".equals(request.getMethod())) {
            doGet(request, response);
        } else {
            response.sendError(405, "Method " + request.getMethod() + " not allowed");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            String path = request.getPath();
            
            if (path.equals("/api/rooms") || path.equals("/rooms")) {
                String available = request.getParameter("available");
                
                List<Room> rooms;
                if ("true".equalsIgnoreCase(available) || available == null) {
                    rooms = roomDAO.findAvailableRooms();
                } else {
                    rooms = roomDAO.findAll();
                }
                
                response.setStatus(200);
                response.sendJSON(toJSONArray(rooms));
            } else if (path.startsWith("/api/rooms/") || path.startsWith("/rooms/")) {
                String roomNumber = extractPathParameter(path, "/api/rooms/", "/rooms/");
                Room room = roomDAO.findByRoomNumber(roomNumber);
                
                if (room != null) {
                    response.setStatus(200);
                    response.sendJSON(toJSON(room));
                } else {
                    response.setStatus(404);
                    response.sendError(404, "Room not found");
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
    
    private String extractPathParameter(String path, String... prefixes) {
        for (String prefix : prefixes) {
            if (path.startsWith(prefix)) {
                return path.substring(prefix.length());
            }
        }
        return "";
    }
    
    private String toJSON(Room room) {
        if (room == null) return "{}";
        
        return String.format(
            "{\"roomNumber\":\"%s\"," +
            "\"roomType\":\"%s\"," +
            "\"typeDescription\":\"%s\"," +
            "\"isAvailable\":%s," +
            "\"capacity\":%d," +
            "\"features\":\"%s\"," +
            "\"rate\":%.2f}",
            escape(room.getRoomNumber()),
            room.getRoomType() != null ? room.getRoomType().name() : "",
            room.getRoomType() != null ? escape(room.getRoomType().getDescription()) : "",
            room.isAvailable(),
            room.getCapacity(),
            escape(room.getFeatures()),
            room.getRate()
        );
    }
    
    private String toJSONArray(List<Room> rooms) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < rooms.size(); i++) {
            if (i > 0) json.append(",");
            json.append(toJSON(rooms.get(i)));
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
