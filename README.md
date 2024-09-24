# Graph-Based Pathfinding System

## Overview

This project implements a **graph-based pathfinding system** utilizing Dijkstra's shortest path algorithm. The system is designed to find the shortest path between various nodes (such as campus buildings) with positive edge weights. The project is divided into a modular structure consisting of interfaces, abstract data types (ADTs), backend, and frontend components, along with test cases to ensure system reliability.

### Key Features
- Inserting and removing nodes and edges.
- Finding the shortest path between nodes.
- Calculating the cost of the shortest path.
- Managing key-value mappings for graph nodes.

The project supports a user interface that allows interaction with the graph system, providing an intuitive way to query paths and distances between nodes. It also includes placeholder implementations and thorough unit testing.

## Project Structure

### `App.java`
This is the main entry point for the application. It initializes and launches the user interface (UI) using JavaFX, facilitating interaction between the user and the backend system. It displays the shortest path and related graph information to the user.

### `BackendInterface.java`
Defines the interface for the backend, specifying the core functionalities such as loading data, adding nodes/edges, and computing the shortest paths between nodes.

### `Backend.java`
Implements the `BackendInterface`, handling the graph operations and processing user requests. It manages the logic for inserting/removing nodes and edges, and calculating the shortest path using Dijkstra’s algorithm.

### `DijkstraGraph.java`
Contains the core logic for Dijkstra's shortest path algorithm. It uses a directed graph data structure with positive edge weights to compute the shortest paths and path costs between two nodes.

### `GraphADT.java`
An abstract data type (ADT) that defines the structure of a directed graph with positive edge weights. It provides methods for inserting/removing nodes and edges, checking the existence of nodes/edges, and calculating the shortest paths.

### `GraphPlaceholder.java`
A placeholder implementation of the `GraphADT` interface. It provides basic functionality with hardcoded data, used primarily for testing and development purposes.

### `MapADT.java`
An abstract data type (ADT) for managing key-value pairs in a collection. It ensures that each key maps to a single value and provides methods for adding, retrieving, and removing key-value pairs.

### `PlaceholderMap.java`
An implementation of the `MapADT` using a `HashMap`. It provides methods for adding, checking, retrieving, and removing key-value pairs, but lacks support for capacity management.

### `FrontendInterface.java`
An interface for the frontend component, defining the methods required to handle user interactions and display graph-related data to the user.

### `Frontend.java`
Implements the `FrontendInterface`, handling the user interface logic. It receives user input, communicates with the backend to retrieve data, and displays the results, such as the shortest path between nodes.

### `Makefile`
A script used to compile and run the application, as well as run tests. It automates the build process and test execution using `javac` and `java` commands.

### `BackendDeveloperTests.java`
Contains unit tests for the backend components. These tests ensure that the backend correctly handles graph operations and returns the correct paths and costs when running Dijkstra’s algorithm.

### `FrontendDeveloperTests.java`
Contains unit tests for the frontend components. These tests validate the user interface's ability to handle user input and communicate correctly with the backend.

### `MapADT.java`
An abstract data type representing a key-value mapping collection. It prevents duplicate keys and allows querying, adding, and removing of key-value pairs.

### `PlaceholderMap.java`
A simple implementation of `MapADT` using Java’s `HashMap`. It’s a minimal version used for testing and development.

---

## How to Build and Run the Application

### Requirements
- Java JDK 8 or higher
- JavaFX
- JUnit 5 for testing

### Compilation and Execution

1. **Compile the application:**
   ```bash
   make runApp
