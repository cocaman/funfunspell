Set CURRENTDIR=%CD%\
Set inputpath=%1
CALL Set filename=%%inputpath:%CURRENTDIR%=%%
echo %filename%
"C:\Program Files\Java\jdk1.6.0_21\bin\native2ascii.exe" -encoding utf-8 ".\%filename%" "..\src\main\resources\messages\%filename%"