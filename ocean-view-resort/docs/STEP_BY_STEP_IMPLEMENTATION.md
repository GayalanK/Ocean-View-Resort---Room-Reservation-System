# Step-by-Step Implementation Guide
## Ocean View Resort - Room Reservation System

This guide provides a detailed, step-by-step approach to implementing the entire reservation system from scratch.

---

## Phase 1: Project Setup (Step 1-3)

### Step 1: Create Project Structure in NetBeans

1. **Open NetBeans IDE**
2. **Create New Project:**
   - File → New Project
   - Select "Java" → "Java Application"
   - Click "Next"
   - Project Name: `OceanViewResort`
   - Project Location: Choose your workspace
   - Main Class: `com.oceanview.resort.main.ReservationSystemMain`
   - Click "Finish"

3. **Create Package Structure:**
   Right-click on "Source Packages" → New → Java Package, create:
   - `com.oceanview.resort.main`
   - `com.oceanview.resort.model`
   - `com.oceanview.resort.dao`
   - `com.oceanview.resort.service`
   - `com.oceanview.resort.factory`
   - `com.oceanview.resort.ui`
   - `com.oceanview.resort.util`
   - `com.oceanview.resort.security`
   - `com.oceanview.resort.webservice`

4. **Create Test Package:**
   - Right-click on "Test Packages" → New → Java Package
   - Create: `com.oceanview.resort.service`
   - Create: `com.oceanview.resort.util`

### Step 2: Create Data Directory

1. Right-click on project root
2. New → Folder
3. Name: `data`
4. This folder will store data files

### Step 3: Create Documentation Folder

1. Right-click on project root
2. New → Folder
3. Name: `docs`

---

## Phase 2: Core Models (Step 4-7)

### Step 4: Implement User Model

**File:** `src/com/oceanview/resort/model/User.java`

1. Right-click on `model` package → New → Java Class
2. Name: `User`
3. Copy the User class code from the existing file
4. **Key Points:**
   - Implements Serializable for file persistence
   - Contains username, password, fullName, role
   - Standard getters and setters

**Test:** Right-click → Compile (should compile without errors)

### Step 5: Implement Guest Model

**File:** `src/com/oceanview/resort/model/Guest.java`

1. Create new class `Guest` in `model` package
2. **Fields:**
   - name, address, contactNumber, email, nicNumber
3. All String fields
4. Implements Serializable

### Step 6: Implement Room Model

**File:** `src/com/oceanview/resort/model/Room.java`

1. Create `Room` class in `model` package
2. **Important:** Include RoomType enum:
   ```java
   public enum RoomType {
       SINGLE("Single Room", 5000.0),
       DOUBLE("Double Room", 8000.0),
       DELUXE("Deluxe Room", 12000.0),
       SUITE("Suite", 15000.0);
       // ... with description and baseRate
   }
   ```
3. Fields: roomNumber, roomType, isAvailable, capacity, features
4. Method: `getRate()` returns roomType.getBaseRate()

### Step 7: Implement Reservation Model

**File:** `src/com/oceanview/resort/model/Reservation.java`

1. Create `Reservation` class
2. **Fields:**
   - reservationNumber, guest, room, checkInDate, checkOutDate
   - numberOfNights, totalAmount, status, reservationDate
3. **Important Methods:**
   - `calculateNights()` - Calculate nights between dates
   - `calculateTotalAmount()` - Calculate total price
4. Use `LocalDate` for dates (java.time.LocalDate)

**Test:** Compile all model classes. Should have no errors.

---

## Phase 3: Utility Layer (Step 8-9)

### Step 8: Implement FileManager (Singleton Pattern)

**File:** `src/com/oceanview/resort/util/FileManager.java`

1. Create `FileManager` class in `util` package
2. **Singleton Implementation:**
   ```java
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
   ```
3. **Key Methods:**
   - `writeObjectToFile()` - Write single object
   - `readObjectFromFile()` - Read single object
   - `writeListToFile()` - Write list of objects
   - `readListFromFile()` - Read list of objects
4. **Data Directory:** Use `"data/"` as base path
5. Create data directory if it doesn't exist in constructor

**Test:** Compile. Check that only one instance can be created.

### Step 9: Implement ValidationUtil

**File:** `src/com/oceanview/resort/util/ValidationUtil.java`

