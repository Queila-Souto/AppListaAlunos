package devandroid.queila.applistaalunos.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.api.PessoaApi;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.model.Pessoa;
import devandroid.queila.applistaalunos.view.MainActivity;
import devandroid.queila.applistaalunos.view.PessoaAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaController {
    private SharedPreferences sharedPreferences;
    public static final String NOME_PREFERENCES = "pref_listaVip";
    private SharedPreferences.Editor listaVip;
    private List<Pessoa> listaPessoas = new ArrayList<>();
    private PessoaApi pessoaApi = RetrofitClient.getRetrofitInstance().create(PessoaApi.class);
    private PessoaAdapter adapter = new PessoaAdapter(listaPessoas);


    public PessoaController(Context context) {
        sharedPreferences = context.getSharedPreferences(NOME_PREFERENCES, 0);
        listaVip = sharedPreferences.edit();
    }

    public void salvarLocalmente(Pessoa pessoa) {
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

    public Pessoa buscarLocalmente() {
        Pessoa pessoa = new Pessoa();
        pessoa.setPrimeiroNome(sharedPreferences.getString("Primeiro Nome", "-"));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome", "-"));
        pessoa.setCurso(sharedPreferences.getString("Curso", "-"));
        pessoa.setTelefone(sharedPreferences.getString("Telefone", "-"));

        return pessoa;
    }

    public void salvarBD(Pessoa pessoa, PessoaCallBack pessoaCallBack) {
        Call<Pessoa> call = pessoaApi.cadastrar(pessoa);
        call.enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(@NonNull Call<Pessoa> call, @NonNull Response<Pessoa> response) {
                if (response.isSuccessful()) {
                    Log.e("conexão", "salvo" + pessoa);
                    pessoaCallBack.onSuccess("Aluno salvo com sucesso");
                } else {
                    Log.e("conexão", "Não foi possível conectar na api" + pessoa);
                    pessoaCallBack.onError("Não foi possível salvar o aluno");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Pessoa> call, @NonNull Throwable t) {
                Log.e("conexão", "Não foi possível conectar na api" + pessoa);
                pessoaCallBack.onError("Serviço indisponível. Tente mais tarde.");
            }
        });
    }

    public void listarAlunos(PessoaCallBack pessoaCallBack) {
        Call<List<Pessoa>> call = pessoaApi.listarPessoas();
        call.enqueue(new Callback<List<Pessoa>>() {
            @Override
            public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pessoaCallBack.onSuccess(response.body());
//                    listaPessoas.clear();
//                    listaPessoas.addAll(response.body());
//                    adapter.notifyDataSetChanged();
//                    Log.e("conexão", "lista criada" + listaPessoas.toString());
                } else {
//                    Log.e("conexão", "erro ao tentar listar");
                    pessoaCallBack.onError("erro ao tentar listar");
                }
            }

            @Override
            public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                Log.e("conexão", "erro ao conectar API");
            }
        });
    }
}
