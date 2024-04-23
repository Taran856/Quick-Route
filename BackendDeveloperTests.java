import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackendDeveloperTests {

  GraphADT<String, Double> graph = new GraphPlaceholder();
  BackendInterface backend = new Backend(graph);

  /**
   * Tests the method getListOfAllLocations().
   * @throws IOException if there was a problem reading in the specified file
   */
  @Test
  public void testGetListOfAllLocations() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> locations = backend.getListOfAllLocations();
    assertNotNull(locations);
    assertFalse(locations.isEmpty());
  }


  /**
   * Tests the method findShortestPath().
   * @throws IOException if there was a problem reading in the specified file
   */
  @Test
  public void testFindShortestPath() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> path =
        backend.findShortestPath("Union South", "Atmospheric, Oceanic and Space Sciences");
    assertNotNull(path);
    assertFalse(path.isEmpty());
    assertEquals("Union South", path.get(0));
    assertEquals("Atmospheric, Oceanic and Space Sciences", path.get(path.size() - 1));
  }

  /**
   * Tests the method getTravelTimesOnPath().
   * @throws IOException if there was a problem reading in the specified file
   */
  @Test
  public void testGetTravelTimesOnPath() throws IOException {
    backend.loadGraphData("campus.dot");
    List<Double> travelTimes =
        backend.getTravelTimesOnPath("Union South", "Atmospheric, Oceanic and Space Sciences");
    assertNotNull(travelTimes);
    assertFalse(travelTimes.isEmpty());
    assertEquals(2, travelTimes.size());
    assertTrue(travelTimes.stream().allMatch(time -> time > 0));
  }

  /**
   * Tests the method getReachableLocations().
   * @throws IOException if there was a problem reading in the specified file
   */
  @Test
  public void testGetReachableLocations() throws IOException {
    backend.loadGraphData("campus.dot");
    List<String> reachableLocations =
        backend.getReachableLocations("Union South", 400);
    assertNotNull(reachableLocations);
    assertFalse(reachableLocations.isEmpty());
    assertTrue(reachableLocations.contains("Atmospheric, Oceanic and Space Sciences"));
    assertTrue(reachableLocations.contains("Memorial Union"));
  }

  /**
   * Tests loadGraphData with a wrong input.
   * @throws IOException if there was a problem reading in the specified file
   */
  @Test
  public void TestWrongInput() throws IOException {
    boolean thrown = false;
    try {
      backend.loadGraphData("camp.dot");
    }
    catch (IOException ioException){
      thrown = true;
    }
    assertTrue(thrown);

  }
}
