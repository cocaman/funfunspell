Set CURRENTDIR=%CD%\
Set inputpath=%1
CALL Set filename=%%inputpath:%CURRENTDIR%=%%
echo %filename%
copy ".\%filename%" "..\src\main\resources\messages\%filename%"