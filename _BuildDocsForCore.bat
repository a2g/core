@echo off
SET MSCPATH=e:/Program Files/mscgen/bin/
echo %MSCPATH%
SET DOTPATH=e:\Program Files\Graphviz\bin\
echo %DOTPATH%

echo 0. set the code generator color to blue, so we can tell cmd windows apart
color 3

echo 1. set the current working directory to the dir that the bat file is in.
for /f %%i in ('cd') do set CWD=%%i




echo Outputting doxygen to doxygen_processing_log.txt ...
echo ...
echo Please wait whilst any Warnings/Errors are dumped to this screen.
"e:/Program Files/Doxygen/bin/doxygen.exe" E:\Conan\appSwingVillage\src\com\github\a2g\core\core.doxyfile > doxygen_processing_log.txt

  
pause
cls
call _BuildDocsForCore.bat