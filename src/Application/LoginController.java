package Application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Pane pnlCreateAccount, pnlLogin;

    @FXML
    private TextField txtCAUsername;

    @FXML
    private TextField txtCAPassword, txtLoginPassword, txtLoginUserName;

    @FXML
    private Button btnCreateAccount, btnCancelCreateAccount, btnLoginCreateAccount, btnLogin, btnLoginCancel;


    @FXML
    void handleButtonAction(ActionEvent event) {

        //if this button is pushed, a window for creating an account will appear (CreateAccount.fxml)
        if (event.getSource() == btnLoginCreateAccount) {
            loadStage("/View/CreateAccount.fxml");
        }
        if (event.getSource()== btnLoginCancel ){
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

        //If this button is pushed TimelineToolUI will open, and all actions will continue in DashboardController
        if (event.getSource() == btnLogin) {
            loadStage("/View/TimeLineToolUI.fxml");

            // Close Login Window
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
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
