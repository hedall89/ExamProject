package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javax.sound.midi.SysexMessage;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class MultiUserStoryImpl implements MultiUserStory {
    ObservableList<savedFile> multiUserStories = FXCollections.observableArrayList();
    Connection con = null;
    CallableStatement myStmt = null;
    private int userID = 0;
    private int storyID = 0;


    @Override
    public ObservableList<savedFile> usersMultiUserStories() {
        multiUserStories.clear();

        try{

            //DB connection
            con = DatabaseConnector.con;
            fetchUserID();
            
            //calling stored procedure
            myStmt = con.prepareCall("{call dbo.usersMultiUserStories(?)}");

            //sets UserID into the stored procedure
            myStmt.registerOutParameter(1, Types.INTEGER);
            myStmt.setInt(1, userID);

            ResultSet rs = myStmt.executeQuery();

            while (rs.next()) {
                multiUserStories.add(new savedFile(rs.getString("fldName"), rs.getString("fldLastModified")));
            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        return multiUserStories;
    }

    private void fetchStoryID() {

        try{
            //DB connection
            con = DatabaseConnector.con;

            //prepared statement to fetch UserID
            Statement stmt = con.createStatement();
            ResultSet rsStoryID = stmt.executeQuery("SELECT fldStoryID from tblStory where fldName = '" + DashboardController.selectedProject.getName() + "'");

            //getting UserID
            while (rsStoryID.next()){
                storyID = rsStoryID.getInt("fldStoryID");
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void fetchUserID() {
        try{
            //DB connection
            con = DatabaseConnector.con;

            String Username = LoginController.username;



            //prepared statement to fetch UserID
            Statement stmt = con.createStatement();
            ResultSet rsUserID = stmt.executeQuery("SELECT fldUserID from tblUser where fldUsername = '" + Username + "'");


            //getting UserID
            while (rsUserID.next()){
                userID = rsUserID.getInt("fldUserID");
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void newMultiUserStory() {
        try{
            //DB connection
            con = DatabaseConnector.con;

            fetchUserID();

            myStmt = con.prepareCall("{call dbo.NewMultiUserStory(?,?,?)}");

            Date date = Date.valueOf(LocalDate.now());

            //sets all the parameters for the stored procedure
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setString(1, String.valueOf(DashboardController.selectedProject.getName()));

            myStmt.registerOutParameter(2, Types.DATE);
            myStmt.setString(2, String.valueOf(date));

            myStmt.registerOutParameter(3, Types.INTEGER);
            myStmt.setInt(3, userID);

            myStmt.execute();


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMultiUserStory() {

        fetchStoryID();

        try{
            con = DatabaseConnector.con;

            //fetch the selectedStory
            String multiUserStoryName = DashboardController.selectedProject.getName();

            myStmt = con.prepareCall("{call dbo.delteMultiUserStory(?,?)}");


            //sets all the parameters for the stored procedure
            myStmt.registerOutParameter(1, Types.VARCHAR);
            myStmt.setInt(1, storyID);

            myStmt.registerOutParameter(2, Types.VARCHAR);
            myStmt.setString(2, String.valueOf(multiUserStoryName));

            myStmt.execute();

            System.out.println("multiUserStory deleted: " + DashboardController.selectedProject.getName());


        } catch (SQLException e){
        e.printStackTrace();
    }





    }

    @Override
    public void saveMultiUserStory() {

        try {
            con = DatabaseConnector.con;

            fetchStoryID();

            //delete old postIt in the selected story
            Statement stmt = con.createStatement();
            stmt.execute("delete from tblPostIt where fldStoryID = '" + storyID + "'");


            //saving postIt on at a time
            for (Application.postIt postIt : StoryController.PostIts) {
                myStmt = con.prepareCall("{call dbo.saveMultiUserStory(?,?,?,?)}");

                //sets all the parameters for the stored procedure
                myStmt.registerOutParameter(1, Types.VARCHAR);
                myStmt.setString(1, String.valueOf(postIt.getX()));

                myStmt.registerOutParameter(2, Types.VARCHAR);
                myStmt.setString(2, String.valueOf(postIt.getY()));

                myStmt.registerOutParameter(3, Types.VARCHAR);
                myStmt.setString(3, String.valueOf(postIt.toString()));

                myStmt.registerOutParameter(4, Types.INTEGER);
                myStmt.setInt(4, storyID);

                myStmt.execute();

                System.out.println("gemt: " + String.valueOf(postIt.getX()) + " - " + postIt.getY() + " - " + postIt.toString() + " - " + storyID);


            }
            }catch(SQLException e){
                e.printStackTrace();
        }
    }

    @Override
    public ArrayList<postIt> loadMultiUserStory() {
        StoryController.PostIts.clear();


        try{

            //DB connection
            con = DatabaseConnector.con;

            //getting storyID
            fetchStoryID();

            //calling stored procedure
            myStmt = con.prepareCall("{call dbo.loadMultiUserStory(?)}");

            //sets UserID into the stored procedure
            myStmt.registerOutParameter(1, Types.INTEGER);
            myStmt.setInt(1, storyID);

            ResultSet rs = myStmt.executeQuery();

            while (rs.next()) {
                //sets rectangle
                Rectangle rect = new Rectangle();
                rect.setFill(Color.WHITE);
                rect.setStroke(Color.GRAY);

                //local label variable
                Label labelText = new Label();

                postIt p = new postIt(Double.parseDouble(rs.getString("fldX")),
                        Double.parseDouble(rs.getString("fldY")), rect, labelText);
                p.getText().setLayoutX(p.getX()+1);
                p.getText().setLayoutY(p.getY()+1);
                labelText.setText(rs.getString("fldText"));
                p.setText(labelText);

                StoryController.PostIts.add(p);

            }


        } catch (SQLException e){
            e.printStackTrace();
        }



        return StoryController.PostIts;


    }
}
