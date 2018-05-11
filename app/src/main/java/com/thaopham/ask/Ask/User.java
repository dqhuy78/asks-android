package com.thaopham.ask.Ask;

public class User {
    private int id;
    private String username, email, password;

    public User(int id, String preferencesString, String sharedPreferencesString, String string){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int id, String username, String email, String password, String passwordconfirm) {
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
