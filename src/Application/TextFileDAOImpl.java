package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TextFileDAOImpl implements TextFileDAO {
    ObservableList<savedFile> allSavedFiles = FXCollections.observableArrayList();

    @Override
    public ObservableList<savedFile> allFilesInFolder() {

        //path to folder
        URL path = getClass().getResource("/SavedFiles");


        File folder = new File(String.valueOf(path.getPath()));
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
}
