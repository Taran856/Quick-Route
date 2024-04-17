import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend implements BackendInterface {

  GraphADT<String, Double> graph;
  StringBuilder data = new StringBuilder();
  List<String> location = new ArrayList<>(); // Initializing a list which will contain all the
  // locations.

  public Backend(GraphADT<String, Double> graph) {
    this.graph = graph;
  }

  /**
   * Loads graph data from a dot file.
   *
   * @param filename the path to a dot file to read graph data from
   * @throws IOException if there was a problem reading in the specified file
   */
  public void loadGraphData(String filename) throws IOException {
    try {
      // Reading the dot file.
      FileReader reader = new FileReader(filename);
      int count = reader.read();

      while (count != -1) {
        data.append((char) count);
        count = reader.read();
      }
      reader.close();

    } catch (IOException io) {
      io.printStackTrace();
    }
    // Using regex to filter out locations and the weights.
    Pattern locationMatch = Pattern.compile("\"[\\w+\\s.'-?&]+?\"");
    Matcher locationMatcher = locationMatch.matcher(data);
    Pattern weightMatch = Pattern.compile("=[\\d+?.]+");
    Matcher weightMatcher = weightMatch.matcher(data);



    // Initializing an integer which will allow us to group the edges.
    int i = 0;
    while (locationMatcher.find()) {
      String locationString = locationMatcher.group();
      location.add(locationString.substring(1, locationString.length() - 1));
      // Inserting locations to the graph
      graph.insertNode(location.get(i));
      i++;
      // Reading a weight for every two location we read.
      if (i % 2 == 0) {
        weightMatcher.find();
        String weightString = weightMatcher.group().substring(1);
        double weight = Double.parseDouble(weightString);
        // Inserting the edge to the graph.
        graph.insertEdge(location.get(i - 2), location.get(i - 1), weight);
      }
    }

  }

  /**
   * Returns a list of all locations (nodes) available on the backend's graph.
   *
   * @return list of all location names
   */
  public List<String> getListOfAllLocations() {
    int i = 0;
    List<String> listOfAllLocations = new ArrayList<>();
    // Using a while loop to iterate through the list location.
    while (i < location.size()) {
      // Adding the unique locations from list location to the list listOfAllLocations.
      if (!listOfAllLocations.contains(location.get(i))) {
        listOfAllLocations.add(location.get(i));
      }
      i++;
    }

    return listOfAllLocations;
  }

  /**
   * Returns the sequence of locations along the shortest path from startLocation to endLocation, or
   * en empty list if no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the nodes along the shortest path from startLocation to endLocation, or an
   * empty list if no such path exists
   */
  public List<String> findShortestPath(String startLocation, String endLocation) {
    // Using the shortestPathData method from class graph to find the shortest path.
    List<String> shortestPath = graph.shortestPathData(startLocation, endLocation);
    return shortestPath;
  }

  /**
   * Returns the walking times in seconds between each two nodes on the shortest path from
   * startLocation to endLocation, or an empty list of no such path exists.
   *
   * @param startLocation the start location of the path
   * @param endLocation   the end location of the path
   * @return a list with the walking times in seconds between two nodes along the shortest path from
   * startLocation to endLocation, or an empty list if no such path exists
   */
  public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
    // Initializing shortestPath and travelTimesOnPath.
    List<String> shortestPath = findShortestPath(startLocation, endLocation);
    List<Double> travelTimesOnPath = new ArrayList<>();
    // Using a for loop to find the path cost of all the locations in the shortest path.
    for (int i = 1; i < shortestPath.size(); i++) {
      travelTimesOnPath.add(graph.shortestPathCost(shortestPath.get(i - 1), shortestPath.get(i)));
    }
    return travelTimesOnPath;
  }


  /**
   * Returns all locations that are reachable from startLocation in at most timesInSec walking
   * time.
   *
   * @param startLocation the maximum walking time for a destination to be included in the list
   */
  public List<String> getReachableLocations(String startLocation, double timesInSec) {
    // initializing reachableLocations and listOfAllLocations.
    List<String> reachableLocations = new ArrayList<>();
    List<String> listOfAllLocations = getListOfAllLocations();
    double cost;
    // Using a for loop to iterate over the listOfAllLocations to get all the locations reachable
    // in those timesInSec.
    for (int i = 0; i < listOfAllLocations.size(); i++) {
      cost = graph.shortestPathCost(startLocation, listOfAllLocations.get(i));
      if (cost <= timesInSec) {
        reachableLocations.add(listOfAllLocations.get(i));
      }
    }

    return reachableLocations;
  }
}
