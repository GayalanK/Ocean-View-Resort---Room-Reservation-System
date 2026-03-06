package com.oceanview.resort.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static FileManager instance;
    private static final String DATA_DIR = "data/";
    
    private FileManager() {
        new File(DATA_DIR).mkdirs();
    }
    
    public static FileManager getInstance() {
        if (instance == null) {
            synchronized (FileManager.class) {
                if (instance == null) {
                    instance = new FileManager();
                }
            }
        }
        return instance;
    }
    
    public void writeListToFile(List<?> list, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_DIR + filename))) {
            oos.writeObject(list);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> readListFromFile(String filename) throws IOException, ClassNotFoundException {
        File file = new File(DATA_DIR + filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            return obj instanceof List ? (List<T>) obj : new ArrayList<>();
        }
    }
}
