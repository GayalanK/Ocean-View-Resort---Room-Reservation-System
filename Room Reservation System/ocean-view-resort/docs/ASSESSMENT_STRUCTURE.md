# Assessment Structure & Checklist
## CIS6003 Advanced Programming - WRIT1
### Online Room Reservation System

**Total Marks:** 100  
**Word Count:** 4,000 words  
**Submission Format:** PDF via Turnitin

---

## TASK A: UML DIAGRAMS (20 Marks)

### Required Deliverables:

- [ ] **Use Case Diagram**
  - [ ] All actors identified (Staff Member, System Administrator, Web Service Client)
  - [ ] All use cases documented
  - [ ] Relationships clearly shown (extends, includes)
  - [ ] Clear explanations provided
  - [ ] Assumptions documented
  
- [ ] **Class Diagram**
  - [ ] All classes shown with attributes and methods
  - [ ] Relationships documented (composition, association, dependency, inheritance)
  - [ ] Design patterns indicated
  - [ ] Package structure shown
  - [ ] Clear explanations provided
  
- [ ] **Sequence Diagram**
  - [ ] At least 2-3 key scenarios (Add Reservation, Calculate Bill, Login)
  - [ ] All participants/lifelines shown
  - [ ] Message flows documented
  - [ ] Alternative flows (alt/opt) included
  - [ ] Clear explanations provided

### Documentation Requirements:
- [ ] Design decisions explained
- [ ] Assumptions documented with valid reasons
- [ ] Diagrams created using UML tools (PlantUML, Draw.io, Enterprise Architect, etc.)
- [ ] Diagrams included in main report with proper referencing

### Expected Content:
1. Introduction to UML diagrams
2. Use Case Diagram with descriptions
3. Class Diagram with descriptions
4. Sequence Diagram with descriptions
5. Design rationale and assumptions

**Current Status:** ✅ UML diagrams documented in `docs/UML_DIAGRAMS.md`

---

## TASK B: INTERACTIVE SYSTEM DEVELOPMENT (40 Marks)

### Core Requirements:

#### 1. User Authentication (Login)
- [ ] Username and password authentication
- [ ] Secure session management
- [ ] Error handling for invalid credentials
- [ ] Current Status: ✅ Implemented (AuthenticationService.java)

#### 2. Add New Reservation
- [ ] Collect all required information:
  - [ ] Reservation number (auto-generated)
  - [ ] Guest name
  - [ ] Address
  - [ ] Contact number
  - [ ] Room type
  - [ ] Check-in date
  - [ ] Check-out date
- [ ] Validation of all inputs
- [ ] Room availability checking
- [ ] Date conflict detection
- [ ] Current Status: ✅ Implemented

#### 3. Display Reservation Details
- [ ] Search by reservation number
- [ ] Display complete booking information
- [ ] Error handling for not found
- [ ] Current Status: ✅ Implemented

#### 4. Calculate and Print Bill
- [ ] Calculate total based on nights and room rate
- [ ] Apply discount logic (if applicable)
- [ ] Format bill nicely
- [ ] Print/display functionality
- [ ] Current Status: ✅ Implemented (with discount strategy)

#### 5. Help Section
- [ ] System usage guidelines
- [ ] Instructions for staff
- [ ] FAQ or troubleshooting
- [ ] Current Status: ✅ Implemented in web interface

#### 6. Exit System
- [ ] Safe application closure
- [ ] Data persistence
- [ ] Current Status: ✅ Implemented

### Additional Requirements:

#### User-Friendly Interfaces
- [ ] Clean and intuitive UI
- [ ] Clear navigation
- [ ] Responsive design (if web-based)
- [ ] Error messages are user-friendly
- [ ] Current Status: ✅ Web-based UI (index.html, dashboard.html)

#### Input Validation
- [ ] Email validation
- [ ] Phone number validation
- [ ] Date validation (format and logical)
- [ ] Required field validation
- [ ] Data type validation
- [ ] Current Status: ✅ ValidationUtil.java implemented

