# Java TCP Socket Chat Application

## Overview
This application implements a basic client-server chat system using Java TCP sockets. It allows for text-based communication between a server and multiple clients over a network.

## Features
- Single-threaded, multithreaded, and chatroom server implementation
- Text-based chat interface
- Custom IP address and port configuration


## Requirements
- Java JDK 8 or higher
- Terminal/Command Prompt to run the application

## Installation
1. Clone this repository or download the source files:
   ```
   git clone https://github.com/sebi-stefan/TCP-IP-Server.git
   ```
2. Navigate to the desired application:
   ```
   cd src/main/java/{singlethread, multithread, chatroom}
   ```
3. Compile the server and client:
   ```
   javac Server.java
   javac Client.java
   ```

## Usage

### Starting the Server
Run the server with default settings (localhost:8080):
```
java Server
```

Or specify a custom IP address and port:
```
java Server <ip-address> <port>
```

If no arguments are provided, the application will prompt you to enter the IP address and port.

### Connecting a Client
Run the client with default settings:
```
java Client
```

Or specify the server's IP address and port:
```
java Client <server-ip> <server-port>
```

If no arguments are provided, the application will prompt you to enter the server's IP address and port.

### Chat Commands
- Type any message and press Enter to send it to the other party
- Type "bye" to terminate the connection

## Architecture
This application follows a basic client-server architecture:
- `Server.java`: Handles incoming client connections and message processing
- `Client.java`: Connects to the server and provides a user interface for sending/receiving messages

Both components use Java's socket API for network communication and BufferedReader/BufferedWriter for message handling.

## Limitations
- The current implementation is single-threaded and can only handle one client connection at a time
- No message encryption or security features
- Basic error handling
