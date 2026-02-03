# How to Run the Application

## ⚠️ Important: Main Class Issue

**Error:** "Class 'com.oceanview.resort.ui.MenuSystem' does not have a main method"

**Solution:** `MenuSystem` is NOT the main class. Use `ReservationSystemMain` instead.

---

## ✅ Correct Way to Run

### Option 1: Run from Project (Recommended)

1. **In NetBeans Projects window:**
   - Right-click on **`OceanViewResort`** (the project name)
   - Select **Run**
   - OR press **F6** key

2. **Or use the Run button:**
   - Click the green **▶ Run Project** button in toolbar
   - Make sure project `OceanViewResort` is selected

### Option 2: Set Main Class Manually

1. Right-click **project** (`OceanViewResort`) → **Properties**
2. Go to **Run** category (left sidebar)
3. **Main Class:** Should be: `com.oceanview.resort.main.ReservationSystemMain`
4. If different, click **Browse...** and select:
   - Package: `com.oceanview.resort.main`
   - Class: `ReservationSystemMain`
5. Click **OK**
6. Now run: Right-click project → **Run** (or press F6)

---

## ❌ What NOT to Do

- ❌ Don't right-click on `MenuSystem.java` → Run File
- ❌ Don't right-click on any file in `ui` package → Run File
- ❌ Don't try to run files without main methods

---

## 📋 Which Files Have Main Methods?

### ✅ Files with main() - Can run directly:

1. **`ReservationSystemMain.java`**
   - Location: `src/com/oceanview/resort/main/ReservationSystemMain.java`
   - **This is the MAIN APPLICATION** - Use this!
   - Has main method: ✅ YES

2. **`WebServiceMain.java`**
   - Location: `src/com/oceanview/resort/main/WebServiceMain.java`
   - Optional - For running web service separately
   - Has main method: ✅ YES

### ❌ Files WITHOUT main() - Don't run directly:

1. **`MenuSystem.java`**
   - Location: `src/com/oceanview/resort/ui/MenuSystem.java`
   - Has main method: ❌ NO
   - This is called by ReservationSystemMain

---

## 🔍 Quick Verification

### Check if Main Class is Set Correctly:

1. Right-click project → **Properties**
2. Go to **Run** category
3. Check **Main Class** field:
   - ✅ Correct: `com.oceanview.resort.main.ReservationSystemMain`
   - ❌ Wrong: `com.oceanview.resort.ui.MenuSystem`

### If Main Class is Wrong:

1. Click **Browse...** button next to Main Class
2. Navigate to:
   - Package: `com.oceanview.resort.main`
   - Class: `ReservationSystemMain`
3. Click **OK**

---

## 🚀 Expected Output

When you run correctly, you should see:

```
============================================
   OCEAN VIEW RESORT
   Room Reservation System
============================================

Username: 
```

Then you can enter login credentials.

---

## 🛠️ Alternative: Command Line

If NetBeans continues to have issues, run from command line:

### Windows Command Prompt:
```cmd
cd "D:\Gayalan K Private\ocean-view-resort"
javac -d build/classes src\com\oceanview\resort\main\*.java src\com\oceanview\resort\model\*.java src\com\oceanview\resort\dao\*.java src\com\oceanview\resort\service\*.java src\com\oceanview\resort\ui\*.java src\com\oceanview\resort\util\*.java src\com\oceanview\resort\factory\*.java src\com\oceanview\resort\security\*.java src\com\oceanview\resort\webservice\*.java
java -cp build/classes com.oceanview.resort.main.ReservationSystemMain
```

### Or using Ant:
```cmd
cd "D:\Gayalan K Private\ocean-view-resort"
ant clean jar run
```

---

## ✅ Summary

**Always:**
- ✅ Run the **PROJECT**, not individual files
- ✅ Use `ReservationSystemMain` as main class
- ✅ Right-click project name → Run (F6)

**Never:**
- ❌ Run `MenuSystem.java` directly
- ❌ Run files without main methods

---

**If you still have issues, check:**
1. Project properties → Run → Main class is correct
2. File `ReservationSystemMain.java` exists and has main method
3. Project compiles without errors
