# Quick Start Guide
## Ocean View Resort - Room Reservation System

## Prerequisites

- **Java JDK 17 or higher**
- A command line terminal (PowerShell, CMD, or Bash)
- Git (optional, for version control)

## Running the Application

This application is designed to run easily from the command line.

### 1. Open Terminal
Navigate to the `ocean-view-resort` folder in your terminal.

```bash
cd ocean-view-resort
```

### 2. Compile the Project
Run the following command to compile the source code:

**Windows (PowerShell):**
```powershell
if (-not (Test-Path "build/classes")) { New-Item -ItemType Directory -Force -Path "build/classes" }
javac -d build/classes (Get-ChildItem -Recurse -Filter *.java src).FullName
```

**Linux/Mac:**
```bash
mkdir -p build/classes
find src -name "*.java" > sources.txt
javac -d build/classes @sources.txt
```

### 3. Run the Server
Start the web server:

```bash
java -cp build/classes com.oceanview.resort.web.WebServer
```

### 4. Access the App
Open your browser and visit: **http://localhost:8080**

## Default Login Credentials

**Administrator:**
- Username: `admin`
- Password: `admin123`

**Staff:**
- Username: `staff`
- Password: `staff123`

## Project Structure

```
ocean-view-resort/
├── src/                    # Source code
│   └── com/oceanview/resort/
│       ├── web/            # Web server & routing
│       ├── model/          # Data models
│       ├── dao/            # Data access
│       ├── service/        # Business logic
│       └── ...
├── web/                    # HTML files
├── data/                   # Data storage (created automatically)
├── build/                  # Compiled classes
└── docs/                   # Documentation
```

## Troubleshooting

### "Class Not Found"
Ensure you ran the compilation step successfully.

### "Port already in use"
Another application (or a previous instance of this app) is using port 8080. Close it or check your running processes.
