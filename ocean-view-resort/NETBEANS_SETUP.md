# NetBeans Setup Guide - Fix Main Class Issue

## Problem
Error: "Class 'com.oceanview.resort.ui.MenuSystem' does not have a main method"

## Solution

### Why This Happens
`MenuSystem` is NOT the main class - it's a UI helper class. The actual main class is `ReservationSystemMain`.

### How to Fix

#### Method 1: Set Main Class in Project Properties

1. **Right-click on the project** (`OceanViewResort`) in the Projects window
2. Select **Properties**
3. Go to **Run** category (left sidebar)
4. In the **Main Class** field, ensure it shows:
   ```
   com.oceanview.resort.main.ReservationSystemMain
   ```
5. If it's different, click **Browse...** and select:
   - Package: `com.oceanview.resort.main`
   - Class: `ReservationSystemMain`
6. Click **OK**

#### Method 2: Right-Click Project to Run

**Important:** Always run from the **project**, not from individual files!

1. **Right-click on the project** (`OceanViewResort`) in Projects window
2. Select **Run**
3. OR use keyboard shortcut: **F6**

**DO NOT:**
- ❌ Right-click on `MenuSystem.java` → Run File
- ❌ Right-click on any file in `ui` package → Run File

**DO:**
- ✅ Right-click on project → Run
- ✅ Use menu: Run → Run Project (F6)

#### Method 3: Select Main Class from Run Menu

1. Go to **Run** menu in NetBeans
2. Select **Set Project Configuration** → **Customize...**
3. In Main Class field, enter: `com.oceanview.resort.main.ReservationSystemMain`
4. Click **OK**

### Verify Main Class is Set Correctly

1. Right-click project → **Properties**
2. Go to **Run** category
3. Verify **Main Class** shows: `com.oceanview.resort.main.ReservationSystemMain`

### Correct File to Run

**Main Application:**
- File: `src/com/oceanview/resort/main/ReservationSystemMain.java`
- Has `main` method: ✅ YES
- Should be run: ✅ YES

**UI Helper:**
- File: `src/com/oceanview/resort/ui/MenuSystem.java`
- Has `main` method: ❌ NO
- Should be run: ❌ NO (called by ReservationSystemMain)

**Web Service:**
- File: `src/com/oceanview/resort/main/WebServiceMain.java`
- Has `main` method: ✅ YES
- Optional: Can be run separately for web services

## Quick Fix Steps

1. **Close any running application**
2. **Right-click on project** `OceanViewResort` → **Properties**
3. **Run** category → Set Main Class to: `com.oceanview.resort.main.ReservationSystemMain`
4. Click **OK**
5. **Right-click project** → **Run** (or press F6)

## Testing the Fix

After setting the main class correctly:
1. Click **Run** button (green play icon) or press **F6**
2. You should see:
   ```
   ============================================
      OCEAN VIEW RESORT
      Room Reservation System
   ============================================
   
   Username: 
   ```
3. If you see the login prompt, it's working! ✅

## Alternative: Run from Command Line

If NetBeans still has issues, you can run directly:

```bash
cd "D:\Gayalan K Private\ocean-view-resort"
javac -d build/classes src/com/oceanview/resort/**/*.java
java -cp build/classes com.oceanview.resort.main.ReservationSystemMain
```

## Troubleshooting

### Issue: Main class still wrong
- Check `nbproject/project.properties` line 20:
  ```
  main.class=com.oceanview.resort.main.ReservationSystemMain
  ```
- If different, change it to the above

### Issue: Can't find ReservationSystemMain
- Make sure the file exists at:
  `src/com/oceanview/resort/main/ReservationSystemMain.java`
- Clean and rebuild: Right-click project → **Clean and Build**

### Issue: Project won't compile
- Check for compilation errors
- Clean build: Right-click project → **Clean and Build**
- Check that all required files exist

---

**Remember:** Always run the **project**, not individual files (unless they have a main method and you specifically want to run them separately).
