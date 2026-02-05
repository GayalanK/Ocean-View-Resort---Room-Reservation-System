# Assignment Requirements Checklist

This checklist maps the assignment brief requirements to the current implementation.

## Scenario Functional Requirements

1. **User Authentication (Login)** ✅
   - Login endpoint: `POST /login` and `POST /api/login`
   - Username/password validation and authentication using `AuthenticationService`.

2. **Add New Reservation** ✅
   - Reservation endpoint: `POST /api/reservations`
   - Stores reservation number, guest details, room, check-in/check-out.
   - Input validation for name, phone, email, room number, dates.

3. **Display Reservation Details** ✅
   - `GET /api/reservations` for all records.
   - `GET /api/reservations/{number}` for a specific reservation.

4. **Calculate and Print Bill** ✅
   - `GET /api/bill/{reservationNumber}`
   - Bill includes room rate, nights, total and long-stay discount policy.

5. **Help Section** ✅
   - Dashboard has a dedicated Help menu card and usage guidelines.

6. **Exit System** ✅
   - Dashboard Logout safely clears user session and redirects to login page.

## Task B requirements

- **Interactive user interfaces** ✅
  - Browser-based login and dashboard interfaces.

- **Validation mechanism** ✅
  - Front-end required fields and server-side validation checks.

- **Distributed app with web services** ✅
  - Custom Java web server + REST-style endpoints.

- **Appropriate design patterns** ✅
  - Singleton, Factory, DAO, Strategy, Front Controller.

- **Proper database usage** ✅
  - JDBC support for Derby/H2/SQLite with file-based fallback.

## Task C requirements (testing)

- **Test code and basic automation** ✅
  - `ValidationUtilTest` and `ReservationServiceTest` with executable test runners.

## Notes for report submission

- Include UML diagrams (Use Case, Class, Sequence) in the report.
- Include a test plan section (test rationale, test cases, test data, expected/actual results).
- Include Git workflow evidence and version history screenshots/links.