#### Reports
- [ ] Reservation list report
- [ ] Available rooms report
- [ ] Revenue/booking summary (optional but valuable)
- [ ] Daily/weekly reports (optional)
- [ ] Current Status: ✅ View all reservations, available rooms

### Must-Have Features:

#### ✅ Distributed Application with Web Services
- [ ] REST API endpoints implemented
- [ ] JSON responses
- [ ] HTTP methods properly used (GET, POST)
- [ ] Web service documentation
- [ ] Current Status: ✅ WebServer.java with REST endpoints

**Endpoints to Document:**
- [ ] GET /reservations (get all)
- [ ] GET /reservations/{id} (get by ID)
- [ ] POST /reservations (create new)
- [ ] GET /rooms (get available rooms)
- [ ] GET /health (health check)

#### ✅ Design Patterns Implemented
- [ ] **Singleton Pattern** - Document where used
  - [ ] Current Status: ✅ FileManager (Singleton)
- [ ] **Factory Pattern** - Document where used
  - [ ] Current Status: ✅ ReservationFactory
- [ ] **DAO Pattern** - Document where used
  - [ ] Current Status: ✅ UserDAO, RoomDAO, ReservationDAO
- [ ] **Strategy Pattern** - Document where used
  - [ ] Current Status: ✅ PricingStrategy (StandardPricingStrategy, DiscountPricingStrategy)

**Documentation Required:**
- [ ] Explain why each pattern was chosen
- [ ] Show code examples
- [ ] Discuss benefits

