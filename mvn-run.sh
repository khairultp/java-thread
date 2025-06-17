#!/bin/bash

# Default thread count is 100000 if not provided
THREAD_COUNT=${1:-100000}

# Compile the code
./mvnw compile

# Run the Main class directly with the thread count as an argument
java -cp target/classes com.khairul.thread.Main $THREAD_COUNT
