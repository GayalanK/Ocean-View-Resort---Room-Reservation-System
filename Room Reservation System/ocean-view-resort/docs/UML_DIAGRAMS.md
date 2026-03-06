# UML Diagrams Documentation
## Ocean View Resort - Room Reservation System

### Introduction

This document provides detailed descriptions and textual representations of the UML diagrams for the Ocean View Resort Room Reservation System. Since visual diagram creation tools may not be available, these descriptions serve as comprehensive documentation that can be used to recreate diagrams using UML modeling tools like PlantUML, Draw.io, or Enterprise Architect.

---

## 1. Use Case Diagram

### Description

The Use Case Diagram illustrates the interactions between actors (users) and the system, showing all the functionalities available.

### Actors

1. **Staff Member**
   - Primary user who performs daily reservation operations

2. **System Administrator**
   - Manages system users and configurations

3. **Web Service Client**
   - External system that interacts via REST API

### Use Cases

#### For Staff Member:

1. **Login to System**
   - Staff authenticates using username and password
   - Precondition: User account exists
   - Postcondition: User session created

2. **Add New Reservation**
   - Create a new room booking
   - Includes: Validate guest information, Check room availability, Generate reservation number
   - Extends: Validate Input, Check Room Availability

3. **Display Reservation Details**
   - View complete reservation information
   - Input: Reservation number
   - Output: Reservation details

4. **Calculate Bill**
   - Generate bill for a reservation
   - Extends: Display Reservation Details
   - Output: Detailed bill with total amount

5. **View Available Rooms**
   - Display all rooms that are currently available
   - Output: List of available rooms with details

6. **View All Reservations**
   - Display all reservations in the system
   - Output: Complete reservation list

7. **Search Reservation by Guest Name**
   - Find reservations using guest name
   - Input: Guest name (partial match supported)

8. **View Help**
   - Display system usage guidelines
   - Output: Help documentation

9. **Exit System**
   - Safely close the application

#### For System Administrator:

1. **Manage Users**
   - Create, update, or delete user accounts

2. **View System Reports**
   - Generate various system reports

#### For Web Service Client:

1. **Get All Reservations (REST)**
   - HTTP GET /reservations
   - Returns: JSON array of reservations

2. **Get Reservation by ID (REST)**
   - HTTP GET /reservations/{id}
   - Returns: JSON object of reservation

3. **Create Reservation (REST)**
   - HTTP POST /reservations
   - Input: JSON reservation data
   - Returns: Created reservation

4. **Get Available Rooms (REST)**
   - HTTP GET /rooms
   - Returns: JSON array of available rooms

### Relationships

- **<<extends>>**: Login extends Add New Reservation (authentication required)
- **<<includes>>**: Add New Reservation includes Validate Input
- **<<includes>>**: Add New Reservation includes Check Room Availability
- **<<extends>>**: Calculate Bill extends Display Reservation Details

---

## 2. Class Diagram

### Package Structure

```
com.oceanview.resort
├── model
│   ├── User
│   ├── Guest
│   ├── Room
│   └── Reservation
├── dao
│   ├── UserDAO
│   ├── ReservationDAO
│   └── RoomDAO
├── service
│   ├── ReservationService
│   ├── BillingService
│   ├── PricingStrategy (interface)
│   ├── StandardPricingStrategy
│   └── DiscountPricingStrategy
├── factory
│   ├── ReservationFactory
│   └── RoomFactory
├── ui
│   └── MenuSystem
├── webservice
│   └── ReservationWebService
├── security
│   └── AuthenticationService
└── util
    ├── FileManager
    └── ValidationUtil
```

### Detailed Class Descriptions

#### Model Classes

**User**
```
Class: User
Attributes:
  - username: String
  - password: String
  - fullName: String
  - role: String
Methods:
  + getUsername(): String
  + setUsername(String): void
  + getPassword(): String
  + setPassword(String): void
  + getFullName(): String
  + setFullName(String): void
  + getRole(): String
  + setRole(String): void
  + toString(): String
```

**Guest**
```
Class: Guest
Attributes:
  - name: String
  - address: String
  - contactNumber: String
  - email: String
  - nicNumber: String
Methods:
  + getName(): String
  + setName(String): void
  + getAddress(): String
  + setAddress(String): void
  + getContactNumber(): String
  + setContactNumber(String): void
  + getEmail(): String
  + setEmail(String): void
  + getNicNumber(): String
  + setNicNumber(String): void
  + toString(): String
```

