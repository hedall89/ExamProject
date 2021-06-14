package Domain;

import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;

public class postIt implements Comparable<postIt> {
    private double x,y;
    private Rectangle r;
    private Label text;


    public postIt(double x, double y, Rectangle r, Label text) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.text = text;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    public Label getText() {
        return text;
    }

    public void setText(Label text) {
        this.text = text;
    }

    public void draw()  {
        r.setHeight(100);
        r.setWidth(80);
        r.setTranslateX(x);
        r.setTranslateY(y);
    }

    public void move(){
        text.setLayoutX(x+1);
        text.setLayoutY(y+1);
    }

    @Override
    public String toString() {
        return text.getText();
    }

    @Override
    public int compareTo(postIt o) {
        return (int) (this.x - o.x);
    }
}
