package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.api.AlunoApi;
import devandroid.queila.applistaalunos.api.AlunoCallBack;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.model.Aluno;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlunoController {
    private SharedPreferences sharedPreferences;
    public static final String NOME_PREFERENCES = "pref_listaVip";
    private SharedPreferences.Editor listaVip;
    private List<Aluno> listaPessoas = new ArrayList<>();
    private AlunoApi alunoApi;



    public AlunoController(Context context) {
        sharedPreferences = context.getSharedPreferences(NOME_PREFERENCES, 0);
        listaVip = sharedPreferences.edit();
        alunoApi = RetrofitClient.getRetrofitInstance(context).create(AlunoApi.class);
    }

    public void salvarLocalmente(Aluno aluno) {
        listaVip.putString("Primeiro Nome", aluno.getPrimeiroNome());
        listaVip.putString("Sobrenome", aluno.getSobrenome());
        listaVip.putString("Curso", aluno.getCurso());
        listaVip.putString("Telefone", aluno.getTelefone());
        listaVip.apply();
    }

    public void limparLocal() {
        listaVip.clear();
        listaVip.apply();
    }

    public void salvarBD(Aluno pessoa, AlunoCallBack alunoCallBack) {
        Call<Aluno> call = alunoApi.cadastrar(pessoa);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(@NonNull Call<Aluno> call, @NonNull Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Aluno alunoSalvo = response.body();
                    Log.d("Aluno", "ID gerado: " + alunoSalvo.getId());
                    alunoCallBack.onSuccess("Aluno salvo com sucesso");
                } else {
                    alunoCallBack.onError("Não foi possível salvar o aluno");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Aluno> call, @NonNull Throwable t) {
                alunoCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void listarAlunos(AlunoCallBack alunoCallBack) {
        Call<List<Aluno>> call = alunoApi.listarPessoas();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    alunoCallBack.onSuccess(response.body());

                } else {
                    alunoCallBack.onError("erro ao tentar listar");
                    Log.e("Erro", "Erro ao listar pessoas"+response);
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
            }
        });
    }
    public void removerAluno(Long id, AlunoCallBack alunoCallBack) {
        Call<Void> call = alunoApi.removerAluno(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    // 204 No Content → sucesso
                    alunoCallBack.onSuccess("Aluno removido com sucesso!");
                    Log.d("RemoverAluno", "Aluno ID " + id + " removido com sucesso.");
                } else if (response.code() == 404) {
                    alunoCallBack.onError("Aluno não encontrado.");
                    Log.e("RemoverAluno", "Aluno ID " + id + " não encontrado (404).");
                } else {
                    alunoCallBack.onError("Erro ao tentar remover o aluno.");
                    Log.e("RemoverAluno", "Falha ao remover aluno: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                alunoCallBack.onError("Falha de conexão com o servidor.");
                Log.e("RemoverAluno", "Erro de conexão: " + t.getMessage());
            }
        });
    }

}
