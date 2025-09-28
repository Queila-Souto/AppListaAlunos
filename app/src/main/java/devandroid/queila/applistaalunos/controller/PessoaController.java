package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.api.AlunoApi;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.view.PessoaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaController {
    private SharedPreferences sharedPreferences;
    public static final String NOME_PREFERENCES = "pref_listaVip";
    private SharedPreferences.Editor listaVip;
    private List<Aluno> listaPessoas = new ArrayList<>();
    private AlunoApi pessoaApi = RetrofitClient.getRetrofitInstance().create(AlunoApi.class);
    private PessoaAdapter adapter = new PessoaAdapter(listaPessoas);


    public PessoaController(Context context) {
        sharedPreferences = context.getSharedPreferences(NOME_PREFERENCES, 0);
        listaVip = sharedPreferences.edit();
    }

    public void salvarLocalmente(Aluno pessoa) {
        listaVip.putString("Primeiro Nome", pessoa.getPrimeiroNome());
        listaVip.putString("Sobrenome", pessoa.getSobrenome());
        listaVip.putString("Curso", pessoa.getCurso());
        listaVip.putString("Telefone", pessoa.getTelefone());
        listaVip.apply();
    }

    public void limparLocal() {
        listaVip.clear();
        listaVip.apply();
    }

    public Aluno buscarLocalmente() {
        Aluno pessoa = new Aluno();
        pessoa.setPrimeiroNome(sharedPreferences.getString("Primeiro Nome", "-"));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome", "-"));
        pessoa.setCurso(sharedPreferences.getString("Curso", "-"));
        pessoa.setTelefone(sharedPreferences.getString("Telefone", "-"));

        return pessoa;
    }

    public void salvarBD(Aluno pessoa, PessoaCallBack pessoaCallBack) {
        Call<Aluno> call = pessoaApi.cadastrar(pessoa);
        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(@NonNull Call<Aluno> call, @NonNull Response<Aluno> response) {
                if (response.isSuccessful()) {
                    pessoaCallBack.onSuccess("Aluno salvo com sucesso");
                } else {
                    pessoaCallBack.onError("Não foi possível salvar o aluno");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Aluno> call, @NonNull Throwable t) {
                pessoaCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void listarAlunos(PessoaCallBack pessoaCallBack) {
        Call<List<Aluno>> call = pessoaApi.listarPessoas();
        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pessoaCallBack.onSuccess(response.body());

                } else {
                    pessoaCallBack.onError("erro ao tentar listar");
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
            }
        });
    }
}
