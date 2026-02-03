# Web Application Guide
## Ocean View Resort - Room Reservation System

## 🌐 Web Application Overview

The project now includes a **full-featured web application** built with pure Java (no external libraries). The web interface provides the same functionality as the console application but with a modern, user-friendly web interface.

---

## 🚀 Running the Web Application

### Option 1: Using NetBeans

1. **Open Project** in NetBeans
2. **Right-click** on project → **Properties**
3. Go to **Run** category
4. Set **Main Class** to: `com.oceanview.resort.main.WebApplicationMain`
5. Click **OK**
6. **Run** the project (F6 or right-click → Run)

### Option 2: Command Line

```bash
cd "D:\Gayalan K Private\ocean-view-resort"

# Compile
javac -d build/classes src/com/oceanview/resort/**/*.java

# Run Web Application
java -cp build/classes com.oceanview.resort.main.WebApplicationMain
```

### Option 3: Using Ant

```bash
ant clean jar
java -cp dist/*.jar com.oceanview.resort.main.WebApplicationMain
```

---

## 📱 Accessing the Web Application

Once the server starts, you'll see:

```
============================================
   OCEAN VIEW RESORT
   Web Application Server
============================================

✅ Web Application is running!
🌐 Open your browser and navigate to:
   http://localhost:8080
```

### Open in Browser

1. Open any web browser (Chrome, Firefox, Edge, Safari)
2. Navigate to: **http://localhost:8080**
3. You'll see the login page

---

## 🔐 Login Credentials

Use the same credentials as the console application:

| Role | Username | Password |
|------|----------|----------|
| Administrator | `admin` | `admin123` |
| Staff | `staff` | `staff123` |

---

## ✨ Web Application Features

### 1. **Login Page** (`/`)
- Beautiful, modern login interface
- Secure authentication
- Session management

### 2. **Dashboard** (`/dashboard.html`)
- Clean, responsive interface
- Menu cards for all features:
  - ➕ Add New Reservation
  - 👁️ View Reservation
  - 💰 Calculate Bill
  - 🏨 Available Rooms
  - 📋 All Reservations
  - 🔍 Search Reservation
  - ❓ Help

### 3. **Add Reservation**
- Form-based reservation creation
- Input validation
- Real-time error messages
- Success notifications

### 4. **View Reservations**
- Search by reservation number
- Display complete reservation details
- Formatted output

### 5. **Calculate Bill**
- Generate detailed bills
- Show discounts
- Professional formatting

### 6. **Available Rooms**
- Table view of all available rooms
- Room details (type, rate, capacity, features)
- Real-time availability

### 7. **All Reservations**
- Complete list of all bookings
- Table format
- Easy to read

### 8. **Search**
- Search by guest name
- Partial name matching
- Multiple results display

### 9. **Help Section**
- System documentation
- Usage guidelines
- Room rates
- Discount policies

---

## 🏗️ Architecture

### Web Server
- **Class:** `WebApplicationServer.java`
- **Technology:** Pure Java HTTP Server (no external libraries)
- **Port:** 8080 (configurable)
- **Protocol:** HTTP/1.1

### Frontend
- **HTML Files:** `web/index.html`, `web/dashboard.html`
- **Technology:** Pure HTML, CSS, JavaScript (no frameworks)
- **Styling:** Modern CSS with gradients and animations

### Backend
- **Services:** Reuses existing service layer
- **Data Access:** Same DAO layer as console app
- **API:** RESTful endpoints

---

## 📁 Project Structure (Web)

```
ocean-view-resort/
├── web/                              # Web files directory
│   ├── index.html                    # Login page
│   └── dashboard.html                # Main dashboard
├── src/com/oceanview/resort/
│   ├── main/
│   │   └── WebApplicationMain.java   # Web server launcher
│   └── webservice/
│       └── WebApplicationServer.java # Web server implementation
```

---

## 🔌 API Endpoints

### Authentication
- **POST** `/login` - User login
  - Request: `{"username": "admin", "password": "admin123"}`
  - Response: `{"success": true, "message": "Login successful"}`

### Reservations
- **GET** `/reservations` - Get all reservations (JSON)
- **GET** `/reservations/{id}` - Get specific reservation
- **POST** `/reservations` - Create new reservation
- **GET** `/reservations/search?name={guestName}` - Search by guest name

### Rooms
- **GET** `/rooms` - Get all available rooms (JSON)

### Billing
- **GET** `/bill/{reservationNumber}` - Get bill details (text)

### System
- **GET** `/health` - Health check

---

## 🎨 Web Interface Features

### Design
- ✅ Modern, professional design
- ✅ Responsive layout
- ✅ Beautiful color scheme (gradient purple)
- ✅ Smooth animations
- ✅ User-friendly interface

### Functionality
- ✅ Form validation
- ✅ Error handling
- ✅ Success messages
- ✅ Session management
- ✅ Logout functionality

---

## 🆚 Console vs Web Application

### Console Application
- **Main Class:** `ReservationSystemMain`
- **Interface:** Command-line menu
- **Best For:** Terminal access, server environments
- **Run:** `java -cp build/classes com.oceanview.resort.main.ReservationSystemMain`

### Web Application
- **Main Class:** `WebApplicationMain`
- **Interface:** Web browser
- **Best For:** Desktop users, modern interface
- **Run:** `java -cp build/classes com.oceanview.resort.main.WebApplicationMain`
- **Access:** http://localhost:8080

---

## 🔧 Configuration

### Change Port

Edit `WebApplicationMain.java`:
```java
int port = 8080;  // Change to your desired port
```

Or pass as command line argument:
```bash
java -cp build/classes com.oceanview.resort.main.WebApplicationMain 9090
```

### Customize Web Files

Edit HTML files in `web/` directory:
- `web/index.html` - Login page
- `web/dashboard.html` - Dashboard

---

## 🐛 Troubleshooting

### Issue: "Port 8080 already in use"
**Solution:** 
1. Stop other application using port 8080
2. Or change port in `WebApplicationMain.java`

### Issue: "404 Not Found" when accessing pages
**Solution:**
- Ensure `web/` directory exists
- Check that HTML files are in `web/` directory
- Verify server is running

### Issue: "Cannot connect"
**Solution:**
- Verify server started successfully
- Check firewall settings
- Ensure using correct URL: http://localhost:8080

### Issue: Login not working
**Solution:**
- Verify default users exist (created automatically)
- Check username/password: admin/admin123
- Check server console for error messages

---

## 📝 Development Notes

### Adding New Pages

1. Create HTML file in `web/` directory
2. Add route in `WebApplicationServer.java`:
   ```java
   if (method.equals("GET") && path.equals("/newpage.html")) {
       serveFile(out, "web/newpage.html", "text/html");
       return;
   }
   ```

### Adding New API Endpoints

1. Add handler method in `WebApplicationServer.java`
2. Add route in `handleRequest()` method
3. Update frontend JavaScript to call new endpoint

---

## ✅ Advantages of Web Application

1. **User-Friendly:** Modern interface vs command-line
2. **Accessible:** Any device with a browser
3. **Professional:** Looks like commercial software
4. **No Installation:** Just run server and access via browser
5. **Responsive:** Works on desktop, tablet, mobile

---

## 🎯 Use Cases

### Console Application
- Server environments
- Automated scripts
- Terminal access
- System administration

### Web Application
- Front desk staff
- Office environments
- Remote access
- Better user experience

---

## 📚 Related Documentation

- [README.md](README.md) - Project overview
- [QUICK_START.md](QUICK_START.md) - Getting started
- [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) - Project structure

---

**Web Application Version:** 1.0  
**Last Updated:** December 2024
