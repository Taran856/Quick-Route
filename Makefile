runApp: 
	javac BackendInterface.java
	javac Backend.java
	javac -cp .:../junit5.jar DijkstraGraph.java
	javac App.java
	java App

runTests: runFDTests
	javac BackendInterface.java
	javac Backend.java
	javac -cp .:../junit5.jar Dijkstra.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runFDTests: FrontendDeveloperTests.class
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java


clean:
	rm *.class
