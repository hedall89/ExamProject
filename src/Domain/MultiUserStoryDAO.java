package Domain;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface MultiUserStoryDAO {

    public ObservableList<savedFile> usersMultiUserStories();

    public void newMultiUserStory();

    public void deleteMultiUserStory();

    public void saveMultiUserStory();

    public ArrayList<postIt> loadMultiUserStory();

    public void exportMultiUserStory();
}
