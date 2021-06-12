package Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TextFromFileDAOImpl implements TextFromFileDAO {
    Path path = Paths.get("src/SavedFiles");

    @Override
    public ArrayList<postIt> loadTextFile() {
        int lineMax = 0;



        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(path + "/" + DashboardController.selectedProject.getName()));
            lineNumberReader.skip(Long.MAX_VALUE);
            lineMax = lineNumberReader.getLineNumber();

            System.out.println("Lines in textfile " + lineNumberReader.getLineNumber());
        } catch (Exception e) {
        }


        try {
            BufferedReader reader = new BufferedReader(new FileReader(path + "/" + DashboardController.selectedProject.getName()));


            for (int i = 0; i < lineMax / 3; i++) {
                //Reading PostIts x and y from textFile
                double rectX = Double.parseDouble(reader.readLine());
                double rectY = Double.parseDouble(reader.readLine());

                //Reading Text from TextFile and sets text x and y
                Label labelText = new Label(reader.readLine());
                labelText.setLayoutX(rectX + 1);
                labelText.setLayoutY(rectY + 1);

                //Drawing PostIts
                Rectangle rect = new Rectangle();
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);

                postIt p = new postIt(rectX, rectY, rect, labelText);
                //adding it to Arraylist
                StoryController.PostIts.add(p);

            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error with selected file");
        }

        return StoryController.PostIts;
    }

    @Override
    public void saveTextFile() {
        try {

            File savedFile = new File(path + "/" + DashboardController.selectedProject.getName() + ".txt");
            if (!savedFile.exists()) {
                savedFile.createNewFile();
                System.out.println("creating new file");
            }


            BufferedWriter wr = new BufferedWriter(new FileWriter(savedFile));

            System.out.println(StoryController.PostIts.size());

            for (Application.postIt postIt : StoryController.PostIts) {
                wr.write("" + postIt.getX());
                wr.newLine();
                wr.write("" + postIt.getY());
                wr.newLine();
                wr.write("" + postIt.toString());
                wr.newLine();

            }
            wr.close();
            System.out.println("file OverWritten");

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}


