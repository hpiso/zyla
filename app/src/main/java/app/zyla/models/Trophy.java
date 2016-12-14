package app.zyla.models;

/**
 * Created by trist_000 on 08/12/2016.
 */

public class Trophy {
    private String title;
    private String description;
    private String img;
    private String date;

    public Trophy(String title, String description, String img, String date) {
        this.title = title;
        this.description = description;
        this.img = img;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
