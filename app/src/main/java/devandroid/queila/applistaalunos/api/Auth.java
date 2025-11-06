package devandroid.queila.applistaalunos.api;

import devandroid.queila.applistaalunos.model.GoogleLoginRequest;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

// Em devandroid.queila.applistaalunos.api.Auth.java

public interface Auth {
    @POST("auth/login") // Substitua pelo seu endpoint de login padrão
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/google") // Substitua pelo seu endpoint de login com Google
    Call<LoginResponse> loginComGoogle(@Body GoogleLoginRequest googleLoginRequest);
}

