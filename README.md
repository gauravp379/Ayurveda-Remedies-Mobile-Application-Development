# 🌿 Ayurveda Remedies - Natural Healing App

A beautiful and comprehensive Android application featuring traditional Ayurvedic remedies for various health conditions.

## ✨ Features

### 🏠 Home Screen
- **Welcome Section** with tagline
- **Search Functionality** to find remedies quickly
- **Category Grid** with 6 main health categories
- **Popular Remedies** section showcasing top-rated treatments
- **Floating Action Button** for quick access to favorites

### 📚 Categories
1. **Digestive Health** - Natural remedies for digestion
2. **Respiratory Care** - Remedies for breathing and lungs
3. **Skin & Beauty** - Natural skincare solutions
4. **Immunity Boost** - Strengthen your immune system
5. **Stress Relief** - Mental wellness and relaxation
6. **Pain Relief** - Natural pain management

### 💊 Remedy Details
Each remedy includes:
- High-quality images
- Detailed description
- Complete ingredient list
- Step-by-step preparation instructions
- Health benefits
- Important precautions
- Recommended dosage
- Difficulty level
- Preparation time

### 🔍 Key Functionalities
- **Search** - Real-time search across all remedies
- **Categories** - Browse remedies by health condition
- **Favorites** - Save your preferred remedies
- **Share** - Share remedies with friends and family
- **Beautiful UI** - Material Design with natural color scheme


### Activities
- **SplashActivity** - Beautiful splash screen with app logo
- **MainActivity** - Home screen with categories and popular remedies
- **RemedyListActivity** - List of remedies by category
- **RemedyDetailActivity** - Detailed view of individual remedy
- **FavoritesActivity** - Saved favorite remedies

### Data Models
- **Category** - Health category information
- **Remedy** - Complete remedy details with all properties
- **RemedyRepository** - Singleton data source with 10 comprehensive remedies

### Adapters
- **CategoryAdapter** - Grid display of health categories
- **RemedyAdapter** - List display of remedies with images

## 🎨 Design Features

### Color Palette
- **Primary Green** (#4CAF50) - Natural healing theme
- **Accent Orange** (#FF9800) - Warmth and energy
- **Natural Earth Tones** - Sage, cream, brown
- **Category-specific Colors** - Each category has unique color

### UI Components
- Material Design 3
- CardView with elevation and rounded corners
- RecyclerView for smooth scrolling
- Collapsing Toolbar for detail screens
- Floating Action Button
- Custom icons and drawables

## 📖 Remedies Included

1. **Turmeric Golden Milk** - Immunity and digestion
2. **Ginger Lemon Tea** - Digestive aid
3. **Triphala Churna** - Complete digestive wellness
4. **Chyawanprash Tonic** - Immunity booster
5. **Tulsi Holy Basil Tea** - Immunity and stress relief
6. **Eucalyptus Steam Therapy** - Respiratory health
7. **Neem & Turmeric Face Pack** - Skin care
8. **Rose Water Toner** - Natural skin toner
9. **Ashwagandha Moon Milk** - Stress relief and sleep
10. **Ginger Compress** - Joint and muscle pain relief

## 🛠️ Technical Details

### Requirements
- **Minimum SDK**: API 30 (Android 11)
- **Target SDK**: API 36
- **Language**: Java
- **Build System**: Gradle with Kotlin DSL

### Dependencies
```gradle
// Core Android
implementation(libs.appcompat)
implementation(libs.material)
implementation(libs.activity)
implementation(libs.constraintlayout)

// RecyclerView and CardView
implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.cardview:cardview:1.0.0")

// ViewPager2
implementation("androidx.viewpager2:viewpager2:1.0.0")

// Glide for image loading
implementation("com.github.bumptech.glide:glide:4.16.0")

// Shared Preferences
implementation("androidx.preference:preference:1.2.1")
```

### Features Implementation
- **ViewBinding** enabled for type-safe view access
- **SharedPreferences** for persistent favorites
- **Serializable** for passing objects between activities
- **Material Components** for modern UI
- **Custom themes** for day mode

## 🚀 How to Build

1. Clone or open the project in Android Studio
2. Sync Gradle files
3. Run the app on an emulator (API 30+) or physical device
4. Explore Ayurvedic remedies!

## 📐 Project Structure

```
app/
├── src/
│   └── main/
│       ├── java/com/example/ayurveda_remedies/
│       │   ├── adapters/
│       │   │   ├── CategoryAdapter.java
│       │   │   └── RemedyAdapter.java
│       │   ├── data/
│       │   │   └── RemedyRepository.java
│       │   ├── models/
│       │   │   ├── Category.java
│       │   │   └── Remedy.java
│       │   ├── SplashActivity.java
│       │   ├── MainActivity.java
│       │   ├── RemedyListActivity.java
│       │   ├── RemedyDetailActivity.java
│       │   └── FavoritesActivity.java
│       └── res/
│           ├── drawable/ (Icons and backgrounds)
│           ├── layout/ (All XML layouts)
│           ├── values/
│           │   ├── colors.xml
│           │   ├── strings.xml
│           │   ├── themes.xml
│           │   └── dimens.xml
│           └── mipmap/ (App icons)
```

## 🎯 Future Enhancements

- [ ] Add more remedies (target: 50+)
- [ ] Implement dosage calculator
- [ ] Add remedy reminders
- [ ] Include video demonstrations
- [ ] Multi-language support
- [ ] Offline mode with local database
- [ ] User notes for remedies
- [ ] Integration with health tracking
- [ ] Community ratings and reviews

## ⚠️ Disclaimer

This app provides information about traditional Ayurvedic remedies for educational purposes only. Always consult with a qualified healthcare provider before trying any remedy, especially if you have existing health conditions or are taking medications.

## 📄 License

This project is created for educational purposes.

## 👨‍💻 Developer

Developed with Gaurav Pawar for learning Android development and promoting awareness of Ayurvedic wellness.

---

**Note**: The app uses placeholder images. In a production version, you would add high-quality images of herbs, remedies, and ingredients in the drawable folders.
#
