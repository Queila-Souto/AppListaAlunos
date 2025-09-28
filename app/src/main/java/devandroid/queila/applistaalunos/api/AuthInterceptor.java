package devandroid.queila.applistaalunos.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;

    public AuthInterceptor(Context context) {
        sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = sharedPreferences.getString("AUTH_TOKEN", null);
        Request originalRequest = chain.request();
        if (token == null) {
            return chain.proceed(originalRequest); // Continua sem o token
        }
        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
        return chain.proceed(newRequest);
    }
}
