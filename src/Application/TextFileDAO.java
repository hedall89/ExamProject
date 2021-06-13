package Application;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;

public interface TextFileDAO {

    public ObservableList<savedFile> allFilesInFolder();

    public void deleteTextFile();

    public void newTextFile();
}
