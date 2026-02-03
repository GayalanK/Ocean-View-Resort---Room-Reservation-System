# NetBeans Setup Guide

## Step-by-Step Instructions

### 1. Open Project in NetBeans

1. Launch NetBeans IDE
2. Go to **File → Open Project** (or press `Ctrl+Shift+O`)
3. Navigate to the folder: `D:\Gayalan K Private\ocean-view-resort`
4. Select the `ocean-view-resort` folder
5. Click **Open Project**

### 2. Set Main Class

1. Right-click on the **ocean-view-resort** project in Projects window
2. Select **Properties**
3. In the left panel, click **Run**
4. In the **Main Class** field, enter: `com.oceanview.resort.web.WebServer`
   - Or click **Browse** and navigate to: `com.oceanview.resort.web.WebServer`
5. In the **Arguments** field (optional), you can set port: `8080`
6. Click **OK**

### 3. Clean and Build Project

1. Right-click on the project
2. Select **Clean and Build** (or press `Shift+F11`)
3. Wait for build to complete
4. Check the Output window for any errors

### 4. Run the Application

1. Right-click on the project
2. Select **Run** (or press `F6`)
3. The Output window will show:
   ```
   ============================================
   Ocean View Resort - Web Application
   Server running on: http://localhost:8080
   ============================================
   ```

### 5. Access the Application

1. Open your web browser
2. Go to: `http://localhost:8080`
3. You should see the login page
4. Login with:
   - **Username:** `admin`
   - **Password:** `admin123`

## Troubleshooting

### Issue: "Cannot find main class"

**Solution:**
1. Ensure all Java files are in the correct package structure
2. Clean and Build the project
3. Check that `WebServer.java` exists at: `src/com/oceanview/resort/web/WebServer.java`
4. Verify the main class path in Project Properties → Run

### Issue: "Package does not exist"

**Solution:**
1. Right-click project → **Clean and Build**
2. Check that all package declarations match folder structure
3. Ensure all required files are present:
   - ReservationService.java
   - All servlet classes
   - All DAO classes

### Issue: "Port 8080 already in use"

**Solution:**
1. Change the port in WebServer.java main method:
   ```java
   int port = args.length > 0 ? Integer.parseInt(args[0]) : 9090;
   ```
2. Or kill the process using port 8080:
   - Windows: `netstat -ano | findstr :8080` then `taskkill /PID <PID> /F`

### Issue: "Cannot resolve symbol"

**Solution:**
1. Clean and Build project
2. Check that all source files are in `src` folder
3. Verify package names match folder structure

## Project Structure Verification

Make sure these files exist:

```
ocean-view-resort/
├── src/
│   └── com/oceanview/resort/
│       ├── web/
│       │   └── WebServer.java ✅
│       ├── service/
│       │   └── ReservationService.java ✅
│       ├── servlet/ (all servlet classes) ✅
│       ├── dao/ (all DAO classes) ✅
│       ├── model/ (all model classes) ✅
│       └── ... (other classes)
└── web/
    ├── index.html ✅
    └── dashboard.html ✅
```

## Alternative: Run from Command Line

If NetBeans still has issues:

1. Open Command Prompt
2. Navigate to project folder:
   ```cmd
   cd "D:\Gayalan K Private\ocean-view-resort"
   ```
3. Compile all Java files:
   ```cmd
   javac -d build/classes -sourcepath src src/com/oceanview/resort/**/*.java
   ```
4. Run the application:
   ```cmd
   java -cp build/classes com.oceanview.resort.web.WebServer
   ```

## Verify Files are Present

Check these critical files exist:
- ✅ `src/com/oceanview/resort/web/WebServer.java`
- ✅ `src/com/oceanview/resort/service/ReservationService.java`
- ✅ `src/com/oceanview/resort/servlet/Servlet.java`
- ✅ `src/com/oceanview/resort/web/ServletRouter.java`

If any are missing, they need to be recreated.
