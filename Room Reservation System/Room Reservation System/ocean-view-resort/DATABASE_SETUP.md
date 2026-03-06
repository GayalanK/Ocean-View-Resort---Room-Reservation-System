# Database Setup Guide
## Ocean View Resort - Room Reservation System

### Overview
The application supports multiple database options, but defaults to file-based storage if no database driver is found.

### Options:

1.  **File-based Storage (Default)**
    -   Requires no configuration.
    -   Automatically creates `data/` directory.
    -   Stores data in `users.dat`, `rooms.dat`, and `reservations.dat`.

2.  **Java DB (Apache Derby)**
    -   Recommended if you have Derby installed.
    -   Ensure `derby.jar` is on your classpath.

3.  **H2 Database**
    -   Ensure `h2.jar` is on your classpath.

4.  **SQLite**
    -   Ensure `sqlite-jdbc.jar` is on your classpath.

### How to use a Database

To use a database (Derby, H2, or SQLite), download the appropriate JDBC driver (`.jar` file) and include it in your classpath when running the application.

**Example (Windows/PowerShell):**

```powershell
# Assuming you have put 'h2.jar' in a 'lib' folder
java -cp "build/classes;lib/h2.jar" com.oceanview.resort.web.WebServer
```

**Example (Linux/Mac):**

```bash
# Assuming you have put 'h2.jar' in a 'lib' folder
java -cp "build/classes:lib/h2.jar" com.oceanview.resort.web.WebServer
```

### Database Schema (Automatically Created)

If a database connection is successful, the application will automatically create the following tables:

**users**
- `username` (VARCHAR, PRIMARY KEY)
- `password` (VARCHAR)
- `full_name` (VARCHAR)
- `role` (VARCHAR)

**rooms**
- `room_number` (VARCHAR, PRIMARY KEY)
- `room_type` (VARCHAR)
- `is_available` (BOOLEAN)
- `capacity` (INTEGER)
- ... (other room details)

**reservations**
- `reservation_number` (VARCHAR, PRIMARY KEY)
- `guest_name` (VARCHAR)
- ... (reservation details)

### Default Data

Upon first run (with database or file storage), the system initializes:

**Default Users:**
- `admin` / `admin123` (Role: ADMIN)
- `staff` / `staff123` (Role: STAFF)

**Default Rooms:**
- Single Rooms (R101-R110)
- Double Rooms (R201-R210)
- Deluxe Rooms (R301-R305)
- Suites (R401-R403)

### Verifying Connection

Check the console output when starting the server:

-   **Success:** "Connected to [Database Type] database"
-   **Fallback:** "Warning: No JDBC driver found. Using file-based storage."

### Troubleshooting

-   **"ClassNotFoundException"**: The JDBC driver is not in your classpath. Check the path to your `.jar` file in the `-cp` argument.
-   **Database locked**: Ensure no other process is using the database file.