1. Create `ValidationUtil` class
2. **Key Validation Methods:**
   - `isValidEmail()` - Check email format using regex
   - `isValidPhone()` - Check phone number format
   - `isValidDate()` - Check date format (YYYY-MM-DD)
   - `isDateInFuture()` - Ensure date is not in past
   - `isValidDateRange()` - Check-out after check-in
   - `isValidName()` - Name length validation
3. Use regex patterns for email and phone validation

---

## Phase 4: Data Access Layer (Step 10-12)

### Step 10: Implement UserDAO

**File:** `src/com/oceanview/resort/dao/UserDAO.java`

1. Create `UserDAO` class in `dao` package
2. **Fields:**
   - `FILENAME = "users.dat"`
   - `fileManager` (FileManager instance)
3. **Methods:**
   - `save(User)` - Save/update user
   - `findByUsername(String)` - Find user by username
   - `findAll()` - Get all users
   - `initializeDefaultUsers()` - Create admin and staff users
4. **Logic:**
   - Read list from file
   - Add or update user in list
   - Write list back to file
5. In `findAll()`, if list is empty, call `initializeDefaultUsers()`

**Test:** Create a simple test to save and retrieve a user.

### Step 11: Implement RoomDAO

**File:** `src/com/oceanview/resort/dao/RoomDAO.java`

1. Create `RoomDAO` class
2. **Methods:**
   - `save(Room)` - Save room
   - `findByRoomNumber(String)` - Find specific room
   - `findAll()` - Get all rooms
   - `findAvailableRooms()` - Filter available rooms
   - `findByRoomType(RoomType)` - Filter by type
   - `initializeDefaultRooms()` - Create default room inventory
3. **Default Rooms:**
   - Single Rooms: R101-R110
   - Double Rooms: R201-R210
   - Deluxe Rooms: R301-R305
   - Suites: R401-R403
4. Call `initializeDefaultRooms()` in `findAll()` if empty

### Step 12: Implement ReservationDAO

**File:** `src/com/oceanview/resort/dao/ReservationDAO.java`

1. Create `ReservationDAO` class
2. **Methods:**
   - `save(Reservation)` - Save/update reservation
   - `findByReservationNumber(String)` - Find by ID
   - `findAll()` - Get all reservations
   - `findByGuestName(String)` - Search by guest name
   - `delete(String)` - Remove reservation
   - `findActiveReservations()` - Get confirmed/pending only
3. **Update Logic:**
   - Check if reservation exists (by number)
   - If exists, replace it; if not, add new one

**Test:** Test saving and retrieving reservations.

---

## Phase 5: Factory Pattern (Step 13-14)

### Step 13: Implement ReservationFactory

**File:** `src/com/oceanview/resort/factory/ReservationFactory.java`

1. Create `ReservationFactory` class in `factory` package
2. **Static counter:**
   ```java
   private static int reservationCounter = 1000;
   ```
3. **Method:** `createReservation(Guest, Room, LocalDate, LocalDate)`
   - Generate unique reservation number
   - Create Reservation object
   - Return reservation
4. **Reservation Number Format:**
   - "RES-" + timestamp + "-" + counter
   - Example: "RES-1234567-1000"
5. Increment counter after each creation

### Step 14: Implement RoomFactory

**File:** `src/com/oceanview/resort/factory/RoomFactory.java`

1. Create `RoomFactory` class
2. **Methods:**
   - `createRoom(roomNumber, roomType, capacity, features)` - Generic
   - `createSingleRoom(roomNumber)` - Convenience method
   - `createDoubleRoom(roomNumber)` - Convenience method
   - `createDeluxeRoom(roomNumber)` - Convenience method
   - `createSuite(roomNumber)` - Convenience method
3. Each convenience method sets appropriate room type and features

---

## Phase 6: Business Logic Layer (Step 15-18)

### Step 15: Implement PricingStrategy Interface

**File:** `src/com/oceanview/resort/service/PricingStrategy.java`

1. Create interface `PricingStrategy` in `service` package
2. **Single Method:**
   ```java
   double calculatePrice(Reservation reservation);
   ```

### Step 16: Implement Pricing Strategies

**Files:**
- `src/com/oceanview/resort/service/StandardPricingStrategy.java`
- `src/com/oceanview/resort/service/DiscountPricingStrategy.java`

