package com.oceanview.resort.web;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Web Server - Main HTTP server for the application
 */
public class WebServer {
    private ServletRouter servletRouter = new ServletRouter();
    private ServerSocket serverSocket;
    private boolean running;
    private static final String WEB_DIR = "web";
    private static final String DATA_DIR = "data";
    
    public WebServer(int port) throws IOException {
        new File(WEB_DIR).mkdirs();
        new File(DATA_DIR).mkdirs();
        this.serverSocket = new ServerSocket(port);
    }
    
    public void start() {
        running = true;
        System.out.println("============================================");
        System.out.println("Ocean View Resort - Web Application");
        System.out.println("Server running on: http://localhost:" + serverSocket.getLocalPort());
        System.out.println("============================================\n");
        
        new Thread(() -> {
            while (running) {
                try {
                    Socket client = serverSocket.accept();
                    new Thread(new ClientHandler(client)).start();
                } catch (IOException e) {
                    if (running) System.err.println("Error: " + e.getMessage());
                }
            }
        }).start();
    }
    
    public void stop() throws IOException {
        running = false;
        serverSocket.close();
    }
    
    private class ClientHandler implements Runnable {
        private Socket socket;
        
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                
                String request = in.readLine();
                if (request == null) return;
                
                String[] parts = request.split(" ");
                if (parts.length < 2) {
                    sendError(out, 400, "Bad Request");
                    return;
                }
                
                String method = parts[0];
                String path = parts[1].split("\\?")[0];
                
                handleRequest(method, path, in, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try { socket.close(); } catch (IOException e) {}
            }
        }
        
        private void handleRequest(String method, String path, BufferedReader in, PrintWriter out) throws Exception {
            // Serve static HTML files
            if (method.equals("GET") && (path.equals("/") || path.equals("/index.html"))) {
                serveFile(out, WEB_DIR + "/index.html", "text/html");
                return;
            } else if (method.equals("GET") && path.equals("/dashboard.html")) {
                serveFile(out, WEB_DIR + "/dashboard.html", "text/html");
                return;
            }
            
            // Parse query parameters
            Map<String, String> params = parseQueryParameters(path);
            String cleanPath = path.split("\\?")[0];
            
            // Parse headers
            Map<String, String> headers = parseHeaders(in);
            
            // Read request body for POST/PUT
            String body = "";
            if (method.equals("POST") || method.equals("PUT")) {
                body = readRequestBody(in, headers);
            }
            
            // Route to servlets
            servletRouter.route(method, cleanPath, params, headers, body, in, out);
        }
        
        private Map<String, String> parseQueryParameters(String path) {
            Map<String, String> params = new HashMap<>();
            if (path.contains("?")) {
                String query = path.substring(path.indexOf("?") + 1);
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        params.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            return params;
        }
        
        private Map<String, String> parseHeaders(BufferedReader in) throws IOException {
            Map<String, String> headers = new HashMap<>();
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    if (parts.length == 2) {
                        headers.put(parts[0].trim().toLowerCase(), parts[1].trim());
                    }
                }
            }
            return headers;
        }
        
        private String readRequestBody(BufferedReader in, Map<String, String> headers) throws IOException {
            String contentLength = headers.get("content-length");
            if (contentLength == null) return "";
            
            try {
                int length = Integer.parseInt(contentLength);
                if (length <= 0) return "";
                
                char[] buffer = new char[length];
                int read = 0;
                while (read < length) {
                    int charsRead = in.read(buffer, read, length - read);
                    if (charsRead == -1) break;
                    read += charsRead;
                }
                return new String(buffer, 0, read);
            } catch (NumberFormatException e) {
                return "";
            }
        }
        
        private void serveFile(PrintWriter out, String filePath, String contentType) {
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                String content = new String(bytes);
                byte[] contentBytes = content.getBytes();
                
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + contentType + "; charset=UTF-8");
                out.println("Content-Length: " + contentBytes.length);
                out.println();
                out.print(content);
                out.flush();
            } catch (IOException e) {
                String error = "{\"error\":\"File not found\"}";
                out.println("HTTP/1.1 404 Not Found");
                out.println("Content-Type: application/json; charset=UTF-8");
                out.println("Content-Length: " + error.getBytes().length);
                out.println();
                out.println(error);
                out.flush();
            }
        }
        
        private void sendError(PrintWriter out, int code, String msg) {
            String json = "{\"error\":\"" + msg + "\"}";
            out.println("HTTP/1.1 " + code + " " + getStatus(code));
            out.println("Content-Type: application/json; charset=UTF-8");
            out.println("Content-Length: " + json.getBytes().length);
            out.println();
            out.println(json);
            out.flush();
        }
        
        private String getStatus(int code) {
            switch (code) {
                case 200: return "OK";
                case 400: return "Bad Request";
                case 404: return "Not Found";
                case 500: return "Internal Server Error";
                default: return "OK";
            }
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
        WebServer server = new WebServer(port);
        server.start();
        System.out.println("Press Enter to stop...");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        server.stop();
    }
}
