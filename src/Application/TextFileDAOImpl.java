package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class TextFileDAOImpl implements TextFileDAO {
    ObservableList<savedFile> allSavedFiles = FXCollections.observableArrayList();

    String path = "C://Users/bollo/IdeaProjects/ExamProject/src/SavedFiles/";

    @Override
    public ObservableList<savedFile> allFilesInFolder() {


        File folder = new File(String.valueOf(path));
        File[] files = folder.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        //Enchanted forLoop that gather fileName and fileLastModified from folder
        for (File file : files) {
            if (file.isFile()) {
                allSavedFiles.add(new savedFile(file.getName(), sdf.format(file.lastModified())));
            } else {
                System.out.println("Couldn't get fileName or fileLastModified ");
            }

        }

        return allSavedFiles;
    }

    @Override
    public boolean textFileCheck() {

        File savedFile = new File(path + DashboardController.selectedProject.getName());
        return savedFile.exists();

    }

    @Override
    public void deleteTextFile() {
        File selectedFile = new File(path + DashboardController.selectedProject.getName());

        if (selectedFile.delete()){
            System.out.println("file Deleted");
        } else {
            System.out.println("failed to delete the file");
        }

    }

}
