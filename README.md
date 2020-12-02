# The Achis
## Introduction
The Achis is a game based on The Settlers 1 and 2.
It's built using javaFX (because I was basically forced to use it)
## How to play
### Setup
The Game is Multiplayer-Only. You have 2 Options:
1. You choose the official server **achirealm.com**
2. You compile/download the server program and run it on your private Server or via VPN with friends
### Lobbies
After starting the client you have the options to either create or join a Lobby
#### Creating a Lobby
Creating a Lobby makes you the Owner of this Lobby.
Being the Lobby Owner gives you the ability to kick people from the Lobby
and Start the Lobby. If you leave/close the Lobby every Member of it will be kicked.
#### Joining a Lobby
You can choose a Lobby from the Lobby list. After joining a Lobby
you are able to choose from a free Color and Start-Position.
#### Ingame
After the Lobby Owner started the game you are able to play against other
Players and attack their bases. Last Core standing wins.
## How to build/compile
This is a modular maven project, to build it you need maven:
https://maven.apache.org/
### Building Client
Switch to the "client" module/directory and execute:
```shell script
$ mvn
```
### Building Server
Switch to the "server" module/directory and execute:
```shell script
$ mvn
```
## File Structure
### Client generated Files
The client will create following files in your home directory:
1. ".the_achis/options.json"
   * Contains all setable options
2. ".the_achis/account.achiid"
   * Contains the client identifying hash
### Server generated FIles
The client will create following files in your home directory:
1. ".the_achis/clients.db"
   * Contains client related data as sqlite database
## External Libraries/Classes
* [JavaFx]
  * OpenSource Java GUI client application platform
  * Used for the whole Client-Side Graphical User Interface
* [gson]
  * Json en-/decoder
  * Used to write/read local Client Options
* [sqlite-jdbc]
  * Library to create and access [SQLite] Databases in Java
  * Used to save anonymous User-Data (like blocked users) on the Server
* [astar algorithm]
  * Implementation of the A* Algorithm from the Baeldung Tutorial
  * client/ic20b106.client.game.astar

[JavaFx]: https://openjfx.io/
[gson]: https://github.com/google/gson
[sqlite-jdbc]: https://github.com/xerial/sqlite-jdbc
[SQLite]: https://www.sqlite.org/index.html
[astar algorithm]: https://www.baeldung.com/java-a-star-pathfinding