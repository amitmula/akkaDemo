@echo off

set AKKA_HOME=%~dp0..
set AKKA_CLASSPATH=%AKKA_HOME%\config;%AKKA_HOME%\lib\*;%AKKA_HOME%\deploy\*
set JAVA_OPTS=-Xms256M -Xmx1024M

java %JAVA_OPTS% -cp "%AKKA_CLASSPATH%" -Dakka.home="%AKKA_HOME%" akka.kernel.Main %*