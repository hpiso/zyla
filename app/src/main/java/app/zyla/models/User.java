package app.zyla.models;

/**
 * Created by trist_000 on 08/12/2016.
 */

public class User {
    private String name;
    private String pwd;
    private int age;
    private int gender;

    public User(String name, String pwd, int age, int gender) {
        this.name = name;
        this.pwd = pwd;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
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
