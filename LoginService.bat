@echo off
title LoginService
ver
echo Created By Danu Hendarto PT. Indomarco Prismatama
:cmd
cd
java -javaagent:jmx_prometheus_javaagent-0.17.2.jar=8080:jmx_exporter_config.yml -Xms128m -jar LoginService.jar
goto cmd