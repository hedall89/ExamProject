package Application;

import java.util.ArrayList;
import java.util.List;

public interface TextFromFileDAO {


    public ArrayList<postIt> loadTextFile();

    public void saveTextFile();
}