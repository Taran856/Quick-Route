runApp: 
	javac BackendInterface.java
	javac Backend.java
	javac --module-path ../javafx/lib --add-modules javafx.controls App.java
	java --module-path ../javafx/lib --add-modules javafx.controls App

runTests: 
	javac BackendInterface.java
	javac Backend.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar BackendDeveloperTests.java
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c BackendDeveloperTests

runFDTests: FrontendDeveloperTests.class
	java --module-path ../javafx/lib --add-modules javafx.controls --add-opens javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar ../junit5fx.jar -cp . -c FrontendDeveloperTests

FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac --module-path ../javafx/lib --add-modules javafx.controls -cp .:../junit5fx.jar FrontendDeveloperTests.java


clean:
	rm *.class
