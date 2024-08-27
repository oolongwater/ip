// Main.java
package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final Giorgo giorgo = new Giorgo();

    public Main() { }

    @Override
    public void start(Stage stage) {
        try {
            System.out.println("Attempting to load FXML...");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            System.out.println("FXML loaded successfully, setting up controller...");

            MainWindow controller = fxmlLoader.getController();
            controller.setGiorgo(giorgo);

            System.out.println("Controller setup completed, showing stage...");

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



