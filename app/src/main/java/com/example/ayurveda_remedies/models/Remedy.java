package com.example.ayurveda_remedies.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Remedy implements Serializable {
    private String id;
    private String name;
    private String categoryId;
    private String categoryName;
    private String shortDescription;
    private String detailedDescription;
    private List<String> ingredients;
    private List<String> preparation;
    private String usage;
    private List<String> benefits;
    private List<String> precautions;
    private String dosage;
    private int imageResource;
    private int difficulty; // 1=Easy, 2=Medium, 3=Hard
    private int preparationTime; // in minutes
    private boolean isFavorite;
    private int popularity;

    public Remedy(String id, String name, String categoryId, String categoryName, 
                  String shortDescription, int imageResource) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.shortDescription = shortDescription;
        this.imageResource = imageResource;
        this.ingredients = new ArrayList<>();
        this.preparation = new ArrayList<>();
        this.benefits = new ArrayList<>();
        this.precautions = new ArrayList<>();
        this.isFavorite = false;
        this.popularity = 0;
        this.difficulty = 1;
        this.preparationTime = 10;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public List<String> getPreparation() {
        return preparation;
    }

    public void setPreparation(List<String> preparation) {
        this.preparation = preparation;
    }

    public void addPreparationStep(String step) {
        this.preparation.add(step);
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

    public void addBenefit(String benefit) {
        this.benefits.add(benefit);
    }

    public List<String> getPrecautions() {
        return precautions;
    }

    public void setPrecautions(List<String> precautions) {
        this.precautions = precautions;
    }

    public void addPrecaution(String precaution) {
        this.precautions.add(precaution);
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getDifficultyText() {
        switch (difficulty) {
            case 1: return "Easy";
            case 2: return "Medium";
            case 3: return "Hard";
            default: return "Unknown";
        }
    }
}
