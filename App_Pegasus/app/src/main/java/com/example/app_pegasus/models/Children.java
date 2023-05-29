package com.example.app_pegasus.models;

public class Children {

    String id;
    String name;
    String email;
    String parentEmail;

    public Children(String id, String name, String email, String parentEmail) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.parentEmail = parentEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }
}
