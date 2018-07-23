@echo off
SET MSCPATH=C:\SapDev\ViewerWin\Releases\9.0\Tools\mscgen\bin\
echo %MSCPATH%
SET DOTPATH=C:\SapDev\ViewerWin\Releases\9.0\Tools\Doxygen1.8.3.1\bin\
echo %DOTPATH%

echo 0. set the code generator color to blue, so we can tell cmd windows apart
color 3

echo 1. set the current working directory to the dir that the bat file is in.
for /f %%i in ('cd') do set CWD=%%i




echo Outputting doxygen to doxygen_processing_log.txt ...
echo ...
echo Please wait whilst any Warnings/Errors are dumped to this screen.
"C:\SapDev\ViewerWin\Releases\9.0\Tools\Doxygen1.8.3.1\bin\doxygen.exe" c:\a2g_core\core.doxyfile > doxygen_processing_log.txt

  
pause
cls
call _BuildDocsForCore.bat