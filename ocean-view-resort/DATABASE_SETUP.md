# Database Setup Guide
## Ocean View Resort - Room Reservation System

### Overview
The application uses JDBC for database connectivity. It supports multiple database options with automatic fallback to file-based storage.

### Supported Databases

1. **Java DB (Apache Derby)** - Bundled with some JDK installations
2. **H2 Database** - Lightweight embedded database  
3. **SQLite** - File-based database
4. **File-based Storage** - Automatic fallback if no database driver is available

### Quick Setup

#### Option 1: Using Java DB (Derby) - Recommended

1. Check if Derby is in your JDK:
   - Location: `%JAVA_HOME%\db\lib\derby.jar`

2. Add to NetBeans:
   - Right-click project → Properties → Libraries
   - Add JAR/Folder → Select `derby.jar`

3. Run application - Database will be created automatically in `data/` folder

#### Option 2: Using H2 Database

1. Download H2: https://www.h2database.com/html/download.html
2. Add `h2-*.jar` to project libraries
3. Run application

#### Option 3: No Database Driver

The application will automatically use file-based storage in the `data/` directory.

### Database Schema

Tables are automatically created:
- **users** - User accounts
- **rooms** - Room inventory  
- **reservations** - Booking records
- **guests** - Guest information

### Default Data

- Users: admin/admin123, staff/staff123
- Rooms: R101-R110 (Single), R201-R210 (Double), R301-R305 (Deluxe), R401-R403 (Suite)

See DatabaseConnection.java for full schema details.
