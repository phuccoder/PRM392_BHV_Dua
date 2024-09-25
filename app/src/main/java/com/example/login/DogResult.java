package com.example.login;

import java.io.Serializable;

public class DogResult implements Serializable {
    public String name;
    public int imageResId; // Store raw resource ID of GIF

    public DogResult(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }
}

