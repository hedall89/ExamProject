package Application;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private Pane pnlCreateAccount, pnlLogin;

    @FXML
    private Label lblError;

    @FXML
    private TextField txtCAPassword, txtLoginPassword, txtLoginUserName, txtCAUsername;

    @FXML
    private Button btnCreateAccount, btnCancelCreateAccount, btnLoginCreateAccount, btnLogin, btnLoginCancel;

    public static String username;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

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
        if (event.getSource() == btnCancelCreateAccount){
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

        //If this button is pushed TimelineToolUI will open, and all actions will continue in DashboardController
        if (event.getSource() == btnLogin) {

            if (logIn().equals("Success")) {
                Node n = (Node) event.getSource();
                Stage previousStage = (Stage) n.getScene().getWindow();
                previousStage.close();
                loadStage("/View/TimeLineToolUI.fxml");
            }
        }

        if (event.getSource() == btnCreateAccount){
            if (signUp().equals("Success")){
                Node n = (Node) event.getSource();
                Stage previousStage = (Stage) n.getScene().getWindow();
                previousStage.close();
            }
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


    public LoginController(){
        con = DatabaseConnector.createConnection();
    }


    //Method for logging in to the app
    private String logIn(){

        String username = txtLoginUserName.getText().toLowerCase().toString();
        String password = txtLoginPassword.getText().toString();
        //query
        String sql = "Select * from tblUser where fldUsername = ? and fldPassword = ?";

        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()){
                lblError.setTextFill(Color.TOMATO);
                lblError.setText("Enter correct Username/Password");
                return "Error";

            }
            else {
                lblError.setTextFill(Color.GREEN);
                lblError.setText("Login Successful");
                return "Success";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "exception";
        }
    }

    //method for creating an account
    private String signUp(){

        String username = txtCAUsername.getText().toLowerCase();
        String password = txtCAPassword.getText();

        //query
        String insertQuery = "Insert into tblUser values(null, '" + username + "', '" + password + "')";
        String dublicate = "Select * from tblUser where fldUsername = '"+username+"'";

        try {
            Statement stat = con.createStatement();
            resultSet = stat.executeQuery(dublicate);
            if (resultSet.next() ){
                System.out.println("Username already registered!");
                return "Fail";
            }
            else {
                stat.executeUpdate(insertQuery);
                System.out.println("account registered");
                return "Success";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "exception";
        }

    }
}
