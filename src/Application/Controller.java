package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Controller {
    //private ArrayList<postIt> PostIts;
    //private ArrayList<text> Texts;
    //private Rectangle r;
    private ContextMenu context;
    private MenuItem item1, item2, item3;
    ArrayList<postIt> PostIts = new ArrayList<postIt>();


    public void initialize() {

    }

    @FXML
    public javafx.scene.layout.AnchorPane AnchorPane;

    @FXML
    void Add(ActionEvent event) {

        DrawPostIt();
    }

    public void DrawPostIt() {

        //Creating PostIt Text

        Label labelText = new Label("Ny note");
        labelText.setLayoutX(31);
        labelText.setLayoutY(31);


        //creating PostIt shape
        Rectangle r = new Rectangle();
        r.setFill(Color.WHITE);
        r.setStroke(Color.GRAY);
        int rx = 30;
        int ry = 30;

        //creating PostIt Object
        postIt p = new postIt(rx,ry,r,labelText);
        //adding it to Arraylist
        PostIts.add(p);
        AnchorPane.getChildren().addAll(r,labelText);
        //drawing rectangle(PostIt)
        p.draw();


        //drag event for every postIt
        r.setOnMouseDragged(event -> dragPostIt(event, p));

        //Context menu set on Mouseclick(right)
        r.setOnMouseClicked(event -> postItOptions(event, p));
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

            context.show(AnchorPane,event.getScreenX(),event.getScreenY());
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

        AnchorPane.getChildren().add(tf);

        //Removes textfield and updates Label
        AnchorPane.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)){

                AnchorPane.getChildren().remove(tf);
                PostItText.setText(tf.getText());
                p.setText(PostItText);
            }
                });

    }

    public void removePostIt(postIt p) {
        AnchorPane.getChildren().removeAll(p.getR(),p.getText());
    }

}
