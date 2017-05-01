#!/bin/bash

# Compile the Java Files
javac MessagePanel.java
javac ChatMessage.java
javac ChatClient.java
javac ChatClientGUI.java
javac ChatServer.java
javac ChatServerGUI.java

# Read the command from terminal
echo -n "What do you want to run? Type SERVER to run server and CLIENT to run client. [ENTER]: "
read runType

# Check the command to run server or client
if  [ "$runType" == "SERVER" ] || [ "$runType" == "server" ]; then
	java ChatServerGUI
	exit 1
elif [ "$runType" == "CLIENT" ] || [ "$runType" == "client" ]; then
	java ChatClientGUI
else
	echo -n "Wrong command. Please run the script again and enter SERVER or CLIENT."
	exit 1
fi
