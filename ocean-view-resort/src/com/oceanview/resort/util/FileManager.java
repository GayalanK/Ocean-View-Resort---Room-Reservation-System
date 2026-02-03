package com.oceanview.resort.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File Manager - Singleton pattern for file operations
 * Handles serialization and file I/O for data persistence
 */
public class FileManager {
    private static FileManager instance;
    private static final String DATA_DIR = "data";
    
    private FileManager() {
        // Create data directory if it doesn't exist
        new File(DATA_DIR).mkdirs();
    }
    
    public static synchronized FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }
    
    /**
     * Write object to file using serialization
     */
    public void writeObjectToFile(Object obj, String filename) throws IOException {
        File file = new File(DATA_DIR, filename);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        }
    }
    
    /**
     * Read object from file using deserialization
     */
    public Object readObjectFromFile(String filename) throws IOException, ClassNotFoundException {
        File file = new File(DATA_DIR, filename);
        if (!file.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        }
    }
    
    /**
     * Write list to file
     */
    @SuppressWarnings("unchecked")
    public void writeListToFile(List<?> list, String filename) throws IOException {
        writeObjectToFile(list, filename);
    }
    
    /**
     * Read list from file
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> readListFromFile(String filename) throws IOException, ClassNotFoundException {
        File file = new File(DATA_DIR, filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        Object obj = readObjectFromFile(filename);
        if (obj instanceof List) {
            return (List<T>) obj;
        }
        return new ArrayList<>();
    }
    
    /**
     * Append text to file
     */
    public void appendToFile(String filename, String content) throws IOException {
        File file = new File(DATA_DIR, filename);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true))) {
            pw.println(content);
        }
    }
    
    /**
     * Read all lines from file
     */
    public List<String> readLinesFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(DATA_DIR, filename);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
