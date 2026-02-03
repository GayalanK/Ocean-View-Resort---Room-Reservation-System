# Build Troubleshooting Guide

## Build Error: BUILD FAILED

### Solution 1: Use NetBeans IDE Build

Instead of command line, use NetBeans:
1. Open project in NetBeans
2. Right-click project → **Clean and Build**
3. Check Output window for specific errors

### Solution 2: Manual Compilation

If Ant doesn't work, compile manually:

```bash
cd "D:\Gayalan K Private\ocean-view-resort"
mkdir build\classes 2>nul
javac -d build/classes -sourcepath src src/com/oceanview/resort/**/*.java
```

### Solution 3: Check Java Version

Ensure Java 17+ is installed:
```bash
java -version
javac -version
```

### Solution 4: Verify Build Files

Ensure these files exist:
- ✅ build.xml (project root)
- ✅ nbproject/project.xml
- ✅ nbproject/project.properties
- ✅ nbproject/build-impl.xml
- ✅ nbproject/private/private.properties

### Solution 5: Clean and Rebuild

In NetBeans:
1. Right-click project → **Clean**
2. Wait for clean to complete
3. Right-click project → **Build**
4. Check for compilation errors

### Solution 6: Check Source Files

Verify all Java files are in correct packages:
- `src/com/oceanview/resort/model/*.java`
- `src/com/oceanview/resort/dao/*.java`
- `src/com/oceanview/resort/service/*.java`
- `src/com/oceanview/resort/web/*.java`
- etc.

### Common Issues

**Issue:** "Cannot find symbol"
- **Solution:** Check imports, ensure all classes are compiled

**Issue:** "Package does not exist"
- **Solution:** Verify package structure matches directory structure

**Issue:** Build fails silently
- **Solution:** Check Output window in NetBeans for detailed error messages

### Quick Test Build

Create a simple test:
```bash
cd "D:\Gayalan K Private\ocean-view-resort"
javac -d build/classes src/com/oceanview/resort/model/User.java
```

If this works, the issue is with the build file configuration.
