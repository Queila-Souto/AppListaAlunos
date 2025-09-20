package devandroid.queila.applistaalunos.controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.api.AlunoApi;
import devandroid.queila.applistaalunos.api.Auth;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.api.UsuarioApi;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import devandroid.queila.applistaalunos.model.Usuario;
import devandroid.queila.applistaalunos.view.PessoaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioController {
    private UsuarioApi usuarioApi = RetrofitClient.getRetrofitInstance().create(UsuarioApi.class);
    private String email;
    private String password;

    public void login(String email, String password, Context context) {
        Log.d("AUTH", "ACESSEI METODO LOGIN");

        LoginRequest loginRequest = new LoginRequest(email, password);
        Auth authApi = RetrofitClient.getRetrofitInstance().create(Auth.class);
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        authApi.login(loginRequest).enqueue(

        new Callback<LoginResponse>(){
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    Log.d("AUTH", "ACESSEI CALLBACK");

                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("AUTH", "RESPOSTA"+response);
                        String token = response.body().getToken();
                        Log.d("AUTH", "Token: " + token);
                        preferences.edit()
                                .putString("AUTH_TOKEN", token)
                                .apply();

                    }
                    else {
                        Log.d("AUTH", "EStou no else da resposta  -- "+response);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("AUTH", "Não foi possível recuperar o token" );
          }

    }
    );
    }
    public void salvarBD(Usuario usuario, UsuarioCallBack usuarioCallBack) {

        Call<Void> call = usuarioApi.cadastrar(usuario);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.e("response", "usuário salvo" + usuario);
                    usuarioCallBack.onSuccess("Usuário salvo com sucesso");
                } else {
                    Log.e("response", "Não foi possível conectar na api ->" + usuario);
                    usuarioCallBack.onError("Não foi possível salvar o usuário");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("erro", "erro" + t);
                usuarioCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }
}
