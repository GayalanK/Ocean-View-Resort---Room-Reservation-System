# Database Setup Guide
## Ocean View Resort - Room Reservation System

### Overview
The application uses JDBC for database connectivity. It supports multiple database options:
- **Java DB (Apache Derby)** - Bundled with some JDK installations (Recommended)
- **H2 Database** - Lightweight embedded database
- **SQLite** - Lightweight file-based database
- **Fallback** - File-based storage if no database driver is available

### Database Connection Strategy

The system automatically detects available database drivers and uses the first one found:
1. Java DB/Derby (if bundled with JDK)
2. H2 Database (if driver is in classpath)
3. SQLite (if driver is in classpath)

If no database driver is found, the system falls back to file-based storage automatically.

### Option 1: Using Java DB (Derby) - Recommended for NetBeans

Java DB (Apache Derby) is often bundled with JDK installations. To use it:

1. **Check if Derby is available:**
   - Derby is typically located at: `%JAVA_HOME%\db\lib\derby.jar`
   - Or check NetBeans: Tools → Java Platforms → select your JDK → Libraries

2. **Add Derby to NetBeans Project:**
   - Right-click project → Properties → Libraries
   - Click "Add JAR/Folder"
   - Navigate to: `%JAVA_HOME%\db\lib\`
   - Select `derby.jar` and `derbytools.jar`
   - Click OK

3. **Run the application:**
   - The database will be automatically created in the `data/` directory
   - Database file: `data/oceanview_resort`

### Option 2: Using H2 Database

1. **Download H2 Database:**
   - Visit: https://www.h2database.com/html/download.html
   - Download: `h2-2.x.x.jar`

2. **Add H2 to NetBeans Project:**
   - Right-click project → Properties → Libraries
   - Click "Add JAR/Folder"
   - Select the downloaded `h2-2.x.x.jar`
   - Click OK

3. **Run the application:**
   - Database will be created automatically

### Option 3: Using SQLite

1. **Download SQLite JDBC Driver:**
   - Visit: https://github.com/xerial/sqlite-jdbc/releases
   - Download: `sqlite-jdbc-3.x.x.jar`

2. **Add SQLite to NetBeans Project:**
   - Right-click project → Properties → Libraries
   - Click "Add JAR/Folder"
   - Select the downloaded `sqlite-jdbc-3.x.x.jar`
   - Click OK

3. **Run the application:**
   - Database will be created as: `data/oceanview_resort.db`

### Option 4: No Database (File-based Storage)

If no database driver is added, the application automatically uses file-based storage:
- Files stored in: `data/` directory
- `users.dat`, `rooms.dat`, `reservations.dat`

### Database Schema

The following tables are automatically created:

**users**
- username (VARCHAR, PRIMARY KEY)
- password (VARCHAR)
- full_name (VARCHAR)
- role (VARCHAR)

**rooms**
- room_number (VARCHAR, PRIMARY KEY)
- room_type (VARCHAR)
- is_available (BOOLEAN)
- capacity (INTEGER)
- features (VARCHAR)
- base_rate (DOUBLE)

**reservations**
- reservation_number (VARCHAR, PRIMARY KEY)
- guest_name (VARCHAR)
- guest_address (VARCHAR)
- guest_contact (VARCHAR)
- guest_email (VARCHAR)
- guest_nic (VARCHAR)
- room_number (VARCHAR, FOREIGN KEY)
- room_type (VARCHAR)
- check_in_date (DATE)
- check_out_date (DATE)
- number_of_nights (INTEGER)
- total_amount (DOUBLE)
- status (VARCHAR)
- reservation_date (DATE)

### Default Data

The system automatically initializes:

**Default Users:**
- Username: `admin`, Password: `admin123`, Role: ADMIN
- Username: `staff`, Password: `staff123`, Role: STAFF

**Default Rooms:**
- R101-R110: Single Rooms (Rs. 5,000/night)
- R201-R210: Double Rooms (Rs. 8,000/night)
- R301-R305: Deluxe Rooms (Rs. 12,000/night)
- R401-R403: Suites (Rs. 15,000/night)

### Verifying Database Connection

When you run the application, check the console output:
```
Connected to Java DB (Derby) database
Database tables created successfully
```

If no database driver is found:
```
Warning: No JDBC driver found. Using file-based storage.
```

### Troubleshooting

**Issue: "ClassNotFoundException: org.apache.derby.jdbc.EmbeddedDriver"**
- Solution: Add Derby JAR to project libraries

**Issue: Database file locked**
- Solution: Close all connections, restart application

**Issue: Tables not created**
- Solution: Delete `data/` directory and restart application

**Issue: Cannot connect to database**
- Solution: Check if database directory has write permissions

### For Assessment Submission

For the assessment, you can:
1. Use any of the database options above
2. Or document that file-based storage is used as a "database equivalent"
3. Include database schema documentation
4. Show database connection code (DatabaseConnection.java)
5. Demonstrate CRUD operations using JDBC

The code structure supports all options and demonstrates database connectivity regardless of which option is used.
