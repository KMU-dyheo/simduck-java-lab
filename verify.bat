@echo off
setlocal

set ROOT=%~dp0
set MAIN=%ROOT%out\main
set TEST=%ROOT%out\test
set STAGE=%~1

if not "%STAGE%"=="" goto stage_ready
if not "%LAB_STAGE%"=="" (
    set STAGE=%LAB_STAGE%
    goto stage_ready
)

set BRANCH=%GITHUB_HEAD_REF%
if "%BRANCH%"=="" set BRANCH=%GITHUB_REF_NAME%
if "%BRANCH%"=="" (
    for /f "delims=" %%i in ('git -C "%ROOT%" branch --show-current 2^>nul') do set BRANCH=%%i
)

if "%BRANCH%"=="main" set STAGE=06
echo %BRANCH% | findstr /c:"strategy-01-start" >nul && set STAGE=00
echo %BRANCH% | findstr /c:"strategy-01-01-fly" >nul && set STAGE=01
echo %BRANCH% | findstr /c:"strategy-01-02-rubber" >nul && set STAGE=02
echo %BRANCH% | findstr /c:"strategy-01-03-override" >nul && set STAGE=03
echo %BRANCH% | findstr /c:"strategy-01-04-interface" >nul && set STAGE=04
echo %BRANCH% | findstr /c:"strategy-01-05-strategy" >nul && set STAGE=05

if "%STAGE%"=="" (
    echo 검증 단계를 자동으로 찾을 수 없습니다: %BRANCH%
    echo 예: verify.bat 03
    exit /b 2
)

:stage_ready
if exist "%ROOT%out" rmdir /s /q "%ROOT%out"
mkdir "%MAIN%"
mkdir "%TEST%"

dir /s /b "%ROOT%simduck\src\main\java\*.java" > "%ROOT%out\main-sources.txt"
dir /s /b "%ROOT%simduck\src\test\java\*.java" > "%ROOT%out\test-sources.txt"

javac --release 17 -encoding UTF-8 -d "%MAIN%" @"%ROOT%out\main-sources.txt"
if errorlevel 1 exit /b 1

xcopy /e /i /y "%ROOT%simduck\src\main\resources" "%MAIN%" >nul

javac --release 17 -encoding UTF-8 -cp "%MAIN%" -d "%TEST%" @"%ROOT%out\test-sources.txt"
if errorlevel 1 exit /b 1

java -Djava.awt.headless=true -cp "%MAIN%;%TEST%" edu.kmu.simduck.CoreDesignVerification
if errorlevel 1 exit /b 1

java -Djava.awt.headless=true -cp "%MAIN%;%TEST%" edu.kmu.simduck.LabMissionVerification "%STAGE%"
if errorlevel 1 exit /b 1

endlocal