**Room**
```
Class: Room
Enum: RoomType
  - SINGLE ("Single Room", 5000.0)
  - DOUBLE ("Double Room", 8000.0)
  - DELUXE ("Deluxe Room", 12000.0)
  - SUITE ("Suite", 15000.0)

Attributes:
  - roomNumber: String
  - roomType: RoomType
  - isAvailable: boolean
  - capacity: int
  - features: String

Methods:
  + getRoomNumber(): String
  + setRoomNumber(String): void
  + getRoomType(): RoomType
  + setRoomType(RoomType): void
  + isAvailable(): boolean
  + setAvailable(boolean): void
  + getCapacity(): int
  + setCapacity(int): void
  + getFeatures(): String
  + setFeatures(String): void
  + getRate(): double
  + toString(): String
```

**Reservation**
```
Class: Reservation
Attributes:
  - reservationNumber: String
  - guest: Guest
  - room: Room
  - checkInDate: LocalDate
  - checkOutDate: LocalDate
  - numberOfNights: int
  - totalAmount: double
  - status: String
  - reservationDate: LocalDate

Methods:
  + getReservationNumber(): String
  + setReservationNumber(String): void
  + getGuest(): Guest
  + setGuest(Guest): void
  + getRoom(): Room
  + setRoom(Room): void
  + getCheckInDate(): LocalDate
  + setCheckInDate(LocalDate): void
  + getCheckOutDate(): LocalDate
  + setCheckOutDate(LocalDate): void
  + getNumberOfNights(): int
  + calculateNights(): void
  + getTotalAmount(): double
  + calculateTotalAmount(): void
  + getStatus(): String
  + setStatus(String): void
  + toString(): String
```

#### Service Layer Classes

**ReservationService**
```
Class: ReservationService
Attributes:
  - reservationDAO: ReservationDAO
  - roomDAO: RoomDAO
  - billingService: BillingService

Methods:
  + createReservation(Guest, String, String, String): String
  + getReservationByNumber(String): Reservation
  + getAllReservations(): List<Reservation>
  + searchReservationsByGuestName(String): List<Reservation>
  + calculateBill(String): String
  - hasDateConflict(String, LocalDate, LocalDate): boolean
```

**BillingService**
```
Class: BillingService
Attributes:
  - pricingStrategy: PricingStrategy

Methods:
  + calculateBill(Reservation): double
  + setPricingStrategy(PricingStrategy): void
  + generateBillDetails(Reservation): String
```

**PricingStrategy (Interface)**
```
Interface: PricingStrategy
Methods:
  + calculatePrice(Reservation): double
```

**StandardPricingStrategy**
```
Class: StandardPricingStrategy implements PricingStrategy
Methods:
  + calculatePrice(Reservation): double
```

**DiscountPricingStrategy**
```
Class: DiscountPricingStrategy implements PricingStrategy
Attributes:
  - DISCOUNT_THRESHOLD: int = 7
  - DISCOUNT_RATE: double = 0.10

Methods:
  + calculatePrice(Reservation): double
```

#### DAO Layer Classes

**ReservationDAO**
```
Class: ReservationDAO
Attributes:
  - fileManager: FileManager

Methods:
  + save(Reservation): void
  + findByReservationNumber(String): Reservation
  + findAll(): List<Reservation>
  + findByGuestName(String): List<Reservation>
  + delete(String): void
  + findActiveReservations(): List<Reservation>
```

**RoomDAO**
```
Class: RoomDAO
Attributes:
  - fileManager: FileManager

Methods:
  + save(Room): void
  + findByRoomNumber(String): Room
  + findAll(): List<Room>
  + findAvailableRooms(): List<Room>
  + findByRoomType(RoomType): List<Room>
  - initializeDefaultRooms(): void
```

**UserDAO**
```
Class: UserDAO
Attributes:
  - fileManager: FileManager

Methods:
  + save(User): void
  + findByUsername(String): User
  + findAll(): List<User>
  - initializeDefaultUsers(): void
```

#### Factory Classes

**ReservationFactory**
```
Class: ReservationFactory
Attributes:
  - reservationCounter: static int = 1000

Methods:
  + createReservation(Guest, Room, LocalDate, LocalDate): Reservation
  - generateReservationNumber(): String
  + resetCounter(): void
```

**RoomFactory**
```
Class: RoomFactory
Methods:
  + createRoom(String, RoomType, int, String): Room
  + createSingleRoom(String): Room
  + createDoubleRoom(String): Room
  + createDeluxeRoom(String): Room
  + createSuite(String): Room
```

#### Utility Classes

**FileManager (Singleton)**
```
Class: FileManager
Attributes:
  - instance: static FileManager
  - DATA_DIR: static final String = "data/"

Methods:
  + getInstance(): FileManager
  + writeObjectToFile(Object, String): void
  + readObjectFromFile(String): Object
  + writeListToFile(List, String): void
  + readListFromFile(String): List
  + appendToFile(String, String): void
  + readLinesFromFile(String): List<String>
```

