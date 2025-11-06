// No pacote devandroid.queila.applistaalunos.util
package devandroid.queila.applistaalunos.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    private static final String PREF_NAME = "APP_PREFS";
    private static final String KEY_AUTH_TOKEN = "auth_token";

    // Salva o token no SharedPreferences
    public static void saveToken(Context context, String token) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    // Lê o token do SharedPreferences
    public static String getToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // Retorna null se o token não for encontrado
        return prefs.getString(KEY_AUTH_TOKEN, null);
    }

    // Limpa o token (usado para logout)
    public static void clearToken(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_AUTH_TOKEN).apply();
    }
}
