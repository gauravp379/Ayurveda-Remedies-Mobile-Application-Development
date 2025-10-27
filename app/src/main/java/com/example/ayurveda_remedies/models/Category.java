package com.example.ayurveda_remedies.models;

public class Category {
    private String id;
    private String name;
    private String description;
    private int iconResource;
    private int colorResource;
    private int remedyCount;

    public Category(String id, String name, String description, int iconResource, int colorResource) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconResource = iconResource;
        this.colorResource = colorResource;
        this.remedyCount = 0;
    }

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public int getColorResource() {
        return colorResource;
    }

    public void setColorResource(int colorResource) {
        this.colorResource = colorResource;
    }

    public int getRemedyCount() {
        return remedyCount;
    }

    public void setRemedyCount(int remedyCount) {
        this.remedyCount = remedyCount;
    }
}
