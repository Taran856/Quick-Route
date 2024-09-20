import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.stage.Window;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;


public class FrontendDeveloperTests extends ApplicationTest {

  /**
   * This method launches the JavaFX application that you would like to test
   * BeforeEach of your @Test methods are run.  Copy and paste this into your
   * own Test class, and change the SampleApp.class reference to instead
   * refer to your own application class that extends Application.
   */
  @BeforeEach
  public void setup() throws Exception {
    ApplicationTest.launch(Frontend.class);
  }

  /**
   * This test confirms that the About button displays instructions for how to use the program when clicked. When clicked again, 
   * the test ensures it closes out of the text.
   */
  @Test
  public void testAboutAndQuit() {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Label aboutLabel = lookup("#aboutLabel").query();
    String label = "This application loads a graph from a dot file and has two features users can use.\n" +
            "The \"Find Shortest Path\" feature allows the user to input a starting location and an ending location.\n" +
            "The program then finds the shortest path between the two locations, starting at the start location and\n" +
            "ending at the end location. When checked, the walking times checkbox displays the times between the\n" +
            "adjacent locations along a path. The \"Locations Within\" feature allows the user to input a starting\n" +
            "location and a time. The program finds all the locations that are within the specified time from the\n" +
            "starting location. Click the About button again to close this text.";

    clickOn("#aboutButton");
    assertEquals(label, aboutLabel.getText());
    clickOn("#aboutButton");
    assertEquals("", aboutLabel.getText());
  }

  /**
   * This test has the program find the shortest path starting at Union South and ending at Atmospheric, Oceanic and Space Sciences.
   * The result is checked to make sure the program outputted the actual shortest path.
   */
  @Test
  public void testfindPathButton() {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Label shortestPathLabel = lookup("#shortestPathLabel").query();
    
    clickOn("#startPathField");
    write("Union South");
    clickOn("#endPathField");
    write("Atmospheric, Oceanic and Space Sciences");
    clickOn("#findPathButton");
    assertEquals("Results List: \n\tUnion South\n\tComputer Sciences and Statistics\n\tAtmospheric, Oceanic and Space Sciences", shortestPathLabel.getText());
  }

  /**
   * This test has the program find the shortest path starting at Union South and ending at Atmospheric, Oceanic and Space Sciences.
   * Then the checkbox is clicked to include walking times. The result is checked to make sure the program outputted the correct
   *  shortest path and walking times.
   */
  @Test
  public void testWalkingTimes() {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Label shortestPathLabel = lookup("#shortestPathLabel").query();
    
    clickOn("#startPathField");
    write("Union South");
    clickOn("#endPathField");
    write("Atmospheric, Oceanic and Space Sciences");
    clickOn("#findPathButton");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Results List: \n\tUnion South\n\t  (176.0 seconds)\n\tComputer Sciences and Statistics\n\t  (80.0 seconds)\n\tAtmospheric, Oceanic and Space Sciences", shortestPathLabel.getText());
  }

  /**
   * This test tests when the checkbox is clicked before the "Find Shortest Path" button is clicked. This test verifies that the
   * program displays an error message telling the user to use the "Find Shortest Path" button before the checkbox.
   */
  @Test
  public void testCheckboxBeforeShortestPath() {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Label shortestPathLabel = lookup("#shortestPathLabel").query();

    clickOn("#startPathField");
    write("Union South");
    clickOn("#endPathField");
    write("Atmospheric, Oceanic and Space Sciences");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Cannot use the checkbox when a shortest path has not been found.", shortestPathLabel.getText());
  }

  /**
   * This test has the program find all locations that are within a specified time from the starting location. This test clicks on
   * the findLocations button and verifies that it displays the correct list of locations.
   */
  @Test
  public void testReachableLocations() {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    Label locationsLabel = lookup("#reachableLocationsLabel").query();

    clickOn("#startingLocationField");
    write("Computer Sciences and Statistics");
    clickOn("#timeField");
    write("200");
    clickOn("#findLocationsButton");
    assertEquals("Locations within 200 seconds of Computer Sciences and Statistics\n\tAtmospheric, "
        +"Oceanic and Space Sciences\n\tMemorial Union",locationsLabel.getText());
  }

