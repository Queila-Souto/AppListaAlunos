package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import devandroid.queila.applistaalunos.api.Auth;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.api.UsuarioApi;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.model.GoogleLoginRequest;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import devandroid.queila.applistaalunos.model.Usuario;
import devandroid.queila.applistaalunos.util.GoogleAuthHelper;
import devandroid.queila.applistaalunos.view.Login; // Adicionado para o redirecionamento no logout
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
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();

                    // --- CORREÇÃO: Lógica defensiva para evitar NullPointerException ---
                    String nome = "Usuário"; // Valor padrão
                    String emailUsuario = email;  // Use o email do login como fallback

                    // Verifique se o objeto 'usuario' e seus atributos não são nulos antes de usá-los
                    if (loginResponse.getUsuario() != null) {
                        if (loginResponse.getUsuario().getNome() != null) {
                            nome = loginResponse.getUsuario().getNome();
                        }
                        if (loginResponse.getUsuario().getEmail() != null) {
                            emailUsuario = loginResponse.getUsuario().getEmail();
                        }
                    }

                    salvarSessaoUsuario(context, token, nome, emailUsuario);
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
        // SUGESTÃO: Garanta que seu GoogleAuthHelper e a interface OnGoogleLoginListener
        // também retornem o nome e o email para otimizar o fluxo.
        googleHelper.setOnLoginListener(new GoogleAuthHelper.OnGoogleLoginListener() {
            @Override
            public void onSuccess(@NonNull String idToken, @Nullable String displayName, @Nullable String email) {
                // Passamos os dados obtidos do Google para o próximo passo, que os usará como fallback.
                enviarTokenParaApi(context, idToken, displayName, email, callback);
            }

            @Override
            public void onError(@NonNull Exception e) {
                callback.onError("Erro no login com Google: " + e.getMessage());
            }
        });
    }

    private void enviarTokenParaApi(Context context, String idToken, String googleDisplayName, String googleEmail, AuthCallBack callback) {
        GoogleLoginRequest request = new GoogleLoginRequest(idToken);
        Auth apiService = RetrofitClient.getRetrofitInstance(context).create(Auth.class);

        apiService.loginComGoogle(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jwt = response.body().getToken();

                    // --- CORREÇÃO: Lógica defensiva para o login com Google ---
                    // Use os dados do Google como valores padrão confiáveis.
                    String nomeFinal = (googleDisplayName != null) ? googleDisplayName : "Usuário";
                    String emailFinal = googleEmail;

                    // Se a sua API retornar dados do usuário, eles podem estar mais atualizados (ex: nome editado no seu sistema).
                    // Então, damos preferência a eles, se existirem.
                    if (response.body().getUsuario() != null) {
                        if (response.body().getUsuario().getNome() != null) {
                            nomeFinal = response.body().getUsuario().getNome();
                        }
                        if (response.body().getUsuario().getEmail() != null) {
                            emailFinal = response.body().getUsuario().getEmail();
                        }
                    }

                    salvarSessaoUsuario(context, jwt, nomeFinal, emailFinal);
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

    private void salvarSessaoUsuario(Context context, String token, String nome, String email) {
        SharedPreferences.Editor editor = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE).edit();

        editor.putString("AUTH_TOKEN", token);
        editor.putString("USER_NAME", nome);
        editor.putString("USER_EMAIL", email);

        editor.apply();

        Log.d("Auth", "Sessão do usuário salva com sucesso. Nome: " + nome);
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
                    Log.e("Erro", "Resposta" + response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                usuarioCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void logout(Context context, GoogleAuthHelper googleHelper) {
        googleHelper.signOut(task -> {
            SharedPreferences preferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
            // Limpa todos os dados da sessão do usuário
            preferences.edit()
                    .remove("AUTH_TOKEN")
                    .remove("USER_NAME")
                    .remove("USER_EMAIL")
                    .apply();

            Log.d("Logout", "Sessão do usuário local e do Google encerrada.");

            // Redireciona o usuário para a tela de login, limpando o histórico
            Intent intent = new Intent(context, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        });
    }
}
