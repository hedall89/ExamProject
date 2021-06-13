package Application;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface MultiUserStory {

    public ObservableList<savedFile> usersMultiUserStories();

    public void newMultiUserStory();

    public void deleteMultiUserStory();

    public void saveMultiUserStory();

    public void loadMultiUserStory();
}
