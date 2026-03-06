@echo off
echo Stopping Ocean View Resort Server...

rem Kill any Java process running the WebServer class
powershell -Command "Get-WmiObject Win32_Process | Where-Object { $_.CommandLine -like '*com.oceanview.resort.web.WebServer*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force; Write-Host 'Stopped Process ID: ' $_.ProcessId }"

echo Done.
pause
