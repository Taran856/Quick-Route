import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BackendDeveloperTests {

  GraphADT<String,Double> graph = new GraphPlaceholder();
  BackendInterface backend = new Backend(graph);

//  public void setup() throws IOException {
//    backend.loadGraphData("campus.dot");
//  }
  @Test
  public void testGetListOfAllLocations() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> locations = backend.getListOfAllLocations();
    assertNotNull(locations);
    assertFalse(locations.isEmpty());
  }


  // Test method to check if findShortestPath() returns a path between two locations
  @Test
  public void testFindShortestPath() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> path = backend.findShortestPath("Union South", "Atmospheric, Oceanic and Space Sciences");
    assertNotNull(path);
    assertFalse(path.isEmpty());
    assertEquals("Union South", path.get(0));
    assertEquals("Atmospheric, Oceanic and Space Sciences", path.get(path.size() - 1));
  }

  // Test method to check if getTravelTimesOnPath() returns travel times for a path
  @Test
  public void testGetTravelTimesOnPath() throws IOException {
    backend.loadGraphData("campus.dot");
    List<Double> travelTimes = backend.getTravelTimesOnPath("Union South", "Atmospheric, Oceanic and Space Sciences");
    assertNotNull(travelTimes);
    assertFalse(travelTimes.isEmpty());
    assertEquals(2, travelTimes.size());
    assertTrue(travelTimes.stream().allMatch(time -> time > 0));
  }

  // Test method to check if getReachableLocations() returns reachable locations within a specified time
  @Test
  public void testGetReachableLocations() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> reachableLocations = backend.getReachableLocations("Union South", 3600); // 1 hour in seconds
    assertNotNull(reachableLocations);
    assertFalse(reachableLocations.isEmpty());
    assertTrue(reachableLocations.contains("Atmospheric, Oceanic and Space Sciences"));
    assertTrue(reachableLocations.contains("Memorial Union"));
  }
}
