package com.example.project_examen;

public class Users {
    private final int id;
    private final String email;
    private final String username;
    private final String role;

    public Users(int id, String email, String username, String role) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
