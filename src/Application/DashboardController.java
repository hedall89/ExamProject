package Application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private Button btnProjects, btnLogOut, btnAddUser, btnExportProject, btnNewStory, btnDeleteStory, btnCreateStory;

    @FXML
    private TextField txtStoryName;
    @FXML
    private RadioButton rdbSingleUser, rdbMultiUser;
    @FXML
    private Label lblWindow;

    @FXML
    private Pane paneProjects;

    @FXML
    void handleButtonAction(ActionEvent event) {

        if (event.getSource()== btnProjects){
        paneProjects.toFront();
        lblWindow.setText("Projects");

        }

        if (event.getSource() == btnLogOut){
            loadStage("/View/Login.fxml");
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

        if (event.getSource() == btnNewStory){
            loadStage("/View/newStory.fxml");

        }

    }


    // Method for Switching between Stages
    public static void loadStage(String fxml) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource(fxml));
            Parent my_root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(my_root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
