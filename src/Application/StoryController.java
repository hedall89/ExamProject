package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class StoryController {
    private MenuItem item1, item2, item3;
    ArrayList<postIt> PostIts = new ArrayList<postIt>();
    public URL path = getClass().getResource("/SavedFiles");



    @FXML
    private AnchorPane ap;

    @FXML
    private Label lblStory;


    public void initialize() {
        loadFromTextFile();
        System.out.println(DashboardController.selectedProject.getName());
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

    @FXML
    public void loadProject(ActionEvent event) {
        loadFromTextFile();
    }

    private void loadFromTextFile() {
        double rectX = 0;
        double rectY = 0;
        Label labelText = null;
        int lineMax = 0;

        //Removing not saved PostIt, before adding saved PostIts
        removeAllPostIts();
        PostIts.clear();

        System.out.println(path.getPath() + "/" + DashboardController.selectedProject);

        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(path.getPath() + "/" + DashboardController.selectedProject.getName()));
            lineNumberReader.skip(Long.MAX_VALUE);
            lineMax = lineNumberReader.getLineNumber();

            System.out.println(lineNumberReader.getLineNumber());
        }catch (Exception e){

        }


        //Load TextFile
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path.getPath() + "/" + DashboardController.selectedProject.getName()));


            for (int i = 0; i < lineMax/3; i++) {
                //Reading PostIts x and y from textFile
                rectX = Double.parseDouble(reader.readLine());
                rectY = Double.parseDouble(reader.readLine());

                //Reading Text from TextFile and sets text x and y
                labelText = new Label(reader.readLine());
                labelText.setLayoutX(rectX+1);
                labelText.setLayoutY(rectY+1);

                //Drawing PostIts
                Rectangle rect = new Rectangle();
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);

                postIt p = new postIt(rectX,rectY,rect,labelText);
                //adding it to Arraylist
                PostIts.add(p);
                ap.getChildren().addAll(rect,labelText);
                p.draw();

                //drag event for every postIt
                rect.setOnMouseDragged(event -> dragPostIt(event, p));

                //Context menu set on Mouseclick(right)
                rect.setOnMouseClicked(event -> postItOptions(event, p));
            }

            reader.close();

        }
        catch (Exception e){

            System.out.println("fejl, den kunne ikke loade filen");
        }

    }

    private void removeAllPostIts() {
        for (Application.postIt postIt : PostIts) {
            ap.getChildren().removeAll(postIt.getR(), postIt.getText());

        }
    }

    @FXML
    void saveProject(ActionEvent event){
        saveToTextFile();
    }

    private void saveToTextFile() {

        try{

            File savedFile = new File(path.getPath() + "/" + DashboardController.selectedProject.getName());
            if (savedFile.createNewFile()){
                System.out.println("File Created");
            }else {
                System.out.println("file OverWritten");

                BufferedWriter wr = new BufferedWriter(new FileWriter(path.getPath() + "/" + DashboardController.selectedProject.getName()));

                for (Application.postIt postIt : PostIts) {
                    wr.write("" + postIt.getX());
                    wr.newLine();
                    wr.write("" + postIt.getY());
                    wr.newLine();
                    wr.write("" + postIt.toString());
                    wr.newLine();


                }
                wr.close();

            }

        }catch (IOException e){
            System.out.println("Error");
        }




    }

}
