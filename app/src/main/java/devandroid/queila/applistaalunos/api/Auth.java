package devandroid.queila.applistaalunos.api;

import devandroid.queila.applistaalunos.model.GoogleLoginRequest;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Auth {
    @POST("usuario/login") // Substitua pelo seu endpoint de login padrão
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/google") // Substitua pelo seu endpoint de login com Google
    Call<LoginResponse> loginComGoogle(@Body GoogleLoginRequest googleLoginRequest);
}

