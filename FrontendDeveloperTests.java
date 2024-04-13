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
    ApplicationTest.launch(FrontendPlaceholder.class);
  }
  
   
  /**
   * This test finds the label and button with the given ids, and confirms
   * that the text in each has been properly initialized.
   */
  @Test
  public void testButtonAndLabelExist() {
    Label pathLabel = lookup("#pathLabel").query();
    clickOn("#findPathButton");
    assertEquals("Results List: \n\tMemorial Union\n\tSciene Hall\n\tPyschology\n\tComputer Science",pathLabel.getText());
  }

  /**
   * This test confirms that the About and Quit buttons have been created correctly.
   */
  @Test
  public void testAboutAndQuit() {
    Button aboutButton = lookup("#aboutButton").query();
    assertEquals("About",aboutButton.getText());
    Button quitButton = lookup("#quitButton").query();
    assertEquals("Quit",quitButton.getText());
  }

  /**
   * This test confirms the checkbox and checkbox label were created. Tests that clicking on the checkbox
   * makes the walkingTimesLabel display the list of travel times.
   */
  @Test
  public void testCheckbox() {
    Label checkboxLabel = lookup("#checkboxLabel").query();
    assertEquals("Show Walking Times",checkboxLabel.getText());

    Label walkingTimesLabel = lookup("#walkingTimesLabel").query();
    clickOn("#walkingTimesCheckbox");
    assertEquals("Results List (with travel times):\n\tMemorial Union\n\t-(30sec)->Science Hall\n\t-(170sec)->"+
        "Psychology\n\t-(45sec)->Computer Science\n\tTotal time: 4.08min",walkingTimesLabel.getText());
  }

  /**
   * This test confirms the that clicking on the findLocations button returns the list of locations that are reachable within a certain time
   */
  @Test
  public void testReachableLocations() {
    Label reachableLabel = lookup("#reachableLabel").query();
    clickOn("#findLocationsButton");
    assertEquals("Locations in 200sec walking distance:\n\tUnion South\n\tComputer Science",reachableLabel.getText());
  }

  /**
   * To demonstrate the code being tested, you can run the SampleApp above
   * as a JavaFX application through the following entry point.
   */
  public static void main(String[] args) {
    Application.launch(FrontendPlaceholder.class);
  }
}