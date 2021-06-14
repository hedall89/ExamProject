package Domain;

import javafx.collections.ObservableList;

public interface TextFileDAO {

    public ObservableList<savedFile> allFilesInFolder();

    public void deleteTextFile();

}
