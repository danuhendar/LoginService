@echo off
title LoginService
ver
echo Created By Danu Hendarto PT. Indomarco Prismatama
:cmd
cd
java -Xms128m -jar LoginService.jar
goto cmd