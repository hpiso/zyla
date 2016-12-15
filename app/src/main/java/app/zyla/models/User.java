package app.zyla.models;

/**
 * Created by trist_000 on 08/12/2016.
 */

public class User {
    private String email;
    private String pwd;
    private int age;
    private int gender;

    public User(String email, String pwd, int age, int gender) {
        this.email = email;
        this.pwd = pwd;
        this.age = age;
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPwd() {
        return pwd;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
