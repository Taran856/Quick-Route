import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;


public class BackendDeveloperTests extends ApplicationTest{

  GraphADT<String, Double> graph = new GraphPlaceholder();
  BackendInterface backend = new Backend(graph);
  FrontendInterface frontend = new Frontend();

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
   * Tests if all the reachable locations are printed correctly after clicking findLocationsButton.
   * @throws Exception
   */
  @Test
  public void IntergrationTestReachableLocations() throws Exception{
    ApplicationTest.launch(Frontend.class);
    Frontend.setBackend(new Backend(new DijkstraGraph<String, Double>()));
    Label label = lookup("#reachableLocationsLabel").query();

    clickOn("#startingLocationField");
    write("Chadbourne Residence Hall");
    clickOn("#timeField");
    write("200");
    clickOn("#findLocationsButton");
    assertEquals(
        "Locations within 200 seconds of Chadbourne Residence Hall\n\tLaw Building" + 
            "\n\tMusic Hall\n\tBarnard Residence Hall\n\tChadbourne Residence Hall\n\tVilas " +
            "Communication Hall",
        label.getText());
  }

  /**
   * Tests if an error message is shown when clicking walkingTimesCheckbox before clicking
   * findPathButton.
   * @throws Exception
   */
  @Test
  public void IntegrationTestWrongCheckBox() throws Exception{
    ApplicationTest.launch(Frontend.class);
    Frontend.setBackend(new Backend(new DijkstraGraph<String, Double>()));
    Label label = lookup("#shortestPathLabel").query();

    clickOn("#startPathField");
    write("Memorial Union");
    clickOn("#endPathField");
    write("Chadbourne Residence Hall");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Cannot use the checkbox when a shortest path has not been found.", label.getText());
  }

  /**
   * Tests partners frontend if the walkable times are visible after clicking the 
   * walkingTimesCheckbox after the findPathButton.
   * @throws Exception
   */
  @Test
  public void partnerTestWalkingTimesCheckbox() throws Exception{
    ApplicationTest.launch(Frontend.class);
    Frontend.setBackend(new Backend(new DijkstraGraph<>()));
    Label label = lookup("#shortestPathLabel").query();

    clickOn("#startPathField");
    write("Memorial Union");
    clickOn("#endPathField");
    write("Law Building");
    clickOn("#findPathButton");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Results List: \n\tMemorial Union\n\t  (176.7 seconds)\n\t" +
        "Radio Hall\n\t  (113.0 seconds)\n\tEducation Building\n\t  (187.6 seconds)\n\t" +
        "South Hall\n\t  (112.80000000000001 seconds)\n\tLaw Building", label.getText());
  }

  /**
   * Tests partners frontend it the shortest path is printed after clicking the findPathButton.
   * @throws Exception
   */
  @Test
  public void partnerTestfindPathButton() throws Exception {
    ApplicationTest.launch(Frontend.class);
    Frontend.setBackend(new Backend(new DijkstraGraph<>()));
    Label label = lookup("#shortestPathLabel").query();

    clickOn("#startPathField");
    write("Memorial Union");
    clickOn("#endPathField");
    write("Law Building");
    clickOn("#findPathButton");
    assertEquals("Results List: \n\tMemorial Union\n\tRadio Hall\n\tEducation Building\n\t" +
        "South Hall\n\tLaw Building", label.getText());
  }

}
