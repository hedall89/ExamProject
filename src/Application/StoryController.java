package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StoryController {
    private MenuItem item1, item2, item3;
    static ArrayList<postIt> PostIts = new ArrayList<postIt>();
    Path path = Paths.get("src/SavedFiles");



    @FXML
    private AnchorPane ap;

    @FXML
    private Label lblStory;


    public void initialize() {
        storyManagement();
    }

    private void storyManagement() {
        TextFileDAO textFileDAO = new TextFileDAOImpl();
        if (textFileDAO.textFileCheck()) {
            loadFromTextFile();
            System.out.println("loading existing file");
        }


    }


    @FXML
    void addPostIt(ActionEvent event) {
        DrawPostIt();
    }

    public void DrawPostIt() {
        //creating PostIt shape
        Rectangle rect = new Rectangle();
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.GRAY);
        double rectX = 30;
        double rectY = 30;

        Label labelText = new Label("Ny note");
        labelText.setLayoutX(rectX+1);
        labelText.setLayoutY(rectY+1);

        //creating PostIt Object
        postIt p = new postIt(rectX,rectY,rect,labelText);
        //adding it to Arraylist
        PostIts.add(p);
        ap.getChildren().addAll(rect,labelText);
        //drawing rectangle(PostIt)
        p.draw();



        //drag event for every postIt
        rect.setOnMouseDragged(event -> dragPostIt(event, p));

        //Context menu set on Mouseclick(right)
        rect.setOnMouseClicked(event -> postItOptions(event, p));
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


        System.out.println(path + "/" + DashboardController.selectedProject.getName());

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

    @FXML
    void saveProject(ActionEvent event){
        TextFromFileDAO postItTextDAO = new TextFromFileDAOImpl();
        postItTextDAO.saveTextFile();
        System.out.println("saving");
    }

}