**ValidationUtil**
```
Class: ValidationUtil
Methods:
  + isValidEmail(String): boolean
  + isValidPhone(String): boolean
  + isValidDate(String): boolean
  + isDateInFuture(LocalDate): boolean
  + isValidDateRange(LocalDate, LocalDate): boolean
  + isValidName(String): boolean
  + isValidRoomNumber(String): boolean
  + sanitizeInput(String): String
```

### Relationships

1. **Reservation** → **Guest** (composition)
2. **Reservation** → **Room** (association)
3. **ReservationService** → **ReservationDAO** (dependency)
4. **ReservationService** → **RoomDAO** (dependency)
5. **ReservationService** → **BillingService** (dependency)
6. **BillingService** → **PricingStrategy** (dependency)
7. **StandardPricingStrategy** → **PricingStrategy** (implements)
8. **DiscountPricingStrategy** → **PricingStrategy** (implements)
9. **ReservationDAO** → **FileManager** (dependency)
10. **RoomDAO** → **FileManager** (dependency)
11. **UserDAO** → **FileManager** (dependency)
12. **ReservationFactory** → **Reservation** (creates)
13. **RoomFactory** → **Room** (creates)

---

## 3. Sequence Diagram

### Sequence: Add New Reservation

```
Participant: Staff
Participant: MenuSystem
Participant: ReservationService
Participant: RoomDAO
Participant: ReservationDAO
Participant: FileManager
Participant: ReservationFactory
Participant: BillingService

Staff -> MenuSystem: Select "Add New Reservation"
MenuSystem -> Staff: Request guest information
Staff -> MenuSystem: Provide guest details
MenuSystem -> Staff: Request room selection
MenuSystem -> ReservationService: createReservation(guest, roomNumber, checkIn, checkOut)
ReservationService -> ReservationService: validateDates()
alt Invalid dates
    ReservationService -> MenuSystem: throw IllegalArgumentException
    MenuSystem -> Staff: Display error message
else Valid dates
    ReservationService -> RoomDAO: findByRoomNumber(roomNumber)
    RoomDAO -> FileManager: readListFromFile("rooms.dat")
    FileManager -> RoomDAO: return List<Room>
    RoomDAO -> ReservationService: return Room
    ReservationService -> ReservationService: hasDateConflict(roomNumber, checkIn, checkOut)
    ReservationService -> ReservationDAO: findAll()
    ReservationDAO -> FileManager: readListFromFile("reservations.dat")
    FileManager -> ReservationDAO: return List<Reservation>
    ReservationDAO -> ReservationService: return List<Reservation>
    ReservationService -> ReservationService: check for conflicts
    alt Room not available
        ReservationService -> MenuSystem: throw IllegalArgumentException
        MenuSystem -> Staff: Display "Room not available" error
    else Room available
        ReservationService -> ReservationFactory: createReservation(guest, room, checkIn, checkOut)
        ReservationFactory -> ReservationFactory: generateReservationNumber()
        ReservationFactory -> ReservationService: return Reservation
        ReservationService -> BillingService: calculateBill(reservation)
        BillingService -> BillingService: apply pricing strategy
        BillingService -> ReservationService: return total amount
        ReservationService -> Reservation: setTotalAmount(total)
        ReservationService -> Reservation: setStatus("CONFIRMED")
        ReservationService -> ReservationDAO: save(reservation)
        ReservationDAO -> FileManager: readListFromFile("reservations.dat")
        FileManager -> ReservationDAO: return List<Reservation>
        ReservationDAO -> ReservationDAO: add/update reservation
        ReservationDAO -> FileManager: writeListToFile(List, "reservations.dat")
        FileManager -> ReservationDAO: confirm write
        ReservationDAO -> ReservationService: confirm save
        ReservationService -> RoomDAO: save(room with isAvailable=false)
        RoomDAO -> FileManager: writeListToFile(List, "rooms.dat")
        RoomDAO -> ReservationService: confirm save
        ReservationService -> MenuSystem: return reservationNumber
        MenuSystem -> Staff: Display "Reservation created successfully"
    end
end
```

### Sequence: Calculate Bill

