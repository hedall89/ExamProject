package Application;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MultiUserStory {

    public ObservableList<savedFile> usersMultiUserStories();

    public void newMultiUserStory();

    public void deleteMultiUserStory();

    public void saveMultiUserStory();

    public ArrayList<postIt> loadMultiUserStory();

    public void exportMultiUserStory();
}
