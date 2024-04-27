import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackendDeveloperTests {

  GraphADT<String, Double> graph = new GraphPlaceholder();
  BackendInterface backend = new Backend(graph);
  /**
   * This method launches the JavaFX application that you would like to test
   * BeforeEach of your @Test methods are run.  Copy and paste this into your
   * own Test class, and change the SampleApp.class reference to instead
   * refer to your own application class that extends Application.
   */
  @BeforeEach
  public void setup() throws Exception {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
  }

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

  /**
   * This test has the program find all locations that are within a specified time from the starting location. This test clicks on
   * the findLocations button and verifies that it displays the correct list of locations.
   */
  @Test
  public void IntergrationTestReachableLocations() {
    Label locationsLabel = lookup("#reachableLocationsLabel").query();

    clickOn("#startingLocationField");
    write("Chadbourne Residence Hall");
    clickOn("#timeField");
    write("500");
    clickOn("#findLocationsButton");
    assertEquals("Locations within 500 seconds of Chadbourne Residence Hall\n\tAtmospheric, Oceanic and Space Sciences\n\tMemorial Union",locationsLabel.getText());
  }

  /**
   * This test tests when the checkbox is clicked before the "Find Shortest Path" button is clicked. This test verifies that the
   * program displays an error message telling the user to use the "Find Shortest Path" button before the checkbox.
   */
  @Test
  public void IntegrationTestWrongCheckBox() {
    Label shortestPathLabel = lookup("#shortestPathLabel").query();

    clickOn("#startPathField");
    write("Memorial Union");
    clickOn("#endPathField");
    write("Chadbourne Residence Hall");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Cannot use the checkbox when a shortest path\nhas not been found.", shortestPathLabel.getText());
  }

  /**
   * This method starts up our Frontend class.
   */
  public static void main(String[] args) {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Application.launch(Frontend.class);
  }
}
