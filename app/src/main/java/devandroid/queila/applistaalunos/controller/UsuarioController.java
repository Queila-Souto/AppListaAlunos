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
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private UsuarioApi usuarioApi = RetrofitClient.getRetrofitInstance().create(UsuarioApi.class);

    public void salvarBD(Usuario usuario, UsuarioCallBack usuarioCallBack) {
        Call<Usuario> call = usuarioApi.cadastrar(usuario);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.e("conexão", "usuário salvo" + usuario);
                    usuarioCallBack.onSuccess("Usuário salvo com sucesso");
                } else {
                    Log.e("conexão", "Não foi possível conectar na api ->" + usuario);
                    usuarioCallBack.onError("Não foi possível salvar o usuário");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("conexão", "Não foi possível conectar na api -> " + usuario);
                usuarioCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }
}
