@echo off
setlocal
set ROOT=%~dp0
set OUT=%ROOT%out\main
if exist "%ROOT%out" rmdir /s /q "%ROOT%out"
mkdir "%OUT%"
dir /s /b "%ROOT%strategy-simduck\src\main\java\*.java" > "%ROOT%out\main-sources.txt"
javac --release 17 -encoding UTF-8 -d "%OUT%" @"%ROOT%out\main-sources.txt"
xcopy /e /i /y "%ROOT%strategy-simduck\src\main\resources" "%OUT%" >nul
java -cp "%OUT%" edu.kmu.simduck.simulator.SimDuckApplication
endlocal
