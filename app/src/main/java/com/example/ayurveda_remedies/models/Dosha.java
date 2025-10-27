package com.example.ayurveda_remedies.models;

public class Dosha {
    public static final String VATA = "Vata";
    public static final String PITTA = "Pitta";
    public static final String KAPHA = "Kapha";
    
    private String type;
    private String description;
    private String characteristics;
    private String recommendations;

    public Dosha(String type, String description, String characteristics, String recommendations) {
        this.type = type;
        this.description = description;
        this.characteristics = characteristics;
        this.recommendations = recommendations;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public static Dosha getVata() {
        return new Dosha(
            VATA,
            "Air & Space - The energy of movement",
            "Light, quick, creative, energetic, changeable",
            "Favor warm, grounding foods. Establish routine. Practice calming activities."
        );
    }

    public static Dosha getPitta() {
        return new Dosha(
            PITTA,
            "Fire & Water - The energy of transformation",
            "Intense, focused, ambitious, sharp intellect",
            "Favor cooling foods. Avoid excessive heat. Practice moderation."
        );
    }

    public static Dosha getKapha() {
        return new Dosha(
            KAPHA,
            "Earth & Water - The energy of stability",
            "Stable, calm, patient, loving, grounded",
            "Favor light, warming foods. Stay active. Seek variety and stimulation."
        );
    }
}
