package com.example.khokan.tutorisbdservice;

/**
 * Created by USER on 9/17/2018.
 */

public class Users {
    public String name;
    public String status;
    public String image;

    public Users() {
    }

    public Users(String name, String status, String image) {
        this.name = name;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
