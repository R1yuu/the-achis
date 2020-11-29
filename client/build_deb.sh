#!/bin/bash

# build the project
echo "build mvn"
mvn clean javafx:jlink

# build installer
echo "package"
jpackage -n "The-Achis" --win-shortcut --win-dir-chooser --app-version "1.0.0" -t deb -d target --runtime-image ./target/AchiClient --module client/ic20b106.client.Game