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
import javafx.stage.Stage;

import java.net.URL;

import static Application.LoginController.loadStage;

import java.time.LocalDate;
import java.util.ResourceBundle;

public class DashboardController {

    @FXML
    private Button btnProjects, btnLogOut, btnAddUser, btnExportProject, btnNewStory, btnDeleteStory, btnCreateStory,
            btnStoryBack, btnStoryAddPostit, btnStorySave;

    @FXML
    private TextField txtStoryName;
    @FXML
    private RadioButton rdbSingleUser, rdbMultiUser;
    @FXML
    private Label lblWindow, lblStory;

    @FXML
    private Pane paneProjects;

    @FXML
    private AnchorPane Ap;

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
        //deleting selected singleUserStoryTextFile
        textFileDAO.deleteTextFile();

        //updating singeUserTable
        updateSingleUserTable();


        //deleting selected multiUserStory
        MultiUserStory multiUserStory = new MultiUserStoryImpl();
        multiUserStory.deleteMultiUserStory();

        //updating MultiuserTable
        updateMultiUserTable();
    }

    public void initialize(){
        //updateMultiUserTable();
        //updateSingleUserTable();
        //loadStory();
        //selectedProjectInTable();
     }

    public void selectedProjectInTable() {
        tblViewSingleUser.setOnMouseClicked(click -> {
            selectedProject = tblViewSingleUser.getSelectionModel().getSelectedItem();
            System.out.println("singleUserStory Selected:" + selectedProject.getName());
        });

        tblViewMultiUser.setOnMouseClicked(click -> {
            selectedProject=tblViewMultiUser.getSelectionModel().getSelectedItem();
            System.out.println("multiUserProject Selected:" + selectedProject.getName());
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
            selectedProject = new savedFile(txtStoryName.getText(),String.valueOf(LocalDate.now()));
        }
        if (rdbMultiUser.isSelected()){
            selectedProject = new savedFile(txtStoryName.getText(), String.valueOf(LocalDate.now()));

            //insert new story into Database
            MultiUserStory multiUserStory = new MultiUserStoryImpl();
            multiUserStory.newMultiUserStory();
        }

    }


    public savedFile selected() {

        return selectedProject;
    }
}
