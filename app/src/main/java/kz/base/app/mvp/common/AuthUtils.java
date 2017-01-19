package kz.base.app.mvp.common;

import com.pixplicity.easyprefs.library.Prefs;

public class AuthUtils {
    private static final String TOKEN = "token";

    public static String getToken() {
        return Prefs.getString(TOKEN, "");
    }

    public static void setToken(String token) {
        Prefs.putString(TOKEN, token);
    }
}
