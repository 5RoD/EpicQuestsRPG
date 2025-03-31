@echo off

REM Stop server if running by killing any java.exe processes
echo Stopping any running Java processes...
taskkill /F /IM "java.exe" >nul 2>nul

REM Set paths for the plugin and server
set PLUGIN_NAME=epicquest-rpg-0.1-ALPHA.jar
set PLUGIN_PATH=target\%PLUGIN_NAME%
set SERVER_PATH=C:\Users\Hex\Desktop\start

REM Check if the plugins folder exists, and if not, create it
if not exist "%SERVER_PATH%\plugins" mkdir "%SERVER_PATH%\plugins"

REM Copy the plugin to the server's plugins folder
echo Copying plugin to server...
copy "%PLUGIN_PATH%" "%SERVER_PATH%\plugins\" /Y

REM Change to the server directory
cd "%SERVER_PATH%"

REM Start the Minecraft server (ensure the server JAR file name is correct)
echo Starting Minecraft server...
start java -Xms2G -Xmx4G -jar d.jar nogui

REM Wait for the server to stop and kill any running java.exe processes
echo Waiting for the server to stop...
timeout /t 5 /nobreak

echo Stopping any running Java processes...
taskkill /F /IM "java.exe" >nul 2>nul

echo Minecraft server stopped.
