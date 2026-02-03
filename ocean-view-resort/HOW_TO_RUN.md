# How to Run the Application

## Prerequisites
- Java JDK 17 or higher
- NetBeans IDE 12.0+ (recommended)

## Steps

### 1. Open Project in NetBeans
1. Open NetBeans IDE
2. File → Open Project
3. Navigate to `ocean-view-resort` folder
4. Select and open

### 2. Set Main Class
1. Right-click project → Properties
2. Select "Run" category
3. Main Class: `com.oceanview.resort.web.WebServer`
4. Click OK

### 3. Run Application
1. Right-click project → Run (or press F6)
2. Check console for server start message
3. Server runs on: http://localhost:8080

### 4. Access Web Interface
1. Open browser
2. Go to: http://localhost:8080
3. Login with:
   - Username: `admin`
   - Password: `admin123`

## Running Tests

### ReservationServiceTest
```bash
cd ocean-view-resort
java -cp build/classes test.com.oceanview.resort.service.ReservationServiceTest
```

### ValidationUtilTest
```bash
java -cp build/classes test.com.oceanview.resort.util.ValidationUtilTest
```

## Troubleshooting

**Port already in use:**
- Change port in WebServer.java main method
- Or kill process using port 8080

**Database connection errors:**
- Application will automatically use file-based storage
- Check console for database status messages

**ClassNotFoundException:**
- Clean and rebuild project in NetBeans
- Project → Clean and Build
