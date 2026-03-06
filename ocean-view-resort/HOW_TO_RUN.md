# How to Run the Application (Without NetBeans)

This project has been configured to run directly from the command line without the need for NetBeans or any IDE-specific tools.

## Prerequisites

- **Java JDK 17 or higher** installed and added to your system PATH.
- **PowerShell** (available by default on Windows).

## Quick Start (PowerShell)

1.  **Open PowerShell** and navigate to the project directory:
    ```powershell
    cd "d:\Gayalan\Ocean-View-Resort---Room-Reservation-System-main\ocean-view-resort"
    ```

2.  **Compile the Code**:
    Run the following command to compile all Java source files into the `build/classes` directory:
    ```powershell
    if (-not (Test-Path "build/classes")) { New-Item -ItemType Directory -Force -Path "build/classes" }
    javac -d build/classes (Get-ChildItem -Recurse -Filter *.java src).FullName
    ```

3.  **Run the Server**:
    Start the web server using the compiled classes:
    ```powershell
    java -cp build/classes com.oceanview.resort.web.WebServer
    ```

4.  **Access the Application**:
    Open your web browser and go to:
    **[http://localhost:8080](http://localhost:8080)**

5.  **Log In**:
    Use the default administrator credentials:
    -   **Username:** `admin`
    -   **Password:** `admin123`

## Troubleshooting

### "Class Not Found" Error
If you see an error about missing classes, ensure you ran the compilation step (Step 2) successfully without errors.

### "Address already in use" Error
If the server fails to start because the port is busy, check if another instance of the application is already running. You can stop it by pressing `Ctrl+C` in the running terminal.

### Data Files
The application automatically creates a `data/` directory for `users.dat` and `rooms.dat`. If you want to reset the application data, you can delete this directory and restart the server.