**StandardPricingStrategy:**
1. Implement `PricingStrategy` interface
2. Calculate: `room.getRate() * reservation.getNumberOfNights()`

**DiscountPricingStrategy:**
1. Implement `PricingStrategy` interface
2. Constants:
   - `DISCOUNT_THRESHOLD = 7` (nights)
   - `DISCOUNT_RATE = 0.10` (10%)
3. Logic:
   - If nights >= 7, apply 10% discount
   - Otherwise, standard calculation

### Step 17: Implement BillingService

**File:** `src/com/oceanview/resort/service/BillingService.java`

1. Create `BillingService` class
2. **Fields:**
   - `pricingStrategy` (PricingStrategy)
3. **Constructor:**
   - Default: Use StandardPricingStrategy
   - Parameterized: Accept any PricingStrategy
4. **Methods:**
   - `calculateBill(Reservation)` - Calculate total
   - `setPricingStrategy(PricingStrategy)` - Change strategy
   - `generateBillDetails(Reservation)` - Format bill as string
5. **Bill Format:**
   - Header with resort name
   - Reservation details
   - Room information
   - Dates and nights
   - Subtotal, discount (if any), total
   - Footer with separators

### Step 18: Implement ReservationService

**File:** `src/com/oceanview/resort/service/ReservationService.java`

1. Create `ReservationService` class
2. **Fields:**
   - `reservationDAO`
   - `roomDAO`
   - `billingService` (with DiscountPricingStrategy)
3. **Method: `createReservation()`**
   - Validate dates (format, range, future date)
   - Find room by number
   - Check room availability
   - Check for date conflicts
   - Create reservation using factory
   - Calculate total using billing service
   - Save reservation
   - Mark room as unavailable
   - Return reservation number
4. **Method: `hasDateConflict()`**
   - Check if room is booked for overlapping dates
   - Compare check-in/out dates with existing reservations
5. **Other Methods:**
   - `getReservationByNumber()` - Retrieve reservation
   - `getAllReservations()` - Get all
   - `searchReservationsByGuestName()` - Search
   - `calculateBill()` - Generate bill details

**Important:** Add proper error handling with meaningful messages.

---

## Phase 7: Security (Step 19)

### Step 19: Implement AuthenticationService

**File:** `src/com/oceanview/resort/security/AuthenticationService.java`

1. Create `AuthenticationService` class in `security` package
2. **Fields:**
   - `userDAO`
   - `currentUser` (User)
3. **Methods:**
   - `login(username, password)` - Authenticate user
     - Find user by username
     - Compare passwords
     - Set currentUser if valid
     - Return true/false
   - `logout()` - Clear current user
   - `isAuthenticated()` - Check if logged in
   - `getCurrentUser()` - Get current user

---

## Phase 8: User Interface (Step 20-21)

### Step 20: Implement MenuSystem

**File:** `src/com/oceanview/resort/ui/MenuSystem.java`

1. Create `MenuSystem` class in `ui` package
2. **Fields:**
   - `BufferedReader reader` (for input)
   - `ReservationService`
   - `RoomDAO`
3. **Method: `displayMainMenu()`**
   - Print menu options (1-8)
   - Format nicely with borders
4. **Method: `handleMenuChoice(int choice)`**
   - Switch statement for each option
   - Call appropriate method
5. **Menu Options Implementation:**
   - **Option 1 - Add Reservation:**
     - Get guest information
     - Display available rooms
     - Get room selection
     - Get dates
     - Call service to create reservation
     - Display success/error
   - **Option 2 - Display Reservation:**
     - Get reservation number
     - Retrieve and display details
   - **Option 3 - Calculate Bill:**
     - Get reservation number
     - Generate and display bill
   - **Option 4 - View Available Rooms:**
     - Get available rooms
     - Display in formatted table
   - **Option 5 - View All Reservations:**
     - Get all reservations
     - Display each with details
   - **Option 6 - Search:**
     - Get guest name
     - Search and display results
   - **Option 7 - Help:**
     - Display comprehensive help text
   - **Option 8 - Exit:**
     - Display goodbye message
     - System.exit(0)
6. **Helper Methods:**
   - `formatReservationDetails()` - Format reservation for display
   - `getUserInput()` - Read user input
   - `getMenuChoice()` - Read menu choice

