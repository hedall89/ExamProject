package Application;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface TextFileDAO {

    public ObservableList<savedFile> allFilesInFolder();
}
