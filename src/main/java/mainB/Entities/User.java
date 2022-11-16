package mainB.Entities;

import mainB.annotations.*;


public class User {

    @primaryKey
    @unique
    @autoIncrementation
    private int id;
    private SomeEntity some=new SomeEntity();

    @FixedSize
    private String email;

    @FixedSize(size = 50)
    private String password;

    public User() {

    }

    public User(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", some=" + some +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
