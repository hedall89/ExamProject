package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.Node;

import java.util.ArrayList;

public class Controller {
    private double mouseX, mouseY;

    private ArrayList<postIt> PostIts;


    public void initialize() {


    }

    @FXML
    private javafx.scene.layout.AnchorPane AnchorPane;

    @FXML
    void Add(ActionEvent event) {
        DrawPostIt();

    }

    public void DrawPostIt() {
        PostIts = new ArrayList<postIt>();

        //creating every PostIt
        Rectangle r = new Rectangle();
        r.setFill(Color.WHITE);
        r.setStroke(Color.GRAY);
        int x = 30;
        int y = 30;
        postIt p = new postIt(x,y,r);
        PostIts.add(p);
        AnchorPane.getChildren().add(r);
        p.draw();


        //drag event for every postIt
        r.setOnMouseDragged(event -> drag(event, p));

    }

    public void drag(MouseEvent event, postIt p) {


        //Drawing new postIt while we drag it on the screen
        p.setX(p.getX() + event.getX());
        p.setY(p.getY() + event.getY());
        p.draw();

    }


}
