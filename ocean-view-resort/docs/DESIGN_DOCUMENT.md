# Design Document
## Ocean View Resort - Room Reservation System

### 1. System Overview

The Ocean View Resort Room Reservation System is a Java-based application designed to manage hotel room bookings efficiently. The system addresses the problem of manual record keeping that was causing booking conflicts and delays.

### 2. System Architecture

The system follows a **three-tier architecture**:

```
┌─────────────────────────────────────┐
│     Presentation Layer              │
│  (Menu-driven Console UI + Web)     │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Business Logic Layer           │
│      (Service Classes)              │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Data Access Layer              │
│      (DAO + File Manager)           │
└─────────────────────────────────────┘
```

### 3. Design Patterns Implemented

#### 3.1 Singleton Pattern

**Implementation:** `FileManager` class

**Purpose:** Ensure only one instance of file manager exists to manage file connections efficiently.

**Rationale:** Prevents multiple file handles and ensures consistent file operations across the application.

```java
public class FileManager {
    private static FileManager instance;
    
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
}
```

#### 3.2 Factory Pattern

**Implementation:** `ReservationFactory` and `RoomFactory` classes

**Purpose:** Centralize object creation logic, making it easier to modify object creation without affecting client code.

**Rationale:** 
- Simplifies reservation number generation
- Provides convenient methods for creating different room types
- Encapsulates complex creation logic

**Usage:**
```java
Reservation reservation = ReservationFactory.createReservation(
    guest, room, checkInDate, checkOutDate);
```

#### 3.3 DAO Pattern (Data Access Object)

**Implementation:** `ReservationDAO`, `RoomDAO`, `UserDAO`

**Purpose:** Separate data access logic from business logic, providing abstraction over data persistence.

**Rationale:**
- Allows changing data storage mechanism without affecting business logic
- Provides a clean interface for data operations
- Makes testing easier by allowing mock implementations

**Structure:**
```
DAO Interface (implicit through class structure)
    ├── ReservationDAO
    ├── RoomDAO
    └── UserDAO
```

#### 3.4 Strategy Pattern

**Implementation:** `PricingStrategy` interface with `StandardPricingStrategy` and `DiscountPricingStrategy`

**Purpose:** Define a family of algorithms (pricing strategies), encapsulate each one, and make them interchangeable.

**Rationale:**
- Allows different pricing calculations (standard, discount, seasonal)
- Easy to add new pricing strategies without modifying existing code
- Provides flexibility for business rule changes

**Class Diagram:**
```
PricingStrategy (interface)
    ├── calculatePrice(Reservation)
    │
    ├── StandardPricingStrategy
    │   └── calculatePrice() → rate × nights
    │
    └── DiscountPricingStrategy
        └── calculatePrice() → (rate × nights) × 0.90 (for 7+ nights)
```

### 4. UML Diagrams

#### 4.1 Use Case Diagram

**Actors:**
- Staff Member
- System Administrator
- Web Service Client

**Use Cases:**

**Staff Member:**
1. Login to System
2. Add New Reservation
3. View Reservation Details
4. Calculate Bill
5. View Available Rooms
6. Search Reservations
7. View Help
8. Exit System

**System Administrator:**
1. Manage Users
2. View System Reports
3. Manage Rooms

**Web Service Client:**
1. Get Reservations (REST)
2. Create Reservation (REST)
3. Get Available Rooms (REST)

**Relationships:**
- Staff Member <<extends>> Login (authentication required)
- Add New Reservation <<includes>> Validate Input
- Calculate Bill <<extends>> View Reservation Details

#### 4.2 Class Diagram

**Main Classes:**

```
┌─────────────────────┐
│   User              │
├─────────────────────┤
│ - username: String  │
│ - password: String  │
│ - fullName: String  │
│ - role: String      │
└─────────────────────┘

┌─────────────────────┐
│   Guest             │
├─────────────────────┤
│ - name: String      │
│ - address: String   │
│ - contactNumber     │
│ - email: String     │
│ - nicNumber: String │
└─────────────────────┘

┌─────────────────────┐
│   Room              │
├─────────────────────┤
│ - roomNumber: String│
│ - roomType: Enum    │
│ - isAvailable: bool │
│ - capacity: int     │
│ - features: String  │
│ + getRate(): double │
└─────────────────────┘

┌─────────────────────┐
│   Reservation       │
├─────────────────────┤
│ - reservationNumber │
│ - guest: Guest      │
│ - room: Room        │
│ - checkInDate       │
│ - checkOutDate      │
│ - numberOfNights    │
│ - totalAmount       │
│ - status: String    │
│ + calculateNights() │
│ + calculateTotal()  │
└─────────────────────┘
```

