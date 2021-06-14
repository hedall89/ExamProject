package Application;

import Domain.MultiUserStoryDAO;
import Domain.MultiUserStoryDAOImpl;
import Domain.savedFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

import static Application.LoginController.loadStage;


public class newStoryController {


    @FXML
    private Button btnCreateStory, btnCreateStoryCancel;

    @FXML
    private RadioButton rdbSingleUser, rdbMultiUser;

    @FXML
    private TextField txtStoryName;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnCreateStory) {

            storySelector();

            //Opens storyUI
            loadStage("/View/StoryUI.fxml");

            //closing newStory
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();

        }

        if (event.getSource() == btnCreateStoryCancel) {
            //Opens storyUI
            loadStage("/View/TimeLineToolUI.fxml");

            //closing newStory
            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }
    }

    @FXML
    void storySelector() {
        if (rdbSingleUser.isSelected()){
            DashboardController.selectedProject = new savedFile(txtStoryName.getText(),String.valueOf(LocalDate.now()));
            StoryController.storyManager = 2;

        }
        if (rdbMultiUser.isSelected()){
            DashboardController.selectedProject = new savedFile(txtStoryName.getText(), String.valueOf(LocalDate.now()));

            //insert new story into Database
            MultiUserStoryDAO multiUserStory = new MultiUserStoryDAOImpl();
            multiUserStory.newMultiUserStory();
            StoryController.storyManager = 4;
        }
    }
}