#### ✅ Database for Data Storage
- [ ] Database design documented
- [ ] Tables/entities defined
- [ ] Relationships shown
- [ ] Current Status: ⚠️ Using file-based storage (data/*.dat files)
  - [ ] **Note:** Need to either:
    - [ ] Migrate to actual database (MySQL, PostgreSQL, H2, SQLite)
    - [ ] OR justify file-based storage as "database equivalent" in documentation
    - [ ] Consider SQLite (lightweight, no server needed) for easy implementation

### Technical Architecture:
- [ ] Three-tier architecture demonstrated
  - [ ] Presentation Layer (Web UI)
  - [ ] Business Logic Layer (Services)
  - [ ] Data Access Layer (DAO)
- [ ] Current Status: ✅ Architecture implemented

### Code Quality:
- [ ] Clean code principles
- [ ] Proper error handling
- [ ] Code comments
- [ ] Consistent naming conventions
- [ ] Current Status: ✅ Good structure

---

## TASK C: TESTING & TEST-DRIVEN DEVELOPMENT (20 Marks)

### Required Deliverables:

#### 1. Test Rationale and Approach
- [ ] Explain testing strategy chosen
- [ ] Justify approach
- [ ] Current Status: ✅ Documented in TEST_PLAN.md

#### 2. Test-Driven Development (TDD) Explanation
- [ ] Explain how TDD was used
- [ ] Show TDD cycle (Red-Green-Refactor)
- [ ] Provide examples of test-first development
- [ ] Document TDD benefits observed
- [ ] Current Status: ✅ Test classes exist, need TDD narrative

#### 3. Test Plan
- [ ] Comprehensive test plan document
- [ ] Test categories defined
- [ ] Test cases listed
- [ ] Current Status: ✅ TEST_PLAN.md exists

**Test Categories Required:**
- [ ] Unit Tests
- [ ] Integration Tests
- [ ] System Tests
- [ ] Validation Tests
- [ ] Web Service Tests

#### 4. Test Data Design
- [ ] Valid test data sets
- [ ] Invalid test data sets
- [ ] Boundary test cases
- [ ] Edge cases
- [ ] Current Status: ✅ Some test data in TEST_PLAN.md

#### 5. Test Classes for the System
- [ ] Unit test classes
- [ ] Integration test classes
- [ ] Test coverage adequate
- [ ] Current Status: ✅ ReservationServiceTest.java, ValidationUtilTest.java
- [ ] **Need to Add:**
  - [ ] More unit tests for other services
  - [ ] Integration tests
  - [ ] Web service tests

#### 6. Test Execution Documentation
- [ ] How to run tests
- [ ] Test execution results
- [ ] Screenshots of test runs
- [ ] Pass/fail statistics
- [ ] Current Status: ⚠️ Need execution documentation

#### 7. Test Automation
- [ ] Automated test execution
- [ ] Test scripts or CI/CD integration
- [ ] Continuous testing approach
- [ ] Current Status: ⚠️ Basic automation exists, need enhancement

#### 8. Evaluation and Lessons Learned
- [ ] What worked well
- [ ] Challenges faced
- [ ] Improvements made
- [ ] Metrics (code coverage, etc.)
- [ ] Current Status: ❌ Need to add this section

#### 9. Traceability (Requirements → Design)
- [ ] Traceability matrix
- [ ] Link each requirement to tests
- [ ] Show how tests verify requirements
- [ ] Current Status: ❌ Need to create traceability matrix

### Test Documentation Template:
```
1. Introduction
2. Test Strategy
3. TDD Approach
4. Test Plan
   - Unit Tests
   - Integration Tests
   - System Tests
5. Test Data
6. Test Execution
7. Test Results
8. Test Automation
9. Coverage Analysis
10. Lessons Learned
11. Traceability Matrix
```

**Current Status:** ✅ Basic test plan exists, needs enhancement

---

## TASK D: GIT/GITHUB REPOSITORY & VERSION CONTROL (20 Marks)

### Required Deliverables:

#### 1. Public Git/GitHub Repository
- [ ] Repository is public
- [ ] Repository URL shared in documentation
- [ ] Current Status: ⚠️ Need to verify and document

#### 2. Multiple Commits/Versions
- [ ] Multiple meaningful commits
- [ ] Daily updates with new features
- [ ] Clear commit messages
- [ ] Current Status: ⚠️ Need to verify commit history

**Commit Strategy:**
- [ ] Feature-based commits
- [ ] Bug fix commits
- [ ] Documentation commits
- [ ] Test commits
- [ ] Refactoring commits

#### 3. Version Control Techniques Documented
- [ ] Branching strategy explained
- [ ] Merge strategy documented
- [ ] Tagging strategy (if used)
- [ ] Current Status: ✅ GIT_WORKFLOW.md exists

**Techniques to Demonstrate:**
- [ ] Feature branches
- [ ] Main/master branch
- [ ] Merge commits
- [ ] Commit history visualization

#### 4. Workflows (CI/CD)
- [ ] GitHub Actions workflows (if applicable)
- [ ] Automated builds
- [ ] Automated testing
- [ ] Deployment automation
- [ ] Current Status: ❌ Need to create GitHub Actions

**Workflow Ideas:**
```yaml
- Build workflow (compile Java)
- Test workflow (run tests)
- Deploy workflow (if deploying)
```

#### 5. Deployment Demonstrated
- [ ] Application deployed (if applicable)
- [ ] Deployment process documented
- [ ] Deployment screenshots
- [ ] Current Status: ⚠️ JAR file created, deployment optional

#### 6. Repository Link in Documentation
- [ ] GitHub URL in README
- [ ] GitHub URL in main report
- [ ] Repository description clear
- [ ] Current Status: ⚠️ Need to verify

### Documentation Requirements:
- [ ] Screenshots of GitHub repository
- [ ] Screenshots of commit history
- [ ] Screenshots of branches/merges
- [ ] Screenshots of workflows (if CI/CD)
- [ ] Version control strategy explained
- [ ] Workflow diagrams

**Current Status:** ✅ GIT_WORKFLOW.md exists, needs enhancement

---

## MAIN REPORT STRUCTURE (4000 Words)

### Report Outline:

1. **Title Page**
   - [ ] Student ID, Module Code, Assessment ID
   - [ ] Title: "Online Room Reservation System"

2. **Abstract/Executive Summary** (~200 words)
   - [ ] Overview of the system
   - [ ] Key achievements
   - [ ] Technologies used

3. **Table of Contents**
   - [ ] All sections listed
   - [ ] Page numbers

4. **Introduction** (~300 words)
   - [ ] Problem statement
   - [ ] Objectives
   - [ ] Scope
   - [ ] System overview

5. **TASK A: UML Diagrams** (~800 words, 20 marks)
   - [ ] Use Case Diagram with explanations
   - [ ] Class Diagram with explanations
   - [ ] Sequence Diagram with explanations
   - [ ] Design decisions
   - [ ] Assumptions

6. **TASK B: System Development** (~1600 words, 40 marks)
   - [ ] System architecture
   - [ ] Design patterns (with explanations)
   - [ ] Web services implementation
   - [ ] Database design
   - [ ] Features implemented
   - [ ] User interface design
   - [ ] Validation mechanisms
   - [ ] Reports generated
   - [ ] Screenshots/diagrams

7. **TASK C: Testing & TDD** (~800 words, 20 marks)
   - [ ] Test rationale
   - [ ] TDD approach explained
   - [ ] Test plan
   - [ ] Test data
   - [ ] Test execution and results
   - [ ] Test automation
   - [ ] Coverage analysis
   - [ ] Lessons learned
   - [ ] Traceability matrix

8. **TASK D: Version Control** (~800 words, 20 marks)
   - [ ] Repository link
   - [ ] Version control techniques used
   - [ ] Workflow demonstrations
   - [ ] Commit history
   - [ ] CI/CD workflows (if applicable)
   - [ ] Deployment process

9. **Conclusion** (~200 words)
   - [ ] Summary of achievements
   - [ ] Challenges faced
   - [ ] Future improvements
   - [ ] Lessons learned

10. **References**
    - [ ] Harvard referencing style
    - [ ] All external sources cited
    - [ ] In-text citations

11. **Appendices** (if needed)
    - [ ] Code snippets
    - [ ] Full test results
    - [ ] Additional diagrams

### Report Formatting:
- [ ] A4 paper size
- [ ] Margins: 1.5" left, 1" right, top and bottom
- [ ] Line spacing: 1.5
- [ ] Font: Times New Roman
- [ ] Headings: 14pt, Bold
- [ ] Body: 12pt
- [ ] Page numbers: bottom, right
- [ ] Word count: 4000 (excluding references)

---

## PRIORITY ACTIONS

### High Priority (Must Complete):

1. **Database Migration or Justification**
   - Option A: Migrate to SQLite/MySQL/PostgreSQL
   - Option B: Document file-based storage as "database equivalent"

2. **Enhanced Testing**
   - Add more test classes
   - Create traceability matrix
   - Document test execution with screenshots
   - Add test automation documentation

3. **GitHub Repository**
   - Ensure repository is public
   - Verify commit history is meaningful
   - Create GitHub Actions workflows (CI/CD)
   - Document repository link

4. **Report Writing**
   - Start compiling all documentation into report format
   - Add screenshots
   - Ensure word count is met
   - Format according to requirements

### Medium Priority:

1. **Additional Reports**
   - Revenue reports
   - Booking statistics
   - Daily/weekly summaries

2. **Enhanced UI/UX**
   - Improve web interface design
   - Add more interactive features

3. **Documentation Enhancement**
   - Add more design pattern explanations
   - Expand on TDD approach
   - Add more technical details

---

## SUBMISSION CHECKLIST

### Before Submission:

- [ ] All four tasks completed
- [ ] Report formatted correctly
- [ ] Word count met (4000 words)
- [ ] All diagrams included
- [ ] Screenshots included
- [ ] GitHub repository is public and accessible
- [ ] Code is complete and functional
- [ ] All tests pass
- [ ] References formatted (Harvard style)
- [ ] File named correctly: `studentID_CIS6003_WRIT1.pdf`
- [ ] PDF file created
- [ ] Proofread and spell-checked
- [ ] Submitted via Turnitin on Moodle before deadline

---

**Document Version:** 1.0  
**Last Updated:** December 2024  
**Use this as a working checklist throughout your project!**
