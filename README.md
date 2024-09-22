# Directed Graph and Map Data Structures

## Overview

This project implements a directed graph data structure with positive edge weights, alongside a map abstract data structure. The project allows for efficient insertion, removal, and querying of nodes and edges, supporting Dijkstra's algorithm for finding the shortest path between nodes. Additionally, a placeholder implementation of the map data structure is provided, utilizing Java's `HashMap`. This project serves as an educational tool for understanding graph algorithms and abstract data types.

## File Descriptions

### 1. `GraphADT.java`
This file defines the **Graph Abstract Data Type (ADT)** interface for a directed graph that only allows positive edge weights. It includes methods for:
- Inserting and removing nodes.
- Checking the existence of nodes and edges.
- Inserting and removing directed edges.
- Retrieving data associated with specific edges.
- Finding the shortest path using Dijkstra's algorithm, both in terms of the path itself and the total cost.

### 2. `GraphPlaceholder.java`
This is a placeholder implementation of the `GraphADT` interface. It provides a simple graph structure with a predefined set of nodes representing locations. The methods are implemented to return fixed values for existing nodes and edges, simulating graph functionality without allowing dynamic modifications. It includes:
- A fixed path of nodes.
- Methods to check for the presence of nodes and edges.
- Basic logic to return edge weights and simulate shortest path calculations.

### 3. `MapADT.java`
This file defines the **Map Abstract Data Type (ADT)** interface, which represents a collection that maps unique keys to values. Key features include:
- Adding key-value pairs, with checks to prevent duplicates.
- Checking for the existence of keys.
- Retrieving and removing values based on keys.
- Methods to clear the map and get its size and capacity.

### 4. `PlaceholderMap.java`
This file implements the `MapADT` interface using Java's `HashMap`. It provides a basic mapping structure with the following functionalities:
- Adding and removing key-value pairs while enforcing unique and non-null keys.
- Checking for key existence and retrieving associated values.
- Clear method to remove all entries in the map.
- Size method to get the number of key-value pairs stored, but it does not support capacity retrieval.

### 5. `DijkstraGraph.java`
This file implements Dijkstra's algorithm specifically for the directed graph defined in `GraphADT.java`. It contains the logic to compute the shortest path between nodes, leveraging the methods provided by the graph ADT.

### 6. `Backend.java`
This file serves as the backend logic for handling graph operations, interacting with the data structures, and performing necessary computations.

### 7. `BackendInterface.java`
This interface defines the methods available in the backend for manipulating graph data, ensuring a clear contract for implementation.

### 8. `App.java`
This file contains the main application logic, initializing the graph and map structures, and facilitating user interaction.

### 9. `Makefile`
A Makefile for building and running the Java application. It includes targets for compiling the necessary files and running tests with the JUnit framework.

### 10. `FrontendDeveloperTests.java`
This file contains unit tests for the frontend operations of the application, ensuring that the implemented functionalities work as intended.

### 11. `BackendDeveloperTests.java`
This file contains unit tests for the backend operations, validating the integrity and performance of graph-related methods.

### 12. `DijkstraGraphTests.java`
This file includes tests specifically for verifying the correctness of the Dijkstra's algorithm implementation.

## Usage

To build and run the project, use the provided Makefile:
```bash
make runApp
