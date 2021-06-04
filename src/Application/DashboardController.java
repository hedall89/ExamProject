package Application;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private JFXButton btnProject;

    @FXML
    private JFXButton btnExportProject;

    @FXML
    private JFXButton btnAddUser;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private VBox Vbox;

    @FXML
    private Label lblWindow;

    @FXML
    private Pane paneProjects;

    @FXML
    private JFXButton btnNewStory;

    @FXML
    private JFXButton btnDeleteStory;

    @FXML
    void handleButtonAction(ActionEvent event) {

        if (event.getSource()== btnProject){
        paneProjects.toFront();
        lblWindow.setText("Projects");

        }

    }

}
