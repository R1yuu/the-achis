@echo off

REM build the project
echo "build mvn"
call mvn clean javafx:jlink

REM build installer
echo "package"
jpackage -n "The-Achis" --win-shortcut --win-dir-chooser --app-version "1.0.0" -t msi -d target --runtime-image ./target/AchiClient --module client/ic20b106.client.Game