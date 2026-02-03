package com.oceanview.resort.servlet;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Base Servlet class - Servlet-like interface for HTTP request handling
 * Pure Java implementation without external servlet libraries
 */
public abstract class Servlet {
    
    public abstract void service(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "Method GET not allowed");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "Method POST not allowed");
    }
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "Method PUT not allowed");
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendError(405, "Method DELETE not allowed");
    }
    
    public static class HttpServletRequest {
        private String method;
        private String path;
        private Map<String, String> parameters;
        private Map<String, String> headers;
        private String body;
        private BufferedReader reader;
        private String sessionId;
        
        public HttpServletRequest(String method, String path, Map<String, String> parameters,
                                 Map<String, String> headers, String body, BufferedReader reader) {
            this.method = method;
            this.path = path;
            this.parameters = parameters;
            this.headers = headers;
            this.body = body;
            this.reader = reader;
        }
        
        public String getMethod() { return method; }
        public String getPath() { return path; }
        public String getParameter(String name) { return parameters != null ? parameters.get(name) : null; }
        public Map<String, String> getParameters() { return parameters; }
        public String getHeader(String name) { return headers != null ? headers.get(name) : null; }
        public String getBody() { return body; }
        public BufferedReader getReader() { return reader; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        
        public String getPathInfo() {
            int lastSlash = path.lastIndexOf('/');
            return lastSlash >= 0 ? path.substring(lastSlash) : path;
        }
    }
    
    public static class HttpServletResponse {
        private PrintWriter writer;
        private int statusCode = 200;
        private String contentType = "text/html";
        private Map<String, String> headers = new HashMap<>();
        
        public HttpServletResponse(PrintWriter writer) {
            this.writer = writer;
        }
        
        public void setStatus(int code) { this.statusCode = code; }
        public int getStatus() { return statusCode; }
        public void setContentType(String type) { this.contentType = type; }
        public String getContentType() { return contentType; }
        public PrintWriter getWriter() { return writer; }
        public void setHeader(String name, String value) {
            headers.put(name, value);
        }
        
        public void sendJSON(String json) {
            setContentType("application/json");
            writer.println("HTTP/1.1 " + statusCode + " " + getStatusText(statusCode));
            writer.println("Content-Type: application/json; charset=UTF-8");
            writer.println("Access-Control-Allow-Origin: *");
            writer.println("Content-Length: " + json.getBytes().length);
            writer.println();
            writer.println(json);
            writer.flush();
        }
        
        public void sendText(String text) {
            byte[] bytes = text.getBytes();
            writer.println("HTTP/1.1 " + statusCode + " " + getStatusText(statusCode));
            writer.println("Content-Type: " + contentType + "; charset=UTF-8");
            writer.println("Content-Length: " + bytes.length);
            writer.println();
            writer.print(text);
            writer.flush();
        }
        
        public void sendError(int code, String message) {
            String json = "{\"error\":\"" + message + "\"}";
            statusCode = code;
            sendJSON(json);
        }
        
        private String getStatusText(int code) {
            switch (code) {
                case 200: return "OK";
                case 201: return "Created";
                case 400: return "Bad Request";
                case 401: return "Unauthorized";
                case 404: return "Not Found";
                case 405: return "Method Not Allowed";
                case 500: return "Internal Server Error";
                default: return "OK";
            }
        }
    }
}
