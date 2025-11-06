package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import devandroid.queila.applistaalunos.api.Auth;
import devandroid.queila.applistaalunos.api.RetrofitClient; // Importação necessária
import devandroid.queila.applistaalunos.api.UsuarioApi;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.model.GoogleLoginRequest;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import devandroid.queila.applistaalunos.model.Usuario;
import devandroid.queila.applistaalunos.util.GoogleAuthHelper;
import devandroid.queila.applistaalunos.view.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioController {


    public void login(Context context, String email, String password, AuthCallBack authCallBack) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Auth apiService = RetrofitClient.getRetrofitInstance(context).create(Auth.class);
        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    salvarJwt(context, response.body().getToken()); // Usando o método unificado
                    authCallBack.onSuccess("Login realizado com sucesso");
                } else {
                    authCallBack.onError("Não é possível realizar o login. Verifique as credenciais");
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.e("Login", "Falha na API de login: " + t.getMessage(), t);
                authCallBack.onError("Erro de comunicação com o servidor.");
            }
        });
    }

    public void loginComGoogle(Context context, GoogleAuthHelper googleHelper, AuthCallBack callback) {
        googleHelper.signIn();
        googleHelper.setOnLoginListener(new GoogleAuthHelper.OnGoogleLoginListener() {
            @Override
            public void onSuccess(@NonNull String idToken) {
                enviarTokenParaApi(context, idToken, callback);
            }

            @Override
            public void onError(@NonNull Exception e) {
                callback.onError("Erro no login com Google: " + e.getMessage());
            }
        });
    }

    private void enviarTokenParaApi(Context context, String idToken, AuthCallBack callback) {
        GoogleLoginRequest request = new GoogleLoginRequest(idToken);
        Auth apiService = RetrofitClient.getRetrofitInstance(context).create(Auth.class);

        apiService.loginComGoogle(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jwt = response.body().getToken();
                    salvarJwt(context, jwt);
                    callback.onSuccess(jwt);
                } else {
                    callback.onError("Erro na API: código " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                callback.onError("Falha de rede: " + t.getMessage());
            }
        });
    }

    private void salvarJwt(Context context, String jwt) {
        context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)
                .edit()
                .putString("AUTH_TOKEN", jwt)
                .apply();
    }

    public void cadastrarUsuario(Usuario usuario, Context context, UsuarioCallBack usuarioCallBack) {
        UsuarioApi usuarioApi = RetrofitClient.getRetrofitInstance(context).create(UsuarioApi.class);

        Call<Void> call = usuarioApi.cadastrar(usuario);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    usuarioCallBack.onSuccess("Usuário cadastrado com sucesso");
                } else {
                    usuarioCallBack.onError("Não foi possível cadastrar o usuário");
                    Log.e("Erro", "Resposta"+response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                usuarioCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void logout(Context context, GoogleAuthHelper googleHelper) {
        // Passo A: Iniciar o logout do Google.
        googleHelper.signOut(task -> {
            SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
            preferences.edit().remove("AUTH_TOKEN").apply();
            Log.d("Logout", "Usuário desconectado. Token local removido e sessão do Google encerrada.");

        });
    }
}

