package Domain;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DrawPostItImpl implements DrawPostIt{

    @Override
    public postIt Draw() {
        //creating PostIt shape
        Rectangle rect = new Rectangle();
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.GRAY);

        //PostIt Location
        double rectX = 30;
        double rectY = 30;

        Label labelText = new Label("Ny note");
        labelText.setLayoutX(rectX+1);
        labelText.setLayoutY(rectY+1);

        //creating PostIt Object
        return new postIt(rectX,rectY,rect,labelText);
    }
}
