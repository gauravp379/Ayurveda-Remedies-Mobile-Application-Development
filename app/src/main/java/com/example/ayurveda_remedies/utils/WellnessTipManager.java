package com.example.ayurveda_remedies.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WellnessTipManager {
    
    private static final List<String> WELLNESS_TIPS = new ArrayList<>();
    
    static {
        WELLNESS_TIPS.add("🌅 Wake up before sunrise to align with nature's rhythm (Brahma Muhurta)");
        WELLNESS_TIPS.add("💧 Drink warm water in the morning to kickstart digestion");
        WELLNESS_TIPS.add("🧘 Practice 10 minutes of meditation daily for mental clarity");
        WELLNESS_TIPS.add("🌿 Oil pulling with sesame or coconut oil improves oral health");
        WELLNESS_TIPS.add("🥛 Have warm milk with turmeric before bed for better sleep");
        WELLNESS_TIPS.add("🚶 Take a 20-minute walk after meals to aid digestion");
        WELLNESS_TIPS.add("🫖 Sip ginger tea throughout the day to boost metabolism");
        WELLNESS_TIPS.add("🧴 Massage your body with warm oil (Abhyanga) before bathing");
        WELLNESS_TIPS.add("🥗 Eat your largest meal at noon when digestive fire is strongest");
        WELLNESS_TIPS.add("😴 Go to bed by 10 PM for optimal rest and rejuvenation");
        WELLNESS_TIPS.add("🌬️ Practice deep breathing (Pranayama) to balance your doshas");
        WELLNESS_TIPS.add("🍯 Take a spoonful of Chyawanprash daily for immunity");
        WELLNESS_TIPS.add("🧂 Use spices like cumin, coriander, and fennel for digestion");
        WELLNESS_TIPS.add("🌡️ Avoid ice-cold drinks; prefer room temperature or warm beverages");
        WELLNESS_TIPS.add("🛀 Bathe with warm water but rinse hair with cool water");
        WELLNESS_TIPS.add("🌾 Include whole grains, legumes, and fresh vegetables in your diet");
        WELLNESS_TIPS.add("☀️ Get 15 minutes of morning sunlight for vitamin D");
        WELLNESS_TIPS.add("🧘‍♀️ Yoga asanas balance body, mind, and spirit");
        WELLNESS_TIPS.add("🌰 Eat a handful of soaked almonds for brain health");
        WELLNESS_TIPS.add("🍵 Herbal teas like tulsi and chamomile promote relaxation");
        WELLNESS_TIPS.add("👃 Use nasya oil drops to clear nasal passages");
        WELLNESS_TIPS.add("🎵 Listen to calming music to reduce stress and anxiety");
        WELLNESS_TIPS.add("🍋 Start your day with lemon water to detoxify");
        WELLNESS_TIPS.add("🌙 Follow a consistent sleep schedule for dosha balance");
        WELLNESS_TIPS.add("🥘 Cook with ghee for healthy fats and better digestion");
        WELLNESS_TIPS.add("🌻 Practice gratitude daily to cultivate positive energy");
        WELLNESS_TIPS.add("🍎 Eat seasonal and local fruits for optimal nutrition");
        WELLNESS_TIPS.add("💆 Scalp massage with coconut oil promotes hair growth");
        WELLNESS_TIPS.add("🧊 Avoid eating heavy meals late at night");
        WELLNESS_TIPS.add("🌸 Use rose water as a natural face toner");
    }
    
    public static String getRandomTip() {
        Random random = new Random();
        int index = random.nextInt(WELLNESS_TIPS.size());
        return WELLNESS_TIPS.get(index);
    }
    
    public static String getTipOfTheDay() {
        // Returns same tip for the whole day based on day of year
        int dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR);
        int index = dayOfYear % WELLNESS_TIPS.size();
        return WELLNESS_TIPS.get(index);
    }
    
    public static List<String> getAllTips() {
        return new ArrayList<>(WELLNESS_TIPS);
    }
}
