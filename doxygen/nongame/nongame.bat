@echo off
SET MSCPATH=C:\Program Files (x86)\_Mscgen
echo %MSCPATH%
SET DOTPATH=C:\Program Files (x86)\_Graphviz\bin
echo %DOTPATH%

echo 0. set the code generator color to blue, so we can tell cmd windows apart
color 3

echo 1. set the current working directory to the dir that the bat file is in.
for /f %%i in ('cd') do set CWD=%%i




echo Outputting doxygen to doxygen_processing_log.txt ...
echo ...
echo Please wait whilst any Warnings/Errors are dumped to this screen.
"C:\Program Files (x86)\_Doxygen1.8.3.1\bin\doxygen.exe" g:\a2g_core\nongame.doxyfile > nongame_log.txt

  
pause
cls
call nongame.bat