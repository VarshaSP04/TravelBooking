@echo off
echo ==============================================
echo  Starting TravelBooking Platform Services
echo ==============================================

set "PROJECT_DIR=%~dp0"
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-17.0.19.10-hotspot"
set "CATALINA_HOME=%PROJECT_DIR%tools\tomcat10"
set "PATH=%JAVA_HOME%\bin;%PATH%"

:: 1. Start MongoDB if not already running
netstat -ano | findstr :27017 | findstr LISTENING >nul
if %ERRORLEVEL% equ 0 (
    echo [1/2] MongoDB is already running on port 27017.
) else (
    echo [1/2] Starting MongoDB on port 27017...
    start "MongoDB" /min "%PROJECT_DIR%tools\mongodb\bin\mongod.exe" --dbpath "%PROJECT_DIR%tools\mongodb-data"
    timeout /t 2 /nobreak >nul
)

:: 2. Start Tomcat if not already running
netstat -ano | findstr :8080 | findstr LISTENING >nul
if %ERRORLEVEL% equ 0 (
    echo [2/2] Tomcat is already running on port 8080.
) else (
    echo [2/2] Starting Apache Tomcat 10 on port 8080...
    start "Tomcat 10" /min "%CATALINA_HOME%\bin\catalina.bat" run
    timeout /t 3 /nobreak >nul
)

echo.
echo ==============================================
echo  Services are active!
echo  Web Application: http://localhost:8080/TravelBooking
echo  Admin Portal:    http://localhost:8080/TravelBooking/admin/login
echo ==============================================
timeout /t 2 /nobreak >nul
start http://localhost:8080/TravelBooking
