package Domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TextFileDAOImplTest {

    @Test
    public void testAllFilesInFolder(){
        TextFileDAOImpl textFileDAO = new TextFileDAOImpl();

        textFileDAO.allFilesInFolder();

        String result = textFileDAO.allSavedFiles.get(0).getName();
        String result2 = textFileDAO.allSavedFiles.get(1).getName();

        assertEquals("asdqwrqw",result);
        assertEquals("sort",result2);


    }

}