```
Participant: Staff
Participant: MenuSystem
Participant: ReservationService
Participant: ReservationDAO
Participant: BillingService

Staff -> MenuSystem: Select "Calculate Bill"
MenuSystem -> Staff: Request reservation number
Staff -> MenuSystem: Provide reservation number
MenuSystem -> ReservationService: calculateBill(reservationNumber)
ReservationService -> ReservationDAO: findByReservationNumber(reservationNumber)
ReservationDAO -> FileManager: readListFromFile("reservations.dat")
FileManager -> ReservationDAO: return List<Reservation>
ReservationDAO -> ReservationService: return Reservation
alt Reservation not found
    ReservationService -> MenuSystem: return "Reservation not found"
    MenuSystem -> Staff: Display error
else Reservation found
    ReservationService -> BillingService: calculateBill(reservation)
    BillingService -> BillingService: apply pricing strategy
    BillingService -> ReservationService: return total amount
    ReservationService -> Reservation: setTotalAmount(total)
    ReservationService -> ReservationDAO: save(reservation)
    ReservationService -> BillingService: generateBillDetails(reservation)
    BillingService -> ReservationService: return formatted bill string
    ReservationService -> MenuSystem: return bill details
    MenuSystem -> Staff: Display bill
end
```

---

## 4. Component Diagram

```
┌─────────────────────────────────────────┐
│      Presentation Layer                 │
│  ┌──────────────┐  ┌─────────────────┐ │
│  │ MenuSystem   │  │ ReservationWeb  │ │
│  │ (Console UI) │  │ Service (REST)  │ │
│  └──────────────┘  └─────────────────┘ │
└─────────────────────────────────────────┘
              │
              ↓
┌─────────────────────────────────────────┐
│      Business Logic Layer               │
│  ┌──────────────────┐  ┌─────────────┐ │
│  │ Reservation      │  │ Billing     │ │
│  │ Service          │  │ Service     │ │
│  └──────────────────┘  └─────────────┘ │
│  ┌──────────────────┐                  │
│  │ Authentication   │                  │
│  │ Service          │                  │
│  └──────────────────┘                  │
└─────────────────────────────────────────┘
              │
              ↓
┌─────────────────────────────────────────┐
│      Data Access Layer                  │
│  ┌──────────┐  ┌──────────┐  ┌───────┐ │
│  │Reservation│  │  Room    │  │ User  │ │
│  │   DAO    │  │   DAO    │  │  DAO  │ │
│  └──────────┘  └──────────┘  └───────┘ │
└─────────────────────────────────────────┘
              │
              ↓
┌─────────────────────────────────────────┐
│      Utility Layer                      │
│  ┌──────────────┐  ┌─────────────────┐ │
│  │ FileManager  │  │ ValidationUtil  │ │
│  │ (Singleton)  │  │                 │ │
│  └──────────────┘  └─────────────────┘ │
└─────────────────────────────────────────┘
              │
              ↓
┌─────────────────────────────────────────┐
│      File System                        │
│  data/users.dat                         │
│  data/reservations.dat                  │
│  data/rooms.dat                         │
└─────────────────────────────────────────┘
```

---

## 5. Activity Diagram: Create Reservation

```
[Start]
  │
  ↓
[Staff selects "Add New Reservation"]
  │
  ↓
[Enter guest information]
  │
  ↓
{Validate guest data}
  │
  ├─ Invalid → [Display error] → [End]
  │
  └─ Valid → [Display available rooms]
               │
               ↓
            [Select room]
               │
               ↓
            [Enter check-in date]
               │
               ↓
            [Enter check-out date]
               │
               ↓
            {Validate dates}
               │
               ├─ Invalid → [Display error] → [End]
               │
               └─ Valid → [Check room availability]
                            │
                            ↓
                         {Room available?}
                            │
                            ├─ No → [Display "Room not available"] → [End]
                            │
                            └─ Yes → [Generate reservation number]
                                       │
                                       ↓
                                    [Create reservation object]
                                       │
                                       ↓
                                    [Calculate total amount]
                                       │
                                       ↓
                                    [Save reservation]
                                       │
                                       ↓
                                    [Mark room as unavailable]
                                       │
                                       ↓
                                    [Display confirmation]
                                       │
                                       ↓
                                    [End]
```

---

## Notes for Diagram Creation

When creating visual diagrams using UML tools:

1. **Use Case Diagram:**
   - Use oval shapes for use cases
   - Use stick figures for actors
   - Use <<extends>> and <<includes>> stereotypes for relationships

2. **Class Diagram:**
   - Use rectangles for classes
   - Show attributes in second compartment
   - Show methods in third compartment
   - Use arrows with different line styles for relationships (solid = association, dashed = dependency, triangle = inheritance)

3. **Sequence Diagram:**
   - Use lifelines (vertical lines) for each participant
   - Use horizontal arrows for messages
   - Use activation boxes for method execution
   - Use alt/opt frames for conditional logic

4. **Component Diagram:**
   - Use rectangles with <<component>> stereotype
   - Show dependencies with dashed arrows

5. **Activity Diagram:**
   - Use rounded rectangles for activities
   - Use diamonds for decisions
   - Use circles for start/end nodes
   - Use arrows to show flow

---

**Document Version:** 1.0  
**Last Updated:** December 2024
