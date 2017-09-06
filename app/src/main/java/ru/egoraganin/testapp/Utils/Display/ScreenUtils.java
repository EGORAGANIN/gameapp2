package ru.egoraganin.testapp.Utils.Display;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {

    public static String getDeviceResolution(Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;

        switch (density) {
            case DisplayMetrics.DENSITY_MEDIUM:
                return "MDPI 320x480";
            case DisplayMetrics.DENSITY_HIGH:
                return "HDPI 480x800";
            case DisplayMetrics.DENSITY_LOW:
                return "LDPI 200x320";
            case DisplayMetrics.DENSITY_XHIGH:
                return "XHDPI 720x1280";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "XXHDPI 960x1600";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI 1280x1920";
            default:
                return "Unknown density: " + density;
        }
    }

    public static int dpToPix(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(px);
    }

    public static int pxToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return Math.round(dp);
    }

    public static Display getDisplay(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay();
    }
}
