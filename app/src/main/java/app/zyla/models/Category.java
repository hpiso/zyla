package app.zyla.models;

/**
 * Created by hugopiso on 21/11/2016.
 */
public enum Category {

    Sport("sport"),
    Grocery("grocery"),
    Study("study"),
    Cleaning("cleaning");

    private String img = "";

    Category(String img){
        this.img = img;
    }

    public String toString(){
        return img;
    }
}