  /**
   * This integration test has the program find the shortest path starting at Memorial Union and ending at Union South using our
   * data stored in campus.dot. Then the checkbox is clicked to include walking times. The result is checked to make sure the 
   * program displays the correct shortest path and walking times.
   */
  @Test
  public void integrationTestWalkingTimes() {
    Frontend.setBackend(new Backend(new DijkstraGraph<String, Double>()));
    Label shortestPathLabel = lookup("#shortestPathLabel").query();
    
    clickOn("#startPathField");
    write("Memorial Union");
    clickOn("#endPathField");
    write("Union South");
    clickOn("#findPathButton");
    clickOn("#walkingTimesCheckbox");
    assertEquals("Results List: \n\tMemorial Union\n\t  (176.7 seconds)\n\tRadio Hall\n\t  (113.0 seconds)\n\tEducation Building\n\t  (187.6 seconds)"
        +"\n\tSouth Hall\n\t  (112.80000000000001 seconds)\n\tLaw Building\n\t  (174.7 seconds)\n\tX01\n\t  (65.5 seconds)\n\tLuther Memorial Church"
        +"\n\t  (183.50000000000003 seconds)\n\tNoland Hall\n\t  (124.19999999999999 seconds)\n\tMeiklejohn House\n\t  (164.20000000000002 seconds)"
        +"\n\tComputer Sciences and Statistics\n\t  (176.00000000000003 seconds)\n\tUnion South", shortestPathLabel.getText());
  }

  /**
   * This integration test has the program find all locations that are within 200 seconds from Memorial Union using our
   * data stored in campus.dot. This test verifies that the program displays the correct list of locations.
   */
  @Test
  public void integrationTestReachableLocations() {
    Frontend.setBackend(new Backend(new DijkstraGraph<String, Double>()));
    Label locationsLabel = lookup("#reachableLocationsLabel").query();

    clickOn("#startingLocationField");
    write("Memorial Union");
    clickOn("#timeField");
    write("200");
    clickOn("#findLocationsButton");
    assertEquals("Locations within 200 seconds of Memorial Union\n\tMemorial Union\n\tScience Hall\n\tBrat Stand\n\t"
        +"Helen C White Hall\n\tRadio Hall\n\tWisconsin State Historical Society",locationsLabel.getText());
  }

  /**
   * This test tests the backend's findShortestPath() method and verifies that it returns the correct list of
   * locations.
   */
  @Test
  public void partnerTestShortestPath() {
    try {
      Backend backend = new Backend(new DijkstraGraph<String, Double>());
      backend.loadGraphData("campus.dot");
      List<String> actualPath = backend.findShortestPath("Bascom Hall", "Humphrey Hall");
      String[] expectedPath = new String[] {"Bascom Hall", "Carillon Tower", "Van Hise Hall", "Nancy Nicholas Hall",
          "Agricultural Hall", "King Hall", "Soils Building", "Carson Gulley Center", "Slichter Residence Hall",
          "Humphrey Hall"};
      if (Arrays.asList(expectedPath).equals(actualPath) == false) {
        Assertions.fail();
      }
      
    } catch (Exception e) {
      Assertions.fail();
    }
  }

  /**
   * This test tests the backend's reachableLocations() method and verifies that it returns the correct list 
   * of reachable locations.
   */
  @Test
  public void partnerTestReachableLocations() {
    try {
      Backend backend = new Backend(new DijkstraGraph<String, Double>());
      backend.loadGraphData("campus.dot");
      List<String> actualLocations = backend.getReachableLocations("Chadbourne Residence Hall", 150);
      String[] expectedLocations = new String[] {"Music Hall", "Chadbourne Residence Hall"};
      if (Arrays.asList(expectedLocations).equals(actualLocations) == false) {
        Assertions.fail();
      }
      
    } catch (Exception e) {
      Assertions.fail();
    }
  }

  /**
   * This method starts up our Frontend class.
   */
  public static void main(String[] args) {
    Frontend.setBackend(new Backend(new DijkstraGraph<String, Double>()));
    Application.launch(Frontend.class);
  }
}
