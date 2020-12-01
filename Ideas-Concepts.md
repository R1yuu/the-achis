# The Achis Project Ideas/Concepts
Development file in which I note and think through Concepts of ideas.
It uses mermaid diagrams which are not supported by Github.
## Concepts

### Client-Server Connection Handshake

```mermaid
sequenceDiagram

participant Client
participant Server

activate Client
note left of Client: Generate Hash
deactivate Client
Note over Client,Server: Open Socket
Client->>Server: Player-Hash
activate Server
Note right of Server: Check if <br/> Hash taken
alt Hash Ok
Note left of Client: Client<br/>Stops
    Server-->>Client: TAKEN
else Hash Taken
    Server-->>Client: OK
end
Note right of Server: Open RMI
Server->>Client: OPEN
deactivate Server
Client-->Server: RMI Communication



```





### User Unique Ids

```mermaid
graph TD
	playerHash[Unique Player Hash]
	macUUID[Mac UUID]
	macUUIDNote>UUID from first <br/> readable Mac Address]
	rdmLong[Random Long]
	playerHashFile[("account.achiid<br/>(Binary File)")]
	genHash([Generate Hash])
	
	macUUID -.-> playerHash
    macUUID --- macUUIDNote 
	rdmLong -.-> playerHash
	playerHash -.- playerHashFile
	playerHashFile -->|Read| ifExists{contains valid Hash}
	ifExists -->|No| genHash
	genHash -->|Save| playerHashFile
	ifExists -->|Yes| server([Checkout with Server])
	

```

### ~~Handle same Hash; Server-side~~

```mermaid
graph TD
	client[[Client generated Hash]]
	
	client -->|Send| server[(Server)]
	server --> action1["Adds '_' + Random UUID"]
```