**Important:** Add try-catch blocks for all operations.

### Step 21: Implement Main Application Class

**File:** `src/com/oceanview/resort/main/ReservationSystemMain.java`

1. Create `ReservationSystemMain` class
2. **Main Method Steps:**
   - Display welcome message
   - Create AuthenticationService
   - **Login Loop:**
     - Prompt for username
     - Prompt for password
     - Attempt login
     - Allow max 3 attempts
     - Exit if all fail
   - **Main Menu Loop:**
     - Display menu
     - Get user choice
     - Handle choice
     - Continue until exit (choice 8)
3. **Error Handling:**
   - Catch IOException
   - Display friendly error messages

**Test:** Run the application. Should show login prompt.

---

## Phase 9: Web Services (Step 22)

### Step 22: Implement ReservationWebService

**File:** `src/com/oceanview/resort/webservice/ReservationWebService.java`

1. Create `ReservationWebService` class in `webservice` package
2. **Fields:**
   - `ReservationService`
   - `RoomDAO`
   - `ServerSocket`
   - `boolean running`
3. **Constructor:**
   - Accept port number
   - Create ServerSocket
4. **Method: `start()`**
   - Set running = true
   - Start thread to accept connections
   - For each connection, create ClientHandler thread
5. **Inner Class: ClientHandler implements Runnable**
   - Handle HTTP requests
   - Parse request (method, path)
   - Route to appropriate handler
   - Send JSON response
6. **Handler Methods:**
   - `handleGetAllReservations()` - GET /reservations
   - `handleGetReservation()` - GET /reservations/{id}
   - `handleGetAvailableRooms()` - GET /rooms
   - `handleCreateReservation()` - POST /reservations
   - `handleHealthCheck()` - GET /health
7. **JSON Formatting:**
   - Simple string concatenation (no external libs)
   - Convert objects to JSON strings manually
8. **HTTP Response:**
   - Send proper HTTP headers
   - Status codes: 200, 201, 400, 404, 500

### Optional: WebServiceMain

**File:** `src/com/oceanview/resort/main/WebServiceMain.java`

1. Create separate main class for web service
2. Start web service on port 8080
3. Keep running until Enter pressed
4. Useful for testing web services independently

---

## Phase 10: Testing (Step 23-24)

### Step 23: Create Test Classes

**File:** `test/com/oceanview/resort/service/ReservationServiceTest.java`

1. Create test class in test package
2. **Main Method:**
   - Run all test methods
   - Count passed/failed
   - Display results
3. **Test Methods:**
   - `testCreateReservation()` - Test reservation creation
   - `testCalculateBill()` - Test bill calculation
   - `testDateValidation()` - Test date validation
   - `testGetReservation()` - Test retrieval
4. Each test:
   - Setup test data
   - Execute operation
   - Verify result
   - Handle exceptions

**File:** `test/com/oceanview/resort/util/ValidationUtilTest.java`

1. Test all validation methods
2. Test valid and invalid inputs
3. Use assertions to verify results

### Step 24: Run Tests

1. Compile test classes
2. Run each test class
3. Verify all tests pass
4. Document any failures
5. Fix issues and re-test

---

## Phase 11: Documentation (Step 25-27)

### Step 25: Create README.md

**File:** `README.md` (in project root)

1. Project overview
2. Features list
3. Installation instructions
4. Usage guide
5. Project structure
6. Technology used

### Step 26: Create Test Plan

**File:** `docs/TEST_PLAN.md`

1. Test objectives
2. TDD approach explanation
3. Test categories
4. Test cases table
5. Test data
6. Test execution plan
7. Results summary

### Step 27: Create Design Documentation

**Files:**
- `docs/DESIGN_DOCUMENT.md` - System design
- `docs/UML_DIAGRAMS.md` - UML descriptions
- `GIT_WORKFLOW.md` - Version control guide

---

## Phase 12: Version Control (Step 28-29)

### Step 28: Initialize Git Repository

1. Open terminal in project root
2. **Initialize Git:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit: Project structure"
   ```
3. **Create GitHub repository** (on GitHub website)
4. **Add remote:**
   ```bash
   git remote add origin <repository-url>
   git push -u origin main
   ```

### Step 29: Create CI/CD Workflow

**File:** `.github/workflows/ci.yml`

1. Create `.github/workflows/` directory
2. Create `ci.yml` file
3. Configure GitHub Actions:
   - Java 17 setup
   - Compile source
   - Run tests
   - Create JAR artifact
4. Commit and push

---

## Phase 13: Final Testing & Refinement (Step 30)

### Step 30: Comprehensive Testing

1. **Functional Testing:**
   - Test each menu option
   - Test error scenarios
   - Test edge cases
   - Verify all validations

2. **Integration Testing:**
   - Test complete workflows
   - Test data persistence
   - Test web services

3. **User Acceptance:**
   - Test from user perspective
   - Verify user-friendly messages
   - Check help documentation

4. **Bug Fixes:**
   - Fix any identified issues
   - Improve error messages
   - Optimize code if needed

---

## Implementation Checklist

Use this checklist to track your progress:

### Phase 1: Setup
- [ ] Create NetBeans project
- [ ] Create package structure
- [ ] Create data folder

### Phase 2: Models
- [ ] User.java
- [ ] Guest.java
- [ ] Room.java (with enum)
- [ ] Reservation.java

### Phase 3: Utilities
- [ ] FileManager.java (Singleton)
- [ ] ValidationUtil.java

### Phase 4: DAO Layer
- [ ] UserDAO.java
- [ ] RoomDAO.java
- [ ] ReservationDAO.java

### Phase 5: Factory
- [ ] ReservationFactory.java
- [ ] RoomFactory.java

### Phase 6: Services
- [ ] PricingStrategy.java (interface)
- [ ] StandardPricingStrategy.java
- [ ] DiscountPricingStrategy.java
- [ ] BillingService.java
- [ ] ReservationService.java

### Phase 7: Security
- [ ] AuthenticationService.java

### Phase 8: UI
- [ ] MenuSystem.java
- [ ] ReservationSystemMain.java

### Phase 9: Web Services
- [ ] ReservationWebService.java
- [ ] WebServiceMain.java (optional)

### Phase 10: Testing
- [ ] ReservationServiceTest.java
- [ ] ValidationUtilTest.java
- [ ] All tests passing

### Phase 11: Documentation
- [ ] README.md
- [ ] TEST_PLAN.md
- [ ] DESIGN_DOCUMENT.md
- [ ] UML_DIAGRAMS.md

### Phase 12: Version Control
- [ ] Git repository initialized
- [ ] GitHub repository created
- [ ] CI/CD workflow configured

### Phase 13: Final
- [ ] All features tested
- [ ] Documentation complete
- [ ] Ready for submission

---

## Tips for Implementation

1. **Compile Frequently:** Compile after each class to catch errors early
2. **Test Incrementally:** Test each component as you build it
3. **Use Version Control:** Commit after each major step
4. **Follow Naming Conventions:** Use camelCase for methods, PascalCase for classes
5. **Add Comments:** Comment complex logic
6. **Error Handling:** Always handle exceptions properly
7. **Validate Input:** Validate all user inputs
8. **Test Edge Cases:** Test boundary conditions
9. **Keep It Simple:** Don't overcomplicate solutions
10. **Document As You Go:** Update documentation while coding

---

## Common Issues & Solutions

### Issue: "Cannot find symbol"
**Solution:** Check imports, ensure classes are in correct packages

### Issue: "FileNotFoundException"
**Solution:** Ensure data directory exists, check file paths

### Issue: Date parsing errors
**Solution:** Use YYYY-MM-DD format, use LocalDate.parse()

### Issue: Serialization errors
**Solution:** Ensure all classes implement Serializable

### Issue: Web service not responding
**Solution:** Check port availability, verify server started correctly

---

## Estimated Time per Phase

- Phase 1: 30 minutes
- Phase 2: 2 hours
- Phase 3: 1.5 hours
- Phase 4: 2 hours
- Phase 5: 1 hour
- Phase 6: 3 hours
- Phase 7: 1 hour
- Phase 8: 3 hours
- Phase 9: 2.5 hours
- Phase 10: 2 hours
- Phase 11: 3 hours
- Phase 12: 1 hour
- Phase 13: 2 hours

**Total Estimated Time: ~25 hours**

---

**Good luck with your implementation!**
