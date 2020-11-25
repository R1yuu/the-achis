# The Achis
## Introduction
## How to play
## How to build
This is a modular maven project, to build it you need maven:
https://maven.apache.org/
### Client
Switch to the "client" module/directory and execute:
```shell script
$ mvn
```
## File Structure
### Client generated Files
The client will create ".the_achis/options.json" in your home directory
### Server generated FIles
The client will create ".the_achis/clients.db" in your home directory
## External Libraries
* [JavaFx]
  * OpenSource Java GUI client application platform
  * Used for the whole Client-Side Graphical User Interface
* [gson]
  * Json en-/decoder
  * Used to write/read local Client Options
* [sqlite-jdbc]
  * Library to create and access [SQLite] Databases in Java
  * Used to save anonymous User-Data (like blocked users) on the Server

[JavaFx]: https://openjfx.io/
[gson]: https://github.com/google/gson
[sqlite-jdbc]: https://github.com/xerial/sqlite-jdbc
[SQLite]: https://www.sqlite.org/index.html