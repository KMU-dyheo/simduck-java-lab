@echo off
setlocal
set ROOT=%~dp0
set MAIN=%ROOT%out\main
set TEST=%ROOT%out\test
if exist "%ROOT%out" rmdir /s /q "%ROOT%out"
mkdir "%MAIN%"
mkdir "%TEST%"
dir /s /b "%ROOT%simduck\src\main\java\*.java" > "%ROOT%out\main-sources.txt"
dir /s /b "%ROOT%simduck\src\test\java\*.java" > "%ROOT%out\test-sources.txt"
javac --release 17 -encoding UTF-8 -d "%MAIN%" @"%ROOT%out\main-sources.txt"
xcopy /e /i /y "%ROOT%simduck\src\main\resources" "%MAIN%" >nul
javac --release 17 -encoding UTF-8 -cp "%MAIN%" -d "%TEST%" @"%ROOT%out\test-sources.txt"
java -Djava.awt.headless=true -cp "%MAIN%;%TEST%" edu.kmu.simduck.CoreDesignVerification
endlocal
