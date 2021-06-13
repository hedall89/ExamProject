package Application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static Application.LoginController.loadStage;

import java.io.IOException;
import java.util.ResourceBundle;

public class DashboardController {

    @FXML
    private Button btnProjects, btnLogOut, btnAddUser, btnExportProject, btnNewStory, btnDeleteStory, btnCreateStory,
            btnStoryBack, btnStoryAddPostit, btnStorySave, btnUsers;

    @FXML
    private TextField txtStoryName;
    @FXML
    private RadioButton rdbSingleUser, rdbMultiUser;
    @FXML
    private Label lblWindow, lblStory;

    @FXML
    private Pane paneUsers, paneProjects;

    @FXML
    private AnchorPane Ap;

    @FXML
    private TableView<savedFile> tblViewSingleUser;

    @FXML
    private TableColumn<savedFile, String> col_singleUserName;

    @FXML
    private TableColumn<savedFile, String> col_singleUserDateModified;

    ObservableList<savedFile> savedTextFiles = FXCollections.observableArrayList();

    public static savedFile selectedProject;

    @FXML
    void handleButtonAction(ActionEvent event) {

        if (event.getSource()== btnProjects){
        paneProjects.toFront();
        lblWindow.setText("Projects");
        }
        else if (event.getSource() == btnUsers){
            paneUsers.toFront();
            lblWindow.setText("Users");
        }


        if (event.getSource()== btnStoryBack){
            loadStage("/View/TimeLineToolUI.fxml");

            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

        if (event.getSource() == btnLogOut){
            loadStage("/View/Login.fxml");
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

        if (event.getSource() == btnNewStory){
            //closing TimeLineToolUI
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();

            loadStage("/View/newStory.fxml");



    }

        if (event.getSource() == btnCreateStory){
            //Opens storyUI
            loadStage("/View/StoryUI.fxml");

            //closing newStory
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();

        }
        if(event.getSource() == btnDeleteStory){

            deleteStory();
        }
        }

    private void deleteStory() {
        TextFileDAO textFileDAO = new TextFileDAOImpl();

        System.out.println(selectedProject);

        //deleting selected file
        textFileDAO.deleteTextFile();

        //updating table
        updateSingleUserTable();

    }

    public void initialize(){
        updateSingleUserTable();
       loadStory();
       selectedProjectInTable();
     }

    private void selectedProjectInTable() {
        tblViewSingleUser.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectedProject = tblViewSingleUser.getSelectionModel().getSelectedItem();
            }
        });
    }

    private void updateSingleUserTable() {
        savedTextFiles.clear();

        TextFileDAO textFileDAO = new TextFileDAOImpl();
        savedTextFiles = textFileDAO.allFilesInFolder();

        //sets textFileName and dateModified in the correct column
        col_singleUserName.setCellValueFactory(new PropertyValueFactory<savedFile, String>("name"));
        col_singleUserDateModified.setCellValueFactory(new PropertyValueFactory<savedFile, String>("dateModified"));

        //loads the textfile names and Date modified from array
        tblViewSingleUser.setItems(savedTextFiles);




    }

    public void loadStory() {
        tblViewSingleUser.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                selectedProject = tblViewSingleUser.getSelectionModel().getSelectedItem();
                System.out.println(selectedProject.getName());

                //open StoryUI Window
                loadStage("/View/StoryUI.fxml");

                //close TimeLineToolUI
                Node n = (Node) click.getSource();
                Stage previousStage = (Stage) n.getScene().getWindow();
                previousStage.close();

            }
        });

    }



    @FXML
    void storySelectorOnAction(ActionEvent event) {
        if (rdbSingleUser.isSelected()){
            selectedProject = new savedFile(txtStoryName.getText(),null);
        }
        if (rdbMultiUser.isSelected()){

        }

    }


}
