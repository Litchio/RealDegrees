package de.hskl.bitcoin.real_degrees.util;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class Utilities {
    public static int getLanguage() {
        String langstr = Locale.getDefault().getLanguage();
        int lang = 1;
        if (langstr.equals("en")) {
            lang = 2;
        }
        if (langstr.equals("fr")) {
            lang = 3;
        }
        if (langstr.equals("es")) {
            lang = 4;
        }
        if (langstr.equals("it")) {
            lang = 5;
        }
        return lang;
    }

    public static boolean isDarkModeOn(Context ctx) {
        int currentNightMode = ctx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) return false;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
