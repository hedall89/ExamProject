package Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class MultiUserStoryImpl implements MultiUserStory {
    ObservableList<savedFile> multiUserStories = FXCollections.observableArrayList();
    Connection con = null;
    CallableStatement myStmt = null;
    private int userID = 0;


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
            con.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

        return multiUserStories;
    }

    private void fetchUserID() {

        try{
            //DB connection
            con = DatabaseConnector.con;

            String Username = LoginController.username;



            //prepared statement to fetch UserID
            Statement stmt = con.createStatement();
            ResultSet rsUserID = stmt.executeQuery("SELECT fldUserID from tblUser where fldUsername = '" + Username + "'");

            userID = 0;

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

        try{
            con = DatabaseConnector.con;

            String multiUserStoryName = DashboardController.selectedProject.getName();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("Delete from tblStory where fldName ='" + multiUserStoryName + "'");
            System.out.println("multiUserStory deleted: " + DashboardController.selectedProject.getName());

            con.close();
        } catch (SQLException e){
        e.printStackTrace();
    }





    }

    @Override
    public void saveMultiUserStory() {

        try {
            con = DatabaseConnector.con;

            //hardcoded
            int storyID = 1;

            //--------
            //delete old postIt
            Statement stmt = con.createStatement();
            stmt.executeQuery("");


            //saving postIt on at a time
            for (Application.postIt postIt : StoryController.PostIts) {
                myStmt = con.prepareCall("{dbo.saveMultiUserStory(?,?,?,?)}");

                //sets all the parameters for the stored procedure
                myStmt.registerOutParameter(1, Types.INTEGER);
                myStmt.setString(1, String.valueOf(postIt.getX()));

                myStmt.registerOutParameter(2, Types.INTEGER);
                myStmt.setString(2, String.valueOf(postIt.getY()));

                myStmt.registerOutParameter(3, Types.DATE);
                myStmt.setString(3, String.valueOf(postIt.toString()));

                myStmt.registerOutParameter(4, Types.INTEGER);
                myStmt.setInt(4, storyID);

                myStmt.execute();
            }




        } catch (SQLException e){
            e.printStackTrace();
        }
    }




    @Override
    public void loadMultiUserStory() {

    }
}
