package main.java;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Giorgo giorgo;

    private final Image userImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaUser.png")));
    private final Image giorgoImage = new Image(Objects.requireNonNull(
            this.getClass().getResourceAsStream("/images/DaDuke.png")));

    /**
     * Initializes the main window. Binds the vertical scroll value of the scroll
     * pane to the height property of the dialog container.
     * Displays a welcome message when the GUI is first opened.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        displayMessage("Welcome to Giorgo!");
    }

    /** Injects the Duke instance */
    public void setGiorgo(Giorgo d) {
        giorgo = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = giorgo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogueBox.getUserDialog(input, userImage),
                DialogueBox.getGiorgoDialog(response, giorgoImage)
        );
        userInput.clear();
    }

    /**
     * Displays a message in the dialog container.
     * Creates a dialog box containing the specified message and adds it to the dialog container.
     *
     * @param message The message to be displayed.
     */
    @FXML
    public void displayMessage(String message) {
        dialogContainer.getChildren().add(
                DialogueBox.getGiorgoDialog(message, giorgoImage)
        );
    }
}
