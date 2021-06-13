package Application;

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
    private MenuItem item1, item2, item3;
    static ArrayList<postIt> PostIts = new ArrayList<postIt>();

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


    public void initialize() {
        //loadFromTextFile();
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource()== btnStoryAddPostit){
            drawPostIt();
        }
        if (event.getSource()== btnStorySave){
            TextFromFileDAO postItTextDAO = new TextFromFileDAOImpl();
            postItTextDAO.saveTextFile();
            System.out.println("saving");
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
         item1 = new MenuItem("Edit PostIt");
         item2 = new MenuItem("Delete PostIt");
         item3 = new MenuItem("Create UnderPostIt");
        context.getItems().addAll(item1,item2,item3);

        System.out.println("PostIt number: " + PostIts.indexOf(p));



        //only right click to show contextmenu
        if(event.getButton() == MouseButton.SECONDARY){

            context.show(ap,event.getScreenX(),event.getScreenY());
        }

        item1.setOnAction((ActionEvent actionEvent) -> {
            editText(p);
        });

        //Removing a PostIt
        item2.setOnAction((ActionEvent actionEvent) -> {
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

    private void loadFromTextFile() {
        //Removing not saved PostIt, before adding saved PostIts
        removeAllPostIts();
        PostIts.clear();

        //uses TextfileDAO interface to gather all postIts objects made in the textfile.
        TextFromFileDAO postItTextDAO = new TextFromFileDAOImpl();
        PostIts = postItTextDAO.loadTextFile();

        for (Application.postIt postIt : PostIts) {

            //Drawing all PostIts
            ap.getChildren().addAll(postIt.getR(), postIt.getText());
            postIt.draw();

            //dragable PostIts
            postIt.getR().setOnMouseDragged(event -> dragPostIt(event, postIt));

            //Context menu set on Mouseclick(right)
            postIt.getR().setOnMouseClicked(event -> postItOptions(event, postIt));
        }

    }

    private void removeAllPostIts() {
        for (Application.postIt postIt : PostIts) {
            ap.getChildren().removeAll(postIt.getR(), postIt.getText());

        }
    }


    private void saveMultiUserStory() {

    }

}
