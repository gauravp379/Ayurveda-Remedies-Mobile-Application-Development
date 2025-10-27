package com.example.ayurveda_remedies.data;

import com.example.ayurveda_remedies.R;
import com.example.ayurveda_remedies.models.Category;
import com.example.ayurveda_remedies.models.Remedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemedyRepository {
    private static RemedyRepository instance;
    private List<Category> categories;
    private List<Remedy> remedies;

    private RemedyRepository() {
        initializeCategories();
        initializeRemedies();
    }

    public static synchronized RemedyRepository getInstance() {
        if (instance == null) {
            instance = new RemedyRepository();
        }
        return instance;
    }

    private void initializeCategories() {
        categories = new ArrayList<>();
        categories.add(new Category("digestive", "Digestive Health", 
            "Natural remedies for digestion", R.drawable.ic_ayurveda_logo, R.color.category_digestive));
        categories.add(new Category("respiratory", "Respiratory Care", 
            "Remedies for breathing and lungs", R.drawable.ic_ayurveda_logo, R.color.category_respiratory));
        categories.add(new Category("skin", "Skin & Beauty", 
            "Natural skincare solutions", R.drawable.ic_ayurveda_logo, R.color.category_skin));
        categories.add(new Category("immunity", "Immunity Boost", 
            "Strengthen your immune system", R.drawable.ic_ayurveda_logo, R.color.category_immunity));
        categories.add(new Category("stress", "Stress Relief", 
            "Mental wellness and relaxation", R.drawable.ic_ayurveda_logo, R.color.category_stress));
        categories.add(new Category("pain", "Pain Relief", 
            "Natural pain management", R.drawable.ic_ayurveda_logo, R.color.category_pain));
    }

    private void initializeRemedies() {
        remedies = new ArrayList<>();

        // Digestive Health Remedies
        Remedy turmericMilk = new Remedy("1", "Turmeric Golden Milk", "digestive", "Digestive Health",
            "A traditional warm beverage for digestion and immunity", R.drawable.ic_ayurveda_logo);
        turmericMilk.setDetailedDescription("Golden Milk, also known as Haldi Doodh, is a traditional Ayurvedic remedy that combines the healing properties of turmeric with warm milk. This powerful combination aids digestion, reduces inflammation, and promotes overall wellness.");
        turmericMilk.setIngredients(Arrays.asList(
            "1 cup milk (dairy or plant-based)",
            "1 teaspoon turmeric powder",
            "1/2 teaspoon cinnamon powder",
            "A pinch of black pepper",
            "1 teaspoon honey (optional)"
        ));
        turmericMilk.setPreparation(Arrays.asList(
            "Heat the milk in a saucepan over medium heat",
            "Add turmeric, cinnamon, and black pepper",
            "Stir well and simmer for 5-7 minutes",
            "Remove from heat and let it cool slightly",
            "Add honey if desired and stir well"
        ));
        turmericMilk.setUsage("Drink warm before bedtime for best results");
        turmericMilk.setBenefits(Arrays.asList(
            "Improves digestion and reduces bloating",
            "Boosts immunity naturally",
            "Reduces inflammation throughout the body",
            "Promotes better sleep",
            "Supports liver health"
        ));
        turmericMilk.setPrecautions(Arrays.asList(
            "Avoid if allergic to turmeric",
            "Consult doctor if on blood thinners",
            "May stain clothes and surfaces"
        ));
        turmericMilk.setDosage("One cup daily before bed");
        turmericMilk.setDifficulty(1);
        turmericMilk.setPreparationTime(10);
        turmericMilk.setPopularity(95);
        remedies.add(turmericMilk);

        Remedy gingerTea = new Remedy("2", "Ginger Lemon Tea", "digestive", "Digestive Health",
            "Refreshing tea that aids digestion and relieves nausea", R.drawable.ic_ayurveda_logo);
        gingerTea.setDetailedDescription("Ginger tea is a powerful digestive aid used in Ayurveda for centuries. Combined with lemon, it creates a refreshing and healing beverage that soothes the stomach and boosts metabolism.");
        gingerTea.setIngredients(Arrays.asList(
            "1-inch fresh ginger root",
            "2 cups water",
            "Juice of half a lemon",
            "1 teaspoon honey",
            "Fresh mint leaves (optional)"
        ));
        gingerTea.setPreparation(Arrays.asList(
            "Peel and slice the ginger thinly",
            "Boil water in a pot",
            "Add ginger slices and simmer for 10 minutes",
            "Strain into a cup",
            "Add lemon juice and honey",
            "Garnish with mint if desired"
        ));
        gingerTea.setUsage("Drink 2-3 times daily, especially after meals");
        gingerTea.setBenefits(Arrays.asList(
            "Relieves nausea and motion sickness",
            "Improves digestion and reduces gas",
            "Boosts metabolism",
            "Has anti-inflammatory properties",
            "Strengthens immune system"
        ));
        gingerTea.setPrecautions(Arrays.asList(
            "Avoid on empty stomach if you have acid reflux",
            "Limit intake during pregnancy",
            "May interact with blood thinners"
        ));
        gingerTea.setDosage("2-3 cups daily");
        gingerTea.setDifficulty(1);
        gingerTea.setPreparationTime(15);
        gingerTea.setPopularity(90);
        remedies.add(gingerTea);

        Remedy triphala = new Remedy("3", "Triphala Churna Mix", "digestive", "Digestive Health",
            "Ancient formula for complete digestive wellness", R.drawable.ic_ayurveda_logo);
        triphala.setDetailedDescription("Triphala is a cornerstone of Ayurvedic medicine, consisting of three fruits that work synergistically to promote digestive health and detoxification.");
        triphala.setIngredients(Arrays.asList(
            "1 teaspoon Triphala powder",
            "1 cup warm water",
            "Optional: honey to taste"
        ));
        triphala.setPreparation(Arrays.asList(
            "Mix Triphala powder in warm water",
            "Stir well until completely dissolved",
            "Let it sit for 2-3 minutes",
            "Add honey if desired"
        ));
        triphala.setUsage("Consume before bed or on empty stomach in morning");
        triphala.setBenefits(Arrays.asList(
            "Promotes regular bowel movements",
            "Detoxifies the body naturally",
            "Improves nutrient absorption",
            "Supports healthy weight management",
            "Enhances eye health"
        ));
        triphala.setPrecautions(Arrays.asList(
            "Start with small doses",
            "Avoid during pregnancy",
            "May cause loose stools initially",
            "Consult doctor if taking medications"
        ));
        triphala.setDosage("1 teaspoon before bed");
        triphala.setDifficulty(1);
        triphala.setPreparationTime(5);
        triphala.setPopularity(85);
        remedies.add(triphala);

        // Immunity Remedies
        Remedy chyawanprash = new Remedy("4", "Chyawanprash Tonic", "immunity", "Immunity Boost",
            "Traditional herbal jam for immunity and vitality", R.drawable.ic_ayurveda_logo);
        chyawanprash.setDetailedDescription("Chyawanprash is a time-tested Ayurvedic formulation containing over 40 herbs, with Amla (Indian Gooseberry) as the base. It's known for boosting immunity and promoting overall health.");
        chyawanprash.setIngredients(Arrays.asList(
            "1-2 teaspoons Chyawanprash",
            "1 cup warm milk or water"
        ));
        chyawanprash.setPreparation(Arrays.asList(
            "Take 1-2 teaspoons of Chyawanprash",
            "Can be consumed directly or mixed with warm milk/water",
            "Best taken on empty stomach"
        ));
        chyawanprash.setUsage("Consume daily in the morning");
        chyawanprash.setBenefits(Arrays.asList(
            "Strengthens immune system",
            "Improves respiratory health",
            "Enhances memory and concentration",
            "Provides natural energy",
            "Rich in antioxidants"
        ));
        chyawanprash.setPrecautions(Arrays.asList(
            "Diabetics should choose sugar-free version",
            "Not recommended for children under 3",
            "May interact with certain medications"
        ));
        chyawanprash.setDosage("1-2 teaspoons daily");
        chyawanprash.setDifficulty(1);
        chyawanprash.setPreparationTime(2);
        chyawanprash.setPopularity(92);
        remedies.add(chyawanprash);

        Remedy tulsiTea = new Remedy("5", "Tulsi Holy Basil Tea", "immunity", "Immunity Boost",
            "Sacred herb tea for immunity and stress relief", R.drawable.ic_ayurveda_logo);
        tulsiTea.setDetailedDescription("Tulsi, or Holy Basil, is revered in Ayurveda as the 'Queen of Herbs.' This powerful adaptogen helps the body adapt to stress while boosting immune function.");
        tulsiTea.setIngredients(Arrays.asList(
            "10-12 fresh Tulsi leaves",
            "2 cups water",
            "1 teaspoon honey",
            "A few black peppercorns",
            "1/2 teaspoon grated ginger"
        ));
        tulsiTea.setPreparation(Arrays.asList(
            "Boil water in a pot",
            "Add Tulsi leaves, pepper, and ginger",
            "Simmer for 5-7 minutes",
            "Strain into cups",
            "Add honey when slightly cooled"
        ));
        tulsiTea.setUsage("Drink 2-3 times daily");
        tulsiTea.setBenefits(Arrays.asList(
            "Boosts immune system naturally",
            "Reduces stress and anxiety",
            "Has antimicrobial properties",
            "Supports respiratory health",
            "Promotes mental clarity"
        ));
        tulsiTea.setPrecautions(Arrays.asList(
            "May lower blood sugar levels",
            "Avoid during pregnancy in large amounts",
            "May affect fertility - consult doctor if trying to conceive"
        ));
        tulsiTea.setDosage("2-3 cups daily");
        tulsiTea.setDifficulty(1);
        tulsiTea.setPreparationTime(10);
        tulsiTea.setPopularity(88);
        remedies.add(tulsiTea);

        // Respiratory Remedies
        Remedy steamInhalation = new Remedy("6", "Eucalyptus Steam Therapy", "respiratory", "Respiratory Care",
            "Effective steam inhalation for congestion relief", R.drawable.ic_ayurveda_logo);
        steamInhalation.setDetailedDescription("Steam inhalation with essential oils is a traditional Ayurvedic practice that opens airways, relieves congestion, and promotes easier breathing.");
        steamInhalation.setIngredients(Arrays.asList(
            "4-5 cups boiling water",
            "5-6 drops eucalyptus oil",
            "2-3 drops peppermint oil (optional)",
            "Large bowl",
            "Towel"
        ));
        steamInhalation.setPreparation(Arrays.asList(
            "Boil water and pour into a large bowl",
            "Add essential oils to the hot water",
            "Place bowl on a stable surface",
            "Drape towel over your head and bowl",
            "Keep face 8-10 inches from water",
            "Breathe deeply for 10-15 minutes"
        ));
        steamInhalation.setUsage("Perform 2-3 times daily when congested");
        steamInhalation.setBenefits(Arrays.asList(
            "Clears nasal congestion",
            "Relieves sinus pressure",
            "Loosens mucus",
            "Soothes irritated airways",
            "Promotes easier breathing"
        ));
        steamInhalation.setPrecautions(Arrays.asList(
            "Be careful with hot water to avoid burns",
            "Keep eyes closed during therapy",
            "Not suitable for young children",
            "Asthmatics should consult doctor first"
        ));
        steamInhalation.setDosage("10-15 minutes, 2-3 times daily");
        steamInhalation.setDifficulty(2);
        steamInhalation.setPreparationTime(5);
        steamInhalation.setPopularity(82);
        remedies.add(steamInhalation);

        // Skin Care Remedies
        Remedy neemFacePack = new Remedy("7", "Neem & Turmeric Face Pack", "skin", "Skin & Beauty",
            "Purifying face pack for clear, glowing skin", R.drawable.ic_ayurveda_logo);
        neemFacePack.setDetailedDescription("Neem and turmeric have been used in Ayurvedic skincare for thousands of years. This powerful combination fights acne, reduces inflammation, and promotes a natural glow.");
        neemFacePack.setIngredients(Arrays.asList(
            "2 tablespoons neem powder",
            "1 teaspoon turmeric powder",
            "2 tablespoons yogurt or rose water",
            "1 teaspoon honey"
        ));
        neemFacePack.setPreparation(Arrays.asList(
            "Mix neem and turmeric powder in a bowl",
            "Add yogurt or rose water gradually",
            "Add honey and mix to form smooth paste",
            "Adjust consistency with more liquid if needed"
        ));
        neemFacePack.setUsage("Apply to clean face, leave for 15-20 minutes, rinse with lukewarm water");
        neemFacePack.setBenefits(Arrays.asList(
            "Treats acne and prevents breakouts",
            "Reduces skin inflammation",
            "Evens out skin tone",
            "Provides natural glow",
            "Has antibacterial properties"
        ));
        neemFacePack.setPrecautions(Arrays.asList(
            "Do patch test before first use",
            "May temporarily tint skin yellow",
            "Avoid if allergic to any ingredient",
            "Keep away from eyes"
        ));
        neemFacePack.setDosage("Apply 2-3 times per week");
        neemFacePack.setDifficulty(1);
        neemFacePack.setPreparationTime(5);
        neemFacePack.setPopularity(87);
        remedies.add(neemFacePack);

        Remedy roseWaterToner = new Remedy("8", "Rose Water Facial Toner", "skin", "Skin & Beauty",
            "Natural toner for refreshed and balanced skin", R.drawable.ic_ayurveda_logo);
        roseWaterToner.setDetailedDescription("Rose water is a gentle, natural toner that has been used in Ayurvedic beauty rituals for centuries. It balances skin pH, hydrates, and provides a refreshing glow.");
        roseWaterToner.setIngredients(Arrays.asList(
            "Fresh rose petals (pesticide-free)",
            "Distilled water",
            "Optional: 2-3 drops glycerin"
        ));
        roseWaterToner.setPreparation(Arrays.asList(
            "Rinse rose petals thoroughly",
            "Place petals in a pot with distilled water",
            "Simmer on low heat until petals lose color",
            "Let cool and strain",
            "Add glycerin if desired",
            "Store in clean bottle"
        ));
        roseWaterToner.setUsage("Apply to face with cotton pad twice daily");
        roseWaterToner.setBenefits(Arrays.asList(
            "Balances skin pH naturally",
            "Tightens pores",
            "Hydrates and refreshes skin",
            "Reduces redness and irritation",
            "Provides anti-aging benefits"
        ));
        roseWaterToner.setPrecautions(Arrays.asList(
            "Use pesticide-free roses only",
            "Store in refrigerator for longevity",
            "Use within 2 weeks",
            "Do patch test if sensitive skin"
        ));
        roseWaterToner.setDosage("Apply twice daily");
        roseWaterToner.setDifficulty(2);
        roseWaterToner.setPreparationTime(30);
        roseWaterToner.setPopularity(84);
        remedies.add(roseWaterToner);

        // Stress Relief Remedies
        Remedy ashwagandhaMilk = new Remedy("9", "Ashwagandha Moon Milk", "stress", "Stress Relief",
            "Calming bedtime drink for stress and better sleep", R.drawable.ic_ayurveda_logo);
        ashwagandhaMilk.setDetailedDescription("Ashwagandha is a powerful adaptogen that helps the body manage stress. This soothing nighttime beverage promotes relaxation and restful sleep.");
        ashwagandhaMilk.setIngredients(Arrays.asList(
            "1 cup warm milk",
            "1/2 teaspoon Ashwagandha powder",
            "1/4 teaspoon cardamom powder",
            "1 teaspoon honey",
            "Pinch of nutmeg"
        ));
        ashwagandhaMilk.setPreparation(Arrays.asList(
            "Warm milk in a saucepan",
            "Add Ashwagandha and cardamom",
            "Whisk well to combine",
            "Simmer for 2-3 minutes",
            "Remove from heat",
            "Add honey and nutmeg"
        ));
        ashwagandhaMilk.setUsage("Drink warm 30 minutes before bedtime");
        ashwagandhaMilk.setBenefits(Arrays.asList(
            "Reduces stress and anxiety",
            "Promotes restful sleep",
            "Balances cortisol levels",
            "Improves mental clarity",
            "Supports overall wellness"
        ));
        ashwagandhaMilk.setPrecautions(Arrays.asList(
            "Avoid during pregnancy",
            "May interact with thyroid medications",
            "Start with smaller doses",
            "Consult doctor if on sedatives"
        ));
        ashwagandhaMilk.setDosage("One cup before bed");
        ashwagandhaMilk.setDifficulty(1);
        ashwagandhaMilk.setPreparationTime(10);
        ashwagandhaMilk.setPopularity(91);
        remedies.add(ashwagandhaMilk);

        // Pain Relief Remedies
        Remedy gingerPaste = new Remedy("10", "Ginger Compress for Joint Pain", "pain", "Pain Relief",
            "Warming compress to relieve joint and muscle pain", R.drawable.ic_ayurveda_logo);
        gingerPaste.setDetailedDescription("Ginger has natural anti-inflammatory properties that make it effective for pain relief. This warm compress provides targeted relief for sore joints and muscles.");
        gingerPaste.setIngredients(Arrays.asList(
            "2 tablespoons fresh grated ginger",
            "1 tablespoon turmeric powder",
            "2 tablespoons mustard oil or coconut oil",
            "Clean cloth for compress"
        ));
        gingerPaste.setPreparation(Arrays.asList(
            "Mix grated ginger with turmeric",
            "Heat oil until warm (not hot)",
            "Add ginger-turmeric mix to oil",
            "Apply mixture to cloth",
            "Apply compress to affected area"
        ));
        gingerPaste.setUsage("Apply to painful areas for 15-20 minutes");
        gingerPaste.setBenefits(Arrays.asList(
            "Reduces inflammation",
            "Relieves joint and muscle pain",
            "Improves circulation",
            "Provides warming relief",
            "Natural pain management"
        ));
        gingerPaste.setPrecautions(Arrays.asList(
            "Test temperature before applying",
            "Avoid on broken skin",
            "May cause slight redness (normal)",
            "Wash hands after application"
        ));
        gingerPaste.setDosage("Apply 2-3 times daily as needed");
        gingerPaste.setDifficulty(2);
        gingerPaste.setPreparationTime(15);
        gingerPaste.setPopularity(79);
        remedies.add(gingerPaste);

        // NEW: Additional 10 remedies
        Remedy amargosSyrup = new Remedy("11", "Amla Immunity Syrup", "immunity", "Immunity Boost",
            "Vitamin C rich syrup for daily immunity", R.drawable.ic_ayurveda_logo);
        amargosSyrup.setDetailedDescription("Amla (Indian Gooseberry) is one of the richest natural sources of Vitamin C. This homemade syrup is a delicious way to boost immunity daily.");
        amargosSyrup.setIngredients(Arrays.asList(
            "500g fresh amla",
            "250g jaggery or honey",
            "1 teaspoon black salt",
            "1 teaspoon roasted cumin powder"
        ));
        amargosSyrup.setPreparation(Arrays.asList(
            "Deseed and chop amla",
            "Boil with minimal water until soft",
            "Mash and strain through cloth",
            "Add jaggery and simmer until syrupy",
            "Add spices and cool",
            "Store in clean bottle"
        ));
        amargosSyrup.setUsage("Take 2 teaspoons daily in morning");
        amargosSyrup.setBenefits(Arrays.asList(
            "Rich source of Vitamin C",
            "Strengthens immunity",
            "Improves digestion",
            "Enhances skin health",
            "Natural antioxidant"
        ));
        amargosSyrup.setPrecautions(Arrays.asList(
            "Store in refrigerator",
            "Use within 2-3 months",
            "Diabetics use sugar-free version"
        ));
        amargosSyrup.setDosage("2 teaspoons daily");
        amargosSyrup.setDifficulty(2);
        amargosSyrup.setPreparationTime(45);
        amargosSyrup.setPopularity(86);
        remedies.add(amargosSyrup);

        Remedy cucumberFaceMask = new Remedy("12", "Cucumber Aloe Cooling Mask", "skin", "Skin & Beauty",
            "Hydrating mask for summer skin care", R.drawable.ic_ayurveda_logo);
        cucumberFaceMask.setDetailedDescription("This cooling face mask combines cucumber and aloe vera to soothe sun-damaged skin and provide intense hydration.");
        cucumberFaceMask.setIngredients(Arrays.asList(
            "1/2 cucumber (blended)",
            "2 tablespoons aloe vera gel",
            "1 tablespoon yogurt",
            "Few drops lemon juice"
        ));
        cucumberFaceMask.setPreparation(Arrays.asList(
            "Blend cucumber to smooth paste",
            "Mix with aloe vera gel",
            "Add yogurt and lemon juice",
            "Mix well to smooth consistency"
        ));
        cucumberFaceMask.setUsage("Apply to face for 20 minutes, rinse with cool water");
        cucumberFaceMask.setBenefits(Arrays.asList(
            "Cools and soothes skin",
            "Reduces tan and pigmentation",
            "Hydrates deeply",
            "Tightens pores",
            "Refreshes tired skin"
        ));
        cucumberFaceMask.setPrecautions(Arrays.asList(
            "Use fresh ingredients",
            "Avoid if allergic to cucumber",
            "Keep away from eyes"
        ));
        cucumberFaceMask.setDosage("Apply 2-3 times weekly");
        cucumberFaceMask.setDifficulty(1);
        cucumberFaceMask.setPreparationTime(10);
        cucumberFaceMask.setPopularity(83);
        remedies.add(cucumberFaceMask);

        Remedy ajwainWater = new Remedy("13", "Ajwain (Carom) Seed Water", "digestive", "Digestive Health",
            "Traditional remedy for indigestion and bloating", R.drawable.ic_ayurveda_logo);
        ajwainWater.setDetailedDescription("Ajwain or carom seeds are known for their carminative properties. This simple water helps relieve gas, bloating, and indigestion instantly.");
        ajwainWater.setIngredients(Arrays.asList(
            "1 teaspoon ajwain seeds",
            "2 cups water",
            "Pinch of black salt"
        ));
        ajwainWater.setPreparation(Arrays.asList(
            "Boil water in a pan",
            "Add ajwain seeds",
            "Simmer for 5 minutes",
            "Strain and add black salt"
        ));
        ajwainWater.setUsage("Drink warm after meals");
        ajwainWater.setBenefits(Arrays.asList(
            "Relieves gas and bloating",
            "Improves digestion",
            "Reduces acidity",
            "Helps with indigestion",
            "Boosts metabolism"
        ));
        ajwainWater.setPrecautions(Arrays.asList(
            "Avoid excessive consumption",
            "Pregnant women consult doctor",
            "May increase body heat"
        ));
        ajwainWater.setDosage("1 cup after meals");
        ajwainWater.setDifficulty(1);
        ajwainWater.setPreparationTime(10);
        ajwainWater.setPopularity(81);
        remedies.add(ajwainWater);

        Remedy brahmiTea = new Remedy("14", "Brahmi Memory Tea", "stress", "Stress Relief",
            "Herbal tea for mental clarity and focus", R.drawable.ic_ayurveda_logo);
        brahmiTea.setDetailedDescription("Brahmi is renowned in Ayurveda as a brain tonic. This tea enhances memory, reduces mental fatigue, and promotes calm focus.");
        brahmiTea.setIngredients(Arrays.asList(
            "1 teaspoon dried brahmi leaves",
            "2 cups water",
            "1 teaspoon honey",
            "Few drops lemon juice"
        ));
        brahmiTea.setPreparation(Arrays.asList(
            "Boil water",
            "Add brahmi leaves",
            "Simmer for 7-10 minutes",
            "Strain and add honey",
            "Add lemon juice"
        ));
        brahmiTea.setUsage("Drink 1-2 cups daily");
        brahmiTea.setBenefits(Arrays.asList(
            "Enhances memory and concentration",
            "Reduces anxiety",
            "Improves cognitive function",
            "Promotes mental clarity",
            "Supports brain health"
        ));
        brahmiTea.setPrecautions(Arrays.asList(
            "Avoid during pregnancy",
            "May cause drowsiness initially",
            "Start with small doses"
        ));
        brahmiTea.setDosage("1-2 cups daily");
        brahmiTea.setDifficulty(1);
        brahmiTea.setPreparationTime(15);
        brahmiTea.setPopularity(85);
        remedies.add(brahmiTea);

        Remedy honeyCinnamonPaste = new Remedy("15", "Honey Cinnamon Throat Soother", "respiratory", "Respiratory Care",
            "Natural remedy for sore throat and cough", R.drawable.ic_ayurveda_logo);
        honeyCinnamonPaste.setDetailedDescription("This simple yet powerful combination of honey and cinnamon provides quick relief from sore throat and helps suppress cough naturally.");
        honeyCinnamonPaste.setIngredients(Arrays.asList(
            "2 tablespoons raw honey",
            "1 teaspoon cinnamon powder",
            "Optional: pinch of black pepper"
        ));
        honeyCinnamonPaste.setPreparation(Arrays.asList(
            "Mix honey and cinnamon in a bowl",
            "Add black pepper if desired",
            "Mix to form smooth paste"
        ));
        honeyCinnamonPaste.setUsage("Take 1 teaspoon 3-4 times daily");
        honeyCinnamonPaste.setBenefits(Arrays.asList(
            "Soothes sore throat",
            "Suppresses cough naturally",
            "Antimicrobial properties",
            "Reduces throat inflammation",
            "Provides quick relief"
        ));
        honeyCinnamonPaste.setPrecautions(Arrays.asList(
            "Not for children under 1 year",
            "Diabetics use in moderation",
            "Use raw, unprocessed honey"
        ));
        honeyCinnamonPaste.setDosage("1 teaspoon 3-4 times daily");
        honeyCinnamonPaste.setDifficulty(1);
        honeyCinnamonPaste.setPreparationTime(3);
        honeyCinnamonPaste.setPopularity(88);
        remedies.add(honeyCinnamonPaste);

        Remedy sesameOilMassage = new Remedy("16", "Warm Sesame Oil Abhyanga", "pain", "Pain Relief",
            "Traditional self-massage for muscle and joint relief", R.drawable.ic_ayurveda_logo);
        sesameOilMassage.setDetailedDescription("Abhyanga is a traditional Ayurvedic self-massage using warm oil. It nourishes the body, relieves pain, and promotes deep relaxation.");
        sesameOilMassage.setIngredients(Arrays.asList(
            "1/4 cup sesame oil",
            "Optional: 5 drops lavender essential oil"
        ));
        sesameOilMassage.setPreparation(Arrays.asList(
            "Warm oil in double boiler",
            "Test temperature on wrist",
            "Add essential oil if using",
            "Apply with circular motions"
        ));
        sesameOilMassage.setUsage("Massage body for 15-20 minutes before bath");
        sesameOilMassage.setBenefits(Arrays.asList(
            "Relieves muscle and joint pain",
            "Improves circulation",
            "Nourishes skin",
            "Promotes relaxation",
            "Reduces stress"
        ));
        sesameOilMassage.setPrecautions(Arrays.asList(
            "Ensure oil is comfortably warm",
            "Avoid on broken skin",
            "Use old towels (oil may stain)",
            "Be careful on bathroom floor"
        ));
        sesameOilMassage.setDosage("2-3 times per week");
        sesameOilMassage.setDifficulty(1);
        sesameOilMassage.setPreparationTime(5);
        sesameOilMassage.setPopularity(80);
        remedies.add(sesameOilMassage);

        Remedy fennelDigestion = new Remedy("17", "Fennel Seed Digestive Aid", "digestive", "Digestive Health",
            "Post-meal digestive and mouth freshener", R.drawable.ic_ayurveda_logo);
        fennelDigestion.setDetailedDescription("Fennel seeds (saunf) are traditionally chewed after meals in India. They aid digestion, freshen breath, and provide instant relief from bloating.");
        fennelDigestion.setIngredients(Arrays.asList(
            "1 tablespoon fennel seeds",
            "1 tablespoon coriander seeds",
            "Optional: rock sugar"
        ));
        fennelDigestion.setPreparation(Arrays.asList(
            "Dry roast fennel and coriander seeds lightly",
            "Let cool completely",
            "Mix with rock sugar if desired",
            "Store in airtight container"
        ));
        fennelDigestion.setUsage("Chew 1 teaspoon after meals");
        fennelDigestion.setBenefits(Arrays.asList(
            "Aids digestion",
            "Freshens breath",
            "Reduces bloating",
            "Relieves acidity",
            "Improves appetite"
        ));
        fennelDigestion.setPrecautions(Arrays.asList(
            "Avoid excessive consumption",
            "May not suit everyone",
            "Store in cool, dry place"
        ));
        fennelDigestion.setDosage("1 teaspoon after meals");
        fennelDigestion.setDifficulty(1);
        fennelDigestion.setPreparationTime(5);
        fennelDigestion.setPopularity(90);
        remedies.add(fennelDigestion);

        Remedy sandalwoodFacePack = new Remedy("18", "Sandalwood Cooling Pack", "skin", "Skin & Beauty",
            "Luxurious face pack for radiant complexion", R.drawable.ic_ayurveda_logo);
        sandalwoodFacePack.setDetailedDescription("Sandalwood has been prized in Ayurvedic beauty treatments for centuries. This cooling pack brightens skin, reduces blemishes, and imparts a natural glow.");
        sandalwoodFacePack.setIngredients(Arrays.asList(
            "2 tablespoons sandalwood powder",
            "1 tablespoon rose water",
            "1 teaspoon honey",
            "Pinch of turmeric"
        ));
        sandalwoodFacePack.setPreparation(Arrays.asList(
            "Mix sandalwood powder with rose water",
            "Add honey and turmeric",
            "Mix to form smooth paste",
            "Adjust consistency as needed"
        ));
        sandalwoodFacePack.setUsage("Apply to face, let dry for 15 minutes, rinse");
        sandalwoodFacePack.setBenefits(Arrays.asList(
            "Brightens complexion",
            "Reduces blemishes and scars",
            "Cools and soothes skin",
            "Provides natural glow",
            "Anti-aging properties"
        ));
        sandalwoodFacePack.setPrecautions(Arrays.asList(
            "Use pure sandalwood powder",
            "Do patch test first",
            "Avoid if allergic"
        ));
        sandalwoodFacePack.setDosage("Apply 2 times per week");
        sandalwoodFacePack.setDifficulty(1);
        sandalwoodFacePack.setPreparationTime(5);
        sandalwoodFacePack.setPopularity(87);
        remedies.add(sandalwoodFacePack);

        Remedy licoriceTea = new Remedy("19", "Licorice Root Soothing Tea", "respiratory", "Respiratory Care",
            "Sweet herbal tea for throat and respiratory health", R.drawable.ic_ayurveda_logo);
        licoriceTea.setDetailedDescription("Licorice root (Mulethi) is a powerful herb for respiratory health. This naturally sweet tea soothes the throat and supports lung function.");
        licoriceTea.setIngredients(Arrays.asList(
            "1 teaspoon dried licorice root",
            "2 cups water",
            "1 teaspoon ginger (optional)",
            "Honey to taste"
        ));
        licoriceTea.setPreparation(Arrays.asList(
            "Crush licorice root slightly",
            "Boil water",
            "Add licorice and ginger",
            "Simmer for 10 minutes",
            "Strain and add honey"
        ));
        licoriceTea.setUsage("Drink 1-2 cups daily");
        licoriceTea.setBenefits(Arrays.asList(
            "Soothes sore throat",
            "Supports respiratory health",
            "Natural expectorant",
            "Reduces cough",
            "Boosts immunity"
        ));
        licoriceTea.setPrecautions(Arrays.asList(
            "Avoid if you have high blood pressure",
            "Not for long-term use",
            "Pregnant women should avoid",
            "May interact with medications"
        ));
        licoriceTea.setDosage("1-2 cups daily, maximum 2 weeks");
        licoriceTea.setDifficulty(1);
        licoriceTea.setPreparationTime(15);
        licoriceTea.setPopularity(82);
        remedies.add(licoriceTea);

        Remedy jatamansiOil = new Remedy("20", "Jatamansi Calming Oil", "stress", "Stress Relief",
            "Aromatic oil for stress relief and hair growth", R.drawable.ic_ayurveda_logo);
        jatamansiOil.setDetailedDescription("Jatamansi (Spikenard) is a rare Himalayan herb known for its calming properties. This infused oil can be used for scalp massage or aromatherapy.");
        jatamansiOil.setIngredients(Arrays.asList(
            "2 tablespoons dried jatamansi root",
            "1 cup coconut or sesame oil",
            "Optional: lavender essential oil"
        ));
        jatamansiOil.setPreparation(Arrays.asList(
            "Crush jatamansi root",
            "Heat oil gently",
            "Add jatamansi and simmer 15 minutes",
            "Cool and strain",
            "Add essential oil if desired",
            "Store in dark bottle"
        ));
        jatamansiOil.setUsage("Massage into scalp or use for aromatherapy");
        jatamansiOil.setBenefits(Arrays.asList(
            "Reduces stress and anxiety",
            "Promotes hair growth",
            "Improves sleep quality",
            "Calms nervous system",
            "Nourishes scalp"
        ));
        jatamansiOil.setPrecautions(Arrays.asList(
            "Do patch test first",
            "Avoid during pregnancy",
            "Store in cool place",
            "Use within 3 months"
        ));
        jatamansiOil.setDosage("Use 2-3 times weekly");
        jatamansiOil.setDifficulty(2);
        jatamansiOil.setPreparationTime(30);
        jatamansiOil.setPopularity(78);
        remedies.add(jatamansiOil);

        // Update category remedy counts
        updateCategoryRemedyCounts();
    }

    private void updateCategoryRemedyCounts() {
        for (Category category : categories) {
            int count = 0;
            for (Remedy remedy : remedies) {
                if (remedy.getCategoryId().equals(category.getId())) {
                    count++;
                }
            }
            category.setRemedyCount(count);
        }
    }

    public List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    public List<Remedy> getAllRemedies() {
        return new ArrayList<>(remedies);
    }

    public List<Remedy> getRemediesByCategory(String categoryId) {
        List<Remedy> filtered = new ArrayList<>();
        for (Remedy remedy : remedies) {
            if (remedy.getCategoryId().equals(categoryId)) {
                filtered.add(remedy);
            }
        }
        return filtered;
    }

    public List<Remedy> getPopularRemedies(int limit) {
        List<Remedy> sorted = new ArrayList<>(remedies);
        sorted.sort((r1, r2) -> Integer.compare(r2.getPopularity(), r1.getPopularity()));
        return sorted.subList(0, Math.min(limit, sorted.size()));
    }

    public List<Remedy> searchRemedies(String query) {
        List<Remedy> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Remedy remedy : remedies) {
            // Search in name
            if (remedy.getName().toLowerCase().contains(lowerQuery)) {
                results.add(remedy);
                continue;
            }
            // Search in description
            if (remedy.getShortDescription().toLowerCase().contains(lowerQuery)) {
                results.add(remedy);
                continue;
            }
            // Search in category
            if (remedy.getCategoryName().toLowerCase().contains(lowerQuery)) {
                results.add(remedy);
                continue;
            }
            // Search in ingredients
            if (remedy.getIngredients() != null) {
                for (String ingredient : remedy.getIngredients()) {
                    if (ingredient.toLowerCase().contains(lowerQuery)) {
                        results.add(remedy);
                        break;
                    }
                }
            }
        }
        return results;
    }

    public Remedy getRemedyById(String id) {
        for (Remedy remedy : remedies) {
            if (remedy.getId().equals(id)) {
                return remedy;
            }
        }
        return null;
    }
    
    // Filter remedies by difficulty
    public List<Remedy> filterByDifficulty(List<Remedy> remedies, int maxDifficulty) {
        List<Remedy> filtered = new ArrayList<>();
        for (Remedy remedy : remedies) {
            if (remedy.getDifficulty() <= maxDifficulty) {
                filtered.add(remedy);
            }
        }
        return filtered;
    }
    
    // Filter remedies by preparation time
    public List<Remedy> filterByPrepTime(List<Remedy> remedies, int maxTime) {
        List<Remedy> filtered = new ArrayList<>();
        for (Remedy remedy : remedies) {
            if (remedy.getPreparationTime() <= maxTime) {
                filtered.add(remedy);
            }
        }
        return filtered;
    }
    
    // Get remedies for current season
    public List<Remedy> getSeasonalRemedies() {
        // Simple implementation - can be enhanced
        int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        
        // Winter months (Nov-Feb): Focus on immunity and warming remedies
        if (month >= 10 || month <= 1) {
            List<Remedy> seasonal = new ArrayList<>();
            for (Remedy remedy : remedies) {
                if (remedy.getCategoryId().equals("immunity") || 
                    remedy.getCategoryId().equals("respiratory")) {
                    seasonal.add(remedy);
                }
            }
            return seasonal;
        }
        
        // Summer months (Mar-Jun): Focus on cooling and skin care
        if (month >= 2 && month <= 5) {
            List<Remedy> seasonal = new ArrayList<>();
            for (Remedy remedy : remedies) {
                if (remedy.getCategoryId().equals("skin") || 
                    remedy.getCategoryId().equals("stress")) {
                    seasonal.add(remedy);
                }
            }
            return seasonal;
        }
        
        // Monsoon months (Jul-Oct): Focus on digestion and immunity
        List<Remedy> seasonal = new ArrayList<>();
        for (Remedy remedy : remedies) {
            if (remedy.getCategoryId().equals("digestive") || 
                remedy.getCategoryId().equals("immunity")) {
                seasonal.add(remedy);
            }
        }
        return seasonal;
    }
}
