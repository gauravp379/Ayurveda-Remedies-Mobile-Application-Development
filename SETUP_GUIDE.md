# 🚀 Ayurveda Remedies - Setup Guide

## ✅ What's Already Done

The complete app has been created with:
- ✅ All Java source files (5 activities, 2 adapters, 2 models, 1 repository)
- ✅ All XML layouts (10+ files)
- ✅ All resources (colors, strings, themes, dimensions)
- ✅ All drawable resources (icons, backgrounds)
- ✅ Gradle configuration with dependencies
- ✅ AndroidManifest.xml properly configured

## 📱 How to Run

### Method 1: Using Android Studio (Recommended)

1. **Open Android Studio**
2. **Open Project**
   - Click "Open" or "Open an Existing Project"
   - Navigate to: `D:\SEM 5\Ayurveda_Remedies`
   - Click "OK"

3. **Wait for Gradle Sync**
   - Android Studio will automatically sync Gradle
   - This may take a few minutes on first run
   - Wait for "Gradle sync finished" message

4. **Set Up Emulator** (if you don't have one)
   - Click on "Device Manager" (phone icon in toolbar)
   - Click "Create Virtual Device"
   - Select any phone (e.g., Pixel 5)
   - Select API Level 30 or higher (Android 11+)
   - Click "Finish"

5. **Run the App**
   - Click the green "Run" button (▶) in toolbar
   - Or press `Shift + F10`
   - Select your emulator or connected device
   - Wait for build and installation

6. **Enjoy!** 🎉
   - App will launch with splash screen
   - Explore remedies, categories, and features

### Method 2: Build from Command Line

```powershell
# Navigate to project directory
cd "D:\SEM 5\Ayurveda_Remedies"

# Build the APK
.\gradlew.bat assembleDebug

# Install to connected device
.\gradlew.bat installDebug
```

## 🔧 Common Issues & Solutions

### Issue 1: Gradle Sync Failed
**Solution:**
- Check internet connection (Gradle needs to download dependencies)
- In Android Studio: File → Invalidate Caches / Restart
- Try: File → Sync Project with Gradle Files

### Issue 2: SDK Not Found
**Solution:**
- Go to: File → Project Structure → SDK Location
- Set Android SDK location (usually: `C:\Users\YourName\AppData\Local\Android\Sdk`)
- Click "Apply"

### Issue 3: Java Version Error
**Solution:**
- The project uses Java 11
- In Android Studio: File → Settings → Build, Execution, Deployment → Build Tools → Gradle
- Set "Gradle JDK" to Java 11 or higher

### Issue 4: Build Tools Not Found
**Solution:**
- Open SDK Manager: Tools → SDK Manager
- Go to "SDK Tools" tab
- Check and install:
  - Android SDK Build-Tools 34
  - Android SDK Platform-Tools
  - Android SDK Tools
- Click "Apply"

### Issue 5: Minimum SDK Error
**Solution:**
- The app requires API 30+ (Android 11)
- If your device/emulator is older, create a new emulator with API 30+
- Or modify `minSdk` in `app/build.gradle.kts` (may require code changes)

## 📦 Dependencies Check

The app uses these libraries (auto-downloaded by Gradle):
- AndroidX AppCompat
- Material Components
- RecyclerView & CardView
- ConstraintLayout
- ViewPager2
- Glide (image loading)
- Preferences

All are standard and should download automatically.

## 🎯 Expected Build Time

- **First Build**: 2-5 minutes (downloading dependencies)
- **Subsequent Builds**: 30-60 seconds
- **Incremental Builds**: 10-20 seconds

## ✅ Verification Checklist

After opening in Android Studio, verify:
- [ ] No red underlines in Java files
- [ ] Gradle sync completed successfully
- [ ] "Build" menu → "Make Project" succeeds
- [ ] Device/Emulator is selected in toolbar
- [ ] Green Run button is enabled

## 🎨 Testing the App

Once running, test these features:
1. **Splash Screen** - Should show for 2.5 seconds
2. **Home Screen** - See categories and popular remedies
3. **Search** - Type "ginger" or "turmeric"
4. **Categories** - Tap any category
5. **Remedy Details** - Tap any remedy card
6. **Favorites** - Tap heart icon on remedies
7. **Share** - Share button on detail screen
8. **Navigation** - Back buttons should work

## 📱 Minimum Requirements

- **Android Studio**: Arctic Fox or newer
- **Gradle**: 8.0+ (included)
- **Java**: JDK 11 or higher
- **Android SDK**: API 30 (Android 11) or higher
- **RAM**: 8GB recommended
- **Disk Space**: 4GB free

## 🐛 Still Having Issues?

1. **Clean and Rebuild**
   - Build → Clean Project
   - Build → Rebuild Project

2. **Check Gradle Files**
   - Ensure no manual modifications broke syntax
   - Compare with original if needed

3. **Android Studio Version**
   - Update to latest stable version
   - File → Check for Updates

4. **Fresh Start**
   - Close Android Studio
   - Delete `.gradle` and `.idea` folders
   - Reopen project in Android Studio

## 📞 Error Messages

If you see specific error messages, they usually indicate:

- **"Cannot resolve symbol"** → Gradle sync needed
- **"SDK not found"** → Set SDK location in settings
- **"Manifest merger failed"** → Check AndroidManifest.xml syntax
- **"Resource not found"** → Check XML resource files

## 🎉 Success Indicators

App is working when you see:
- ✅ Green splash screen with leaf logo
- ✅ Home screen with 6 colorful categories
- ✅ Search bar at top
- ✅ List of remedies below
- ✅ Orange heart button (FAB) at bottom right

## 📚 Next Steps

Once running successfully:
1. Explore all 10 remedies
2. Try favorites feature
3. Test search functionality
4. Share a remedy
5. Browse by categories

Happy coding! 🌿
