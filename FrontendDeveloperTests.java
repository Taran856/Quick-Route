import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
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


import javafx.application.Application;

public class FrontendDeveloperTests extends ApplicationTest {

  /**
   * This method launches the JavaFX application that you would like to test
   * BeforeEach of your @Test methods are run.  Copy and paste this into your
   * own Test class, and change the SampleApp.class reference to instead
   * refer to your own application class that extends Application.
   */
  @BeforeEach
  public void setup() throws Exception {
    Frontend.setBackend(new BackendPlaceholder(new GraphPlaceholder()));
    ApplicationTest.launch(Frontend.class);
  }

  /**
   * This test confirms that the About button displays instructions for how to use the program when clicked. When clicked again, 
   * the test ensures it closes out of the text.
   */
  @Test
  public void testAboutAndQuit() {
    Label aboutLabel = lookup("#aboutLabel").query();
    String label = "This application loads a graph from a dot file and has two features users can use. \nThe " +
        "\"Find Shortest Path\" feature allows the user to input a starting location and an ending location. The program then\n" +
        "finds the shortest path between the two locations, starting at the starting location and ending at the ending location.\n" +
        "The walking times checkbox allows the user to adds times between the adjacent locations along a path. The \"Locations\n" +
        "Within\" feature allows the user to input a starting location and a time. The program finds all the locations that are\n" +
        "within the specified time from the starting location. Click the About button again to close this text.";

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
    Label locationsLabel = lookup("#reachableLocationsLabel").query();

    clickOn("#startingLocationField");
    write("Computer Sciences and Statistics");
    clickOn("#timeField");
    write("200");
    clickOn("#findLocationsButton");
    assertEquals("Locations within 200 seconds of Computer Sciences and Statistics\n\tAtmospheric, Oceanic and Space Sciences\n\tMemorial Union",locationsLabel.getText());
  }

  /**
   * This method starts up our Frontend class.
   */
  public static void main(String[] args) {
    Application.launch(Frontend.class);
  }
}