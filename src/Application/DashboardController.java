package Application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import static Application.LoginController.loadStage;

public class DashboardController {

    @FXML
    private Button btnProjects, btnLogOut, btnAddUser, btnExportProject, btnNewStory,
            btnDeleteStory, btnCreateNewStory, btnCancelStory;

    @FXML
    private VBox Vbox;

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

    @FXML
    private Pane pnlCreateStory;


    ObservableList<savedFile> savedTextFiles = FXCollections.observableArrayList();

    public static savedFile selectedProject;


    @FXML
    void handleButtonAction(ActionEvent event) {

        if (event.getSource() == btnProjects) {
            paneProjects.toFront();
            lblWindow.setText("Projects");


        }

        if (event.getSource() == btnLogOut) {

        }

    }

    public void initialize() {
        loadStory();
        insertSavedProjectsToTable();

    }

    private void insertSavedProjectsToTable() {
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
    void handleNewStory(ActionEvent event) {
        if (event.getSource() == btnNewStory) {
        }


    }
}
