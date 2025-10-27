package com.example.ayurveda_remedies;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileUtils {
    
    /**
     * Load profile picture and name into existing ImageView and TextView
     * @param activity The activity context
     * @param imageViewId Resource ID of the profile picture ImageView
     * @param textViewId Resource ID of the username TextView (can be 0 if not used)
     */
    public static void loadProfileIntoViews(Activity activity, int imageViewId, int textViewId) {
        SharedPreferences prefs = activity.getSharedPreferences("AyurvedaPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("user_name", "User");
        String profilePicBase64 = prefs.getString("profile_picture", null);
        
        // Load profile picture
        if (imageViewId != 0) {
            ImageView ivProfile = activity.findViewById(imageViewId);
            if (ivProfile != null) {
                if (profilePicBase64 != null && !profilePicBase64.isEmpty()) {
                    try {
                        byte[] decodedBytes = Base64.decode(profilePicBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        ivProfile.setImageBitmap(getCircularBitmap(bitmap));
                    } catch (Exception e) {
                        // If loading fails, show initials
                        ivProfile.setImageBitmap(createInitialsBitmap(activity, userName));
                    }
                } else {
                    // Show initials if no profile picture
                    ivProfile.setImageBitmap(createInitialsBitmap(activity, userName));
                }
            }
        }
        
        // Load username
        if (textViewId != 0) {
            TextView tvName = activity.findViewById(textViewId);
            if (tvName != null && !userName.isEmpty()) {
                tvName.setText(userName);
            }
        }
    }
    
    /**
     * Create circular bitmap from square bitmap
     */
    private static Bitmap getCircularBitmap(Bitmap bitmap) {
        int size = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, size, size);
        RectF rectF = new RectF(rect);
        
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawOval(rectF, paint);
        
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        
        return output;
    }
    
    /**
     * Create bitmap with user initials
     */
    private static Bitmap createInitialsBitmap(Context context, String name) {
        String initials = getInitials(name);
        int size = 120;
        
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        
        // Draw background circle
        Paint bgPaint = new Paint();
        bgPaint.setColor(context.getResources().getColor(R.color.primary_green, null));
        bgPaint.setAntiAlias(true);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, bgPaint);
        
        // Draw initials
        Paint textPaint = new Paint();
        textPaint.setColor(context.getResources().getColor(R.color.white, null));
        textPaint.setTextSize(size / 2.5f);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        
        float xPos = size / 2f;
        float yPos = (size / 2f) - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(initials, xPos, yPos, textPaint);
        
        return bitmap;
    }
    
    /**
     * Extract initials from name
     */
    private static String getInitials(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "U";
        }
        
        String[] parts = name.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        
        for (int i = 0; i < Math.min(parts.length, 2); i++) {
            if (!parts[i].isEmpty()) {
                initials.append(parts[i].charAt(0));
            }
        }
        
        return initials.toString().toUpperCase();
    }
}
