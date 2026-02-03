# How to Open Project in NetBeans

## ✅ Project Configuration Files Restored

All NetBeans project configuration files have been recreated. You should now be able to open the project.

---

## 📋 Steps to Open Project in NetBeans

### Method 1: Open Existing Project

1. **Launch NetBeans IDE**
2. **Go to:** `File → Open Project...` (or press `Ctrl+Shift+O`)
3. **Navigate to:** `D:\Gayalan K Private\ocean-view-resort`
4. **Select the folder:** `ocean-view-resort`
5. **Click:** `Open Project`
6. The project should appear in the Projects window

### Method 2: Open from Recent Projects

1. **Launch NetBeans**
2. **File → Open Recent Projects**
3. Select `OceanViewResort` if it appears in the list

### Method 3: Drag and Drop

1. **Open Windows File Explorer**
2. **Navigate to:** `D:\Gayalan K Private\ocean-view-resort`
3. **Drag the folder** `ocean-view-resort` into NetBeans IDE
4. Drop it in the Projects window

---

## 🔍 Verify Project is Loaded

After opening, you should see:

1. **Projects Window** shows:
   - `OceanViewResort` (project name)
   - `Source Packages` folder
   - `Test Packages` folder
   - Various packages under Source Packages

2. **Files Window** (if visible) shows:
   - All project files and folders

---

## ⚙️ Configure Main Class

After opening the project:

1. **Right-click** on project `OceanViewResort`
2. Select **Properties**
3. Go to **Run** category (left sidebar)
4. **Main Class** should be set to:
   ```
   com.oceanview.resort.main.ReservationSystemMain
   ```
5. If different, click **Browse...** and select:
   - Package: `com.oceanview.resort.main`
   - Class: `ReservationSystemMain`
6. Click **OK**

---

## 🚀 Test the Project

### Build the Project

1. **Right-click** project → **Clean and Build**
2. Should see "BUILD SUCCESSFUL" in Output window

### Run the Project

1. **Right-click** project → **Run** (or press F6)
2. Application should start in Output window
3. You should see login prompt

---

## ❌ Troubleshooting

### Issue: "Not a NetBeans project"

**Solution:**
- Ensure you're opening the `ocean-view-resort` folder (not parent folder)
- Check that these files exist:
  - `build.xml` (in project root)
  - `nbproject/project.xml`
  - `nbproject/project.properties`

### Issue: Project opens but no source files visible

**Solution:**
1. **Right-click** project → **Properties**
2. Go to **Sources** category
3. Verify **Source Package Folders** shows: `src`
4. Verify **Test Package Folders** shows: `test`
5. Click **OK**

### Issue: Compilation errors

**Solution:**
1. **Right-click** project → **Clean and Build**
2. Check Output window for specific errors
3. Verify Java version is set correctly:
   - Right-click project → Properties → Sources
   - **Source/Binary Format:** Java 17

### Issue: Can't find main class

**Solution:**
1. Right-click project → Properties
2. Go to **Run** category
3. Set **Main Class:** `com.oceanview.resort.main.ReservationSystemMain`
4. Click **Browse...** if needed to select it

---

## 📁 Required Files for NetBeans

The following files must exist for NetBeans to recognize the project:

```
ocean-view-resort/
├── build.xml                    ✅ Required
└── nbproject/
    ├── project.xml              ✅ Required
    ├── project.properties       ✅ Required
    ├── build-impl.xml           ✅ Required
    └── private/
        └── private.properties   ✅ Required (can be empty)
```

All these files have been recreated and should be present.

---

## 🎯 Quick Checklist

- [ ] NetBeans IDE is installed and running
- [ ] Project folder `ocean-view-resort` exists
- [ ] All configuration files are present
- [ ] Project opens in NetBeans
- [ ] Source files are visible
- [ ] Project builds successfully
- [ ] Main class is configured
- [ ] Project runs successfully

---

## 💡 Tips

1. **Always open the project folder** (ocean-view-resort), not the parent folder
2. **Use File → Open Project** for best results
3. **Clean and Build** first time to ensure everything compiles
4. **Check Output window** for any error messages

---

## 🔄 If Still Having Issues

If the project still won't open:

1. **Close NetBeans completely**
2. **Restart NetBeans**
3. **Try opening again** using Method 1 above
4. **Check NetBeans version:** Should be 12.0 or higher
5. **Verify Java JDK:** Should be installed and configured in NetBeans

---

**All configuration files have been restored. Try opening the project now!**
