package Domain;

import Domain.postIt;

import java.util.ArrayList;

public interface TextFromFileDAO {


    public ArrayList<postIt> loadTextFile();

    public void saveTextFile();

    public void exportSingleUserStory();
}