package Application;

import Domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.util.ArrayList;

import static Application.LoginController.loadStage;

public class StoryController {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button btnStoryBack;

    @FXML
    private Label lblStory;

    @FXML
    private Button btnStoryAddPostit;

    @FXML
    private Button btnStorySave;

    private MenuItem edit, delete, createUnderPostIt;
    public static ArrayList<postIt> PostIts = new ArrayList<postIt>();
    public static int storyManager;

    public void initialize() {
        storyManagement();
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btnStoryAddPostit){
            drawPostIt();
        }
        if (event.getSource()== btnStorySave){
            //1 = existing singleUserStory, 2 new singleUserStory
            if (storyManager == 1 || storyManager == 2){
                TextFromFileDAO postItTextDAO = new TextFromFileDAOImpl();
                postItTextDAO.saveTextFile();

                //3 = existing multiUserStory, 4 = new multiUserStory
            } if (storyManager == 3 || storyManager == 4){
                MultiUserStoryDAO multiUserStory = new MultiUserStoryDAOImpl();
                multiUserStory.saveMultiUserStory();
            }

        }
        if (event.getSource()== btnStoryBack){

            loadStage("/View/TimeLineToolUI.fxml");

            Node n = (Node) event.getSource();
            Stage previousStage = (Stage) n.getScene().getWindow();
            previousStage.close();
        }

    }

    public void drawPostIt() {

        DrawPostIt drawPostIt = new DrawPostItImpl();

        postIt p = drawPostIt.Draw();

        //adding it to Arraylist
        PostIts.add(p);
        ap.getChildren().addAll(p.getR(),p.getText());

        //drawing rectangle(PostIt)
        p.draw();

        //drag event for every postIt
        p.getR().setOnMouseDragged(event -> dragPostIt(event, p));

        //Context menu set on Mouseclick(right)
        p.getR().setOnMouseClicked(event -> postItOptions(event, p));
    }

    public void dragPostIt(MouseEvent event, postIt p) {
        //only dragable with left mousebutton
        if(event.getButton() == MouseButton.PRIMARY){
            //Drawing new rectangle while we drag it on the screen
            p.setX(p.getX() + event.getX());
            p.setY(p.getY() + event.getY());
            p.draw();
            //moving text
            p.move();
        }

    }

    public void postItOptions(MouseEvent event, postIt p) {

        ContextMenu context = new ContextMenu();

        //sets the options in the context menu
         edit = new MenuItem("Edit PostIt");
         delete = new MenuItem("Delete PostIt");
         createUnderPostIt = new MenuItem("Create UnderPostIt");
        context.getItems().addAll(edit,delete,createUnderPostIt);

        System.out.println("PostIt number: " + PostIts.indexOf(p));



        //only right click to show contextmenu
        if(event.getButton() == MouseButton.SECONDARY){

            context.show(ap,event.getScreenX(),event.getScreenY());
        }

        edit.setOnAction((ActionEvent actionEvent) -> {
            editText(p);
        });

        //Removing a PostIt
        delete.setOnAction((ActionEvent actionEvent) -> {
            removePostIt(p);

        });

    }

    public void editText(postIt p){


        TextField tf = new TextField();

        //sets textfields text to the same as the PostIts
        Label PostItText = p.getText();
        tf.setText(PostItText.getText());
        tf.setPrefSize(78,98);

        tf.setLayoutX(p.getX() + 1);
        tf.setLayoutY(p.getY() + 1);

        ap.getChildren().add(tf);

        //Removes textfield and updates Label
        ap.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)){

                ap.getChildren().remove(tf);
                PostItText.setText(tf.getText());
                p.setText(PostItText);
            }
                });

    }

    public void removePostIt(postIt p) {
        ap.getChildren().removeAll(p.getR(),p.getText());
        PostIts.remove(p);
    }

    private void removeAllPostIts() {
        for (Domain.postIt postIt : PostIts) {
            ap.getChildren().removeAll(postIt.getR(), postIt.getText());

        }
    }

    private void storyManagement() {

        //switch to manage which Story the user opens
        switch (storyManager){
            case 1:
                loadFromTextFile();
                break;
            case 3:
                loadMultiUserStory();
                break;
            default:
                break;
        }

    }

    private void loadFromTextFile() {
        //Clearing the PostIt array, before adding saved PostIts
        removeAllPostIts();

        //uses TextFromFileDAO interface to gather all postIts objects made in the textfile.
        TextFromFileDAO postItTextDAO = new TextFromFileDAOImpl();
        PostIts = postItTextDAO.loadTextFile();

        for (Domain.postIt postIt : PostIts) {

            //Drawing all PostIts
            ap.getChildren().addAll(postIt.getR(), postIt.getText());
            postIt.draw();

            //dragable PostIts
            postIt.getR().setOnMouseDragged(event -> dragPostIt(event, postIt));

            //Context menu set on Mouseclick(right)
            postIt.getR().setOnMouseClicked(event -> postItOptions(event, postIt));
        }

    }

    private void loadMultiUserStory() {
        removeAllPostIts();

        MultiUserStoryDAO multiUserStory = new MultiUserStoryDAOImpl();
        PostIts = multiUserStory.loadMultiUserStory();

        System.out.println("PostIt Array size : " + PostIts.size());



        for (Domain.postIt postIt : PostIts) {

            //Drawing all PostIts
            ap.getChildren().addAll(postIt.getR(), postIt.getText());
            postIt.draw();

            //dragable PostIts
            postIt.getR().setOnMouseDragged(event -> dragPostIt(event, postIt));

            //Context menu set on Mouseclick(right)
            postIt.getR().setOnMouseClicked(event -> postItOptions(event, postIt));
        }

    }

}
