@echo off

REM Create build directory if not exists
if not exist build\classes mkdir build\classes

REM Create list of all Java files using relative paths to avoid space issues
REM Java package structure ensures no spaces in relative paths
powershell -Command "Get-ChildItem -Recurse -Name -Filter *.java src | ForEach-Object { 'src\' + $_ } | Out-File -Encoding ASCII sources.txt"

REM Compile all Java files together to handle dependencies correctly
echo Compiling source files...
javac -d build\classes -cp build\classes -sourcepath src @sources.txt

REM Clean up temporary file
del sources.txt

REM Run the main class
echo Starting server...
java -cp build\classes com.oceanview.resort.web.WebServer

pause
