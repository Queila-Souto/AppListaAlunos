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
    private String email;
    private String password;

    public void login(String email, String password, Context context, AuthCallBack authCallBack) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Auth authApi = RetrofitClient.getRetrofitInstance(context).create(Auth.class);
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", MODE_PRIVATE);
        authApi.login(loginRequest).enqueue(

        new Callback<LoginResponse>(){
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                    if (response.isSuccessful() && response.body() != null) {
                        String token = response.body().getToken();

                        preferences.edit()
                                .putString("AUTH_TOKEN", token)
                                .apply();
                        authCallBack.onSuccess("Login realizado com sucesso");

                    }
                    else {

                        authCallBack.onError("Não é possível realizar o login. Verifique as credenciais");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.e("Login", "Falha ao tentar se conectar à API de login: "+ t.getMessage() );
                    authCallBack.onError("Erro de comunicação com o servidor. Tente novamente mais tarde.");


                }

    }
    );
    }
    public void salvarBD(Usuario usuario,Context context, UsuarioCallBack usuarioCallBack) {
        UsuarioApi usuarioApi = RetrofitClient.getRetrofitInstance(context).create(UsuarioApi.class);

        Call<Void> call = usuarioApi.cadastrar(usuario);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    usuarioCallBack.onSuccess("Usuário salvo com sucesso");
                } else {
                    usuarioCallBack.onError("Não foi possível salvar o usuário");
                    Log.e("Erro", "Resposta"+response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                usuarioCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void logout(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        preferences.edit().remove("AUTH_TOKEN").apply(); // apaga o token
        Log.d("Logout", "Usuário desconectado. Token removido.");
    }
}
