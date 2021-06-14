package Application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


import java.net.URL;

import static Application.LoginController.loadStage;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController {

    @FXML
    private Button btnProjects, btnLogOut, btnAddUser, btnExportStory, btnNewStory, btnDeleteStory, btnCreateStory,
            btnStoryBack, btnStoryAddPostit, btnStorySave, btnUsers, btnSelectStory;

    @FXML
    private TextField txtStoryName;
    @FXML
    private RadioButton rdbSingleUser, rdbMultiUser;
    @FXML
    private Label lblWindow, lblStory;

    @FXML
    private Pane paneUsers, paneProjects;

    @FXML
    private TableView<savedFile> tblViewSingleUser;

    @FXML
    private TableColumn<savedFile, String> col_singleUserName;

    @FXML
    private TableColumn<savedFile, String> col_singleUserDateModified;

    @FXML
    private TableView<savedFile> tblViewMultiUser;

    @FXML
    private TableColumn<savedFile, String> col_multiUserName;

    @FXML
    private TableColumn<savedFile, String> col_multiUserDateModified;

    ObservableList<savedFile> savedTextFiles = FXCollections.observableArrayList();
    ObservableList<savedFile> multiUserStories = FXCollections.observableArrayList();

    public static savedFile selectedProject;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

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

        if(event.getSource() == btnDeleteStory){
            deleteStory();
        }

        if (event.getSource() == btnSelectStory){
            //close TimeLineToolUI
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();

            //open StoryUI Window
            loadStage("/View/StoryUI.fxml");
        }
        if (event.getSource() == btnExportStory){
            //export singleUserStory
            if (StoryController.storyManager == 1){
                TextFromFileDAO textFromFileDAO = new TextFromFileDAOImpl();
                textFromFileDAO.exportSingleUserStory();
            } //export multiUserStory
            if (StoryController.storyManager == 3){
                MultiUserStory multiUserStory = new MultiUserStoryImpl();
                multiUserStory.exportMultiUserStory();
                System.out.println("multi");
            }

        }

        }

    private void deleteStory() {

        if (StoryController.storyManager == 1){
            TextFileDAO textFileDAO = new TextFileDAOImpl();
            //deleting selected singleUserStoryTextFile
            textFileDAO.deleteTextFile();

            //updating singeUserTable
            updateSingleUserTable();
        } if (StoryController.storyManager == 3) {
            //deleting selected multiUserStory
            MultiUserStory multiUserStory = new MultiUserStoryImpl();
            multiUserStory.deleteMultiUserStory();

            //updating MultiuserTable
            updateMultiUserTable();
        }






    }

    public void initialize(){
        updateMultiUserTable();
        updateSingleUserTable();
        selectedProjectInTable();
     }

    public void selectedProjectInTable() {
        tblViewSingleUser.setOnMouseClicked(click -> {
            selectedProject = tblViewSingleUser.getSelectionModel().getSelectedItem();
            System.out.println("singleUserStory Selected:" + selectedProject.getName());

            //sets counter for story
            StoryController.storyManager = 1;
        });

        tblViewMultiUser.setOnMouseClicked(click -> {
            selectedProject=tblViewMultiUser.getSelectionModel().getSelectedItem();
            System.out.println("multiUserProject Selected:" + selectedProject.getName());

            //sets counter for story
            StoryController.storyManager = 3;
        });

    }

    public void updateSingleUserTable() {
        savedTextFiles.clear();

        TextFileDAO textFileDAO = new TextFileDAOImpl();
        savedTextFiles = textFileDAO.allFilesInFolder();

        //sets textFileName and dateModified in the correct column
        col_singleUserName.setCellValueFactory(new PropertyValueFactory<savedFile, String>("name"));
        col_singleUserDateModified.setCellValueFactory(new PropertyValueFactory<savedFile, String>("dateModified"));

        //loads the textfile names and Date modified from array
        tblViewSingleUser.setItems(savedTextFiles);




    }

    public void updateMultiUserTable() {

        //Fetches storyNames and dateModified from database
        MultiUserStory multiUserStory = new MultiUserStoryImpl();

        multiUserStories = multiUserStory.usersMultiUserStories();


        //sets textFileName and dateModified in the correct column
        col_multiUserName.setCellValueFactory(new PropertyValueFactory<savedFile, String>("name"));
        col_multiUserDateModified.setCellValueFactory(new PropertyValueFactory<savedFile, String>("dateModified"));

        tblViewMultiUser.setItems(multiUserStories);



    }

}
