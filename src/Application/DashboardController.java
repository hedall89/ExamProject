package Application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import static Application.LoginController.loadStage;

import java.io.IOException;
import java.util.ResourceBundle;

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

    public void initialize(URL location, ResourceBundle resources){
        singleUserProjects();
        loadStory();

    }

    private void singleUserProjects() {

        savedTextFiles.clear();

        //path to folder
        URL path = getClass().getResource("/SavedFiles");

        File folder = new File(String.valueOf(path.getPath()));
        File[] files = folder.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        //Enchanted forLoop that
        for (File file : files) {
            if (file.isFile()) {
                savedTextFiles.add(new savedFile(file.getName(), sdf.format(file.lastModified())));
            } else{
                System.out.println("gejl");
            }

        }

        insertSavedProjectsToTable();
        }


    private void insertSavedProjectsToTable() {

        //sets textFileName and dateModified in the correct column
        col_singleUserName.setCellValueFactory(new PropertyValueFactory<savedFile, String>("name"));
        col_singleUserDateModified.setCellValueFactory(new PropertyValueFactory<savedFile, String>("dateModified"));

        //loads the textfile names and Date modified from array
        tblViewSingleUser.setItems(savedTextFiles);




    }

    public void loadStory(){

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


}