**Service Layer:**

```
┌─────────────────────┐
│ ReservationService  │
├─────────────────────┤
│ - reservationDAO    │
│ - roomDAO           │
│ - billingService    │
├─────────────────────┤
│ + createReservation()│
│ + getReservation()  │
│ + calculateBill()   │
│ + searchReservations()│
└─────────────────────┘

┌─────────────────────┐
│  BillingService     │
├─────────────────────┤
│ - pricingStrategy   │
├─────────────────────┤
│ + calculateBill()   │
│ + generateBillDetails()│
└─────────────────────┘
```

**DAO Layer:**

```
┌─────────────────────┐
│   ReservationDAO    │
├─────────────────────┤
│ - fileManager       │
├─────────────────────┤
│ + save()            │
│ + findByReservationNumber()│
│ + findAll()         │
│ + delete()          │
└─────────────────────┘
```

#### 4.3 Sequence Diagram

**Sequence: Create New Reservation**

```
Staff          MenuSystem    ReservationService    ReservationDAO    RoomDAO    FileManager
  │                 │                │                   │              │            │
  │  Select "Add"   │                │                   │              │            │
  ├────────────────>│                │                   │              │            │
  │                 │  createReservation()               │              │            │
  │                 ├───────────────>│                   │              │            │
  │                 │                │  validateDates()  │              │            │
  │                 │                │  findRoom()       │              │            │
  │                 │                ├──────────────────>│              │            │
  │                 │                │                   │              │  readFile()│
  │                 │                │                   │<─────────────┼────────────┤
  │                 │                │<──────────────────┤              │            │
  │                 │                │  checkAvailability│              │            │
  │                 │                ├───────────────────┼──────────────>│            │
  │                 │                │                   │              │  readFile()│
  │                 │                │                   │              │<───────────┤
  │                 │                │<──────────────────┼──────────────┤            │
  │                 │                │  createReservation()              │            │
  │                 │                │  save()           │              │            │
  │                 │                ├──────────────────>│              │            │
  │                 │                │                   │  writeFile() │            │
  │                 │                │                   ├──────────────┼───────────>│
  │                 │                │                   │<─────────────┼────────────┤
  │                 │  Success       │                   │              │            │
  │                 │<───────────────┤                   │              │            │
  │  Display Result │                │                   │              │            │
  │<────────────────┤                │                   │              │            │
```

### 5. Assumptions

1. **Date Format:** All dates are in YYYY-MM-DD format (ISO 8601)
2. **Currency:** All monetary values are in Sri Lankan Rupees (LKR)
3. **Room Numbers:** Follow format "R" + 3 digits (e.g., R101, R201)
4. **Default Users:** System initializes with admin/admin123 and staff/staff123
5. **File Storage:** Data stored in "data/" directory relative to application root
6. **Discount Policy:** 10% discount applies for stays of 7 nights or more
7. **Room Availability:** Rooms are marked unavailable immediately upon reservation
8. **Web Service:** Runs on port 8080 by default

### 6. Data Storage

**File-based Storage:**
- `users.dat` - Serialized User objects
- `reservations.dat` - Serialized List<Reservation>
- `rooms.dat` - Serialized List<Room>

**File Format:** Java Serialization (binary format)

### 7. Security Considerations

1. **Authentication:** Simple username/password authentication
2. **Password Storage:** Passwords stored in plain text (for educational purposes only)
3. **Session Management:** Current user stored in AuthenticationService
4. **Input Validation:** All user inputs validated before processing

### 8. Error Handling

- Custom exception handling for business logic errors
- User-friendly error messages
- Input validation at multiple layers
- Try-catch blocks in UI layer

### 9. Future Enhancements

1. Database integration (MySQL/PostgreSQL)
2. GUI implementation (Java Swing/JavaFX)
3. Email notifications
4. Payment processing integration
5. Advanced reporting and analytics
6. Multi-user concurrent access handling
7. Encryption for sensitive data

---

**Document Version:** 1.0  
**Last Updated:** December 2024
