import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Frontend extends Application implements FrontendInterface {
  private static BackendInterface back;
  private String startingLocation = "";
  private String endingLocation = "";
  private List<String> shortestPath = new LinkedList<String>();

  private Label startPathLabel;
  private TextField startPathField;
  private Label endPathLabel;
  private TextField endPathField;
  private Button findPathButton;
  private CheckBox walkingTimesCheckbox;
  private Label shortestPathLabel;

  private Label startingLocationLabel;
  private TextField startingLocationField;
  private Label timeLabel;
  private TextField timeField;
  private Button findLocationsButton;
  private Label reachableLocationsLabel;

  private Button aboutButton;
  private Label aboutLabel;
  private boolean aboutShown = false;
  private Button quitButton;

  public static void setBackend(BackendInterface back) {
    try {
      Frontend.back = back;
      back.loadGraphData("campus.dot");
    } catch (IOException e) {
      System.out.println("The graph file could not be found.");
    }
  }

  public void start(Stage stage) {
    Pane root = new Pane();
    createAllControls(root);

    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.setTitle("P2: Prototype");
    stage.show();
  }

  public void createAllControls(Pane parent) {
    createShortestPathControls(parent);
    createPathListDisplay(parent);
    createAdditionalFeatureControls(parent);
    createAboutAndQuitControls(parent);
  }

  public void createShortestPathControls(Pane parent) {
    startPathLabel = new Label("Enter Start Location:");
    startPathLabel.setId("startPathLabel");
    startPathLabel.setLayoutX(32);
    startPathLabel.setLayoutY(16);
    parent.getChildren().add(startPathLabel);

    startPathField = new TextField("");
    startPathField.setId("startPathField");
    startPathField.setLayoutX(175);
    startPathField.setLayoutY(12);
    parent.getChildren().add(startPathField);

    endPathLabel = new Label("Enter End Location:");
    endPathLabel.setId("endPathLabel");
    endPathLabel.setLayoutX(32);
    endPathLabel.setLayoutY(48);
    parent.getChildren().add(endPathLabel);

    endPathField = new TextField("");
    endPathField.setId("endPathField");
    endPathField.setLayoutX(175);
    endPathField.setLayoutY(44);
    parent.getChildren().add(endPathField);

    findPathButton = new Button("Find Shortest Path");
    findPathButton.setId("findPathButton");
    findPathButton.setLayoutX(32);
    findPathButton.setLayoutY(85);
    parent.getChildren().add(findPathButton);

    findPathButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      startingLocation = startPathField.getText();
      endingLocation = endPathField.getText();
      shortestPath = back.findShortestPath(startingLocation, endingLocation);

      if (shortestPath.isEmpty()) {
        shortestPathLabel.setText("There was an error with finding the shortest path between your two\nchosen locations.");
      } else {
        String label = "Results List: ";
      
        if (walkingTimesCheckbox.isSelected()) {
          List<Double> shortestPathTimes = back.getTravelTimesOnPath(startingLocation, endingLocation);
          for (int i = 0; i<shortestPath.size(); i++) {
            label += "\n\t" + shortestPath.get(i);

            if (i != shortestPath.size()-1) {
              label += "\n\t  (" + shortestPathTimes.get(i) + " seconds)";
            }
          }
        } else {
          for (int i = 0; i<shortestPath.size(); i++) {
            label += "\n\t" + shortestPath.get(i);
          }
        }

        shortestPathLabel.setText(label);
      }

    });
  }

  public void createPathListDisplay(Pane parent) {
    shortestPathLabel = new Label("");
    shortestPathLabel.setId("shortestPathLabel");
    shortestPathLabel.setLayoutX(32);
    shortestPathLabel.setLayoutY(117);
    parent.getChildren().add(shortestPathLabel);
  }

  public void createAdditionalFeatureControls(Pane parent) {
    this.createTravelTimesBox(parent);
    this.createFindReachableControls(parent);
  }

  public void createTravelTimesBox(Pane parent) {
    walkingTimesCheckbox = new CheckBox("Show Walking Times");
    walkingTimesCheckbox.setId("walkingTimesCheckbox");
    walkingTimesCheckbox.setLayoutX(200);
    walkingTimesCheckbox.setLayoutY(90);
    parent.getChildren().add(walkingTimesCheckbox);

    walkingTimesCheckbox.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      String label = "Results List: ";
      if (walkingTimesCheckbox.isSelected()) {
        if (shortestPath.isEmpty()) {
          shortestPathLabel.setText("Cannot use the checkbox when a shortest path has not been found.");
        } else {
          List<Double> shortestPathTimes = back.getTravelTimesOnPath(startingLocation, endingLocation);
          for (int i = 0; i<shortestPath.size(); i++) {
            label += "\n\t" + shortestPath.get(i);

            if (i != shortestPath.size()-1) {
              label += "\n\t  (" + shortestPathTimes.get(i) + " seconds)";
            }
          }
          shortestPathLabel.setText(label);
        } 
      } else {
        if (shortestPath.isEmpty()) {
          shortestPathLabel.setText("There was an error with finding the shortest path between your two\nchosen locations.");
        } else {
          for (int i = 0; i<shortestPath.size(); i++) {
            label += "\n\t" + shortestPath.get(i);
          }
          shortestPathLabel.setText(label);
        }
      }
    });
  }

  public void createFindReachableControls(Pane parent) {
    startingLocationLabel = new Label("Location Selector:");
    startingLocationLabel.setId("startingLocationLabel");
    startingLocationLabel.setLayoutX(425);
    startingLocationLabel.setLayoutY(16);
    parent.getChildren().add(startingLocationLabel);
    startingLocationField = new TextField();
    startingLocationField.setId("startingLocationField");
    startingLocationField.setLayoutX(545);
    startingLocationField.setLayoutY(12);
    parent.getChildren().add(startingLocationField);
    timeLabel = new Label("Time Selector:");
    timeLabel.setId("timeLabel");
    timeLabel.setLayoutX(425);
    timeLabel.setLayoutY(48);
    parent.getChildren().add(timeLabel);
    timeField = new TextField();
    timeField.setId("timeField");
    timeField.setLayoutX(525);
    timeField.setLayoutY(44);
    parent.getChildren().add(timeField);
    findLocationsButton = new Button("Find Locations");
    findLocationsButton.setId("findLocationsButton");
    findLocationsButton.setLayoutX(425);
    findLocationsButton.setLayoutY(80);
    parent.getChildren().add(findLocationsButton);
    reachableLocationsLabel = new Label("");
    reachableLocationsLabel.setId("reachableLocationsLabel");
    reachableLocationsLabel.setLayoutX(425);
    reachableLocationsLabel.setLayoutY(112);
    parent.getChildren().add(reachableLocationsLabel);

    findLocationsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      try {
        String startingLocation = startingLocationField.getText();
        String time = timeField.getText();
        String label = "Locations within " + time + " seconds of " + startingLocation;
        List<String> reachableLocations = back.getReachableLocations(startingLocation, Double.parseDouble(time));

        if (reachableLocations.isEmpty()) {
          label = "Your location could not be found in the graph.";
        } else {
          for (int i = 0; i<reachableLocations.size(); i++) {
            label += "\n\t" + reachableLocations.get(i);
          }
        }

        reachableLocationsLabel.setText(label);
      } catch (NumberFormatException e) {
        String label = "You must input a number into the time field";
        reachableLocationsLabel.setText(label);
      }
    });
  }

  public void createAboutAndQuitControls(Pane parent) {
    aboutButton = new Button("About");
    aboutButton.setId("aboutButton");
    aboutButton.setLayoutX(32);
    aboutButton.setLayoutY(560);
    parent.getChildren().add(aboutButton);

    quitButton = new Button("Quit");
    quitButton.setId("quitButton");
    quitButton.setLayoutX(96);
    quitButton.setLayoutY(560);
    parent.getChildren().add(quitButton);

    aboutLabel = new Label("");
    aboutLabel.setId("aboutLabel");
    aboutLabel.setLayoutX(32);
    aboutLabel.setLayoutY(430);
    parent.getChildren().add(aboutLabel);

    aboutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      if (aboutShown) {
        aboutLabel.setText("");
        aboutShown = false;
      } else {
        String label = "This application loads a graph from a dot file and has two features users can use.\n" +
            "The \"Find Shortest Path\" feature allows the user to input a starting location and an ending location.\n" +
	    "The program then finds the shortest path between the two locations, starting at the start location and\n" +
	    "ending at the end location. When checked, the walking times checkbox displays the times between the\n" +
	    "adjacent locations along a path. The \"Locations Within\" feature allows the user to input a starting\n" +
	    "location and a time. The program finds all the locations that are within the specified time from the\n" +
	    "starting location. Click the About button again to close this text.";
        aboutLabel.setText(label);
        aboutShown = true;
      }
    });

    quitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
      Platform.exit();
    });
  }

}
