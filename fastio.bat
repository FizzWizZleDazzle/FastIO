@echo off
REM FastIO Cross-Platform Utility for Windows
REM This batch file provides Windows support for FastIO utilities

setlocal

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java not found. Please install Java JDK and add it to PATH.
    exit /b 1
)

REM Compile FastIOUtil if needed
if not exist "utils\FastIOUtil.class" (
    echo Compiling FastIOUtil...
    javac utils\FastIOUtil.java
    if errorlevel 1 (
        echo Error: Failed to compile FastIOUtil.java
        exit /b 1
    )
)

REM Add utils to classpath and run FastIOUtil
cd utils
java FastIOUtil %*
cd ..