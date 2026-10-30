@echo off
echo ==============================================
echo  Stopping TravelBooking Platform Services
echo ==============================================

set "PROJECT_DIR=%~dp0"
set "CATALINA_HOME=%PROJECT_DIR%tools\tomcat10"

echo Stopping Apache Tomcat...
call "%CATALINA_HOME%\bin\shutdown.bat" >nul 2>&1

echo Stopping MongoDB...
taskkill /F /IM mongod.exe >nul 2>&1

echo.
echo All services stopped.
pause
