package Application;

public class savedFile {
    String name;
    String dateModified;

    public savedFile(String name, String dateModified) {
        this.name = name;
        this.dateModified = dateModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void selectedSavedFile(){

    }

}
