package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.api.AlunoApi;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.api.UsuarioApi;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.model.Usuario;
import devandroid.queila.applistaalunos.view.PessoaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioController {
    private UsuarioApi usuarioApi = RetrofitClient.getRetrofitInstance().create(UsuarioApi.class);

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
