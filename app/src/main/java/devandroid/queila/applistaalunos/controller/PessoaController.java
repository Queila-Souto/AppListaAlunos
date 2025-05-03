package devandroid.queila.applistaalunos.controller;

import android.content.SharedPreferences;
import android.util.Log;
import devandroid.queila.applistaalunos.api.PessoaApi;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.model.Pessoa;
import devandroid.queila.applistaalunos.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoaController {
    SharedPreferences sharedPreferences;
    public static final String NOME_PREFERENCES="pref_listaVip";
    SharedPreferences.Editor listaVip;

    public PessoaController(MainActivity mainActivity) {
        sharedPreferences = mainActivity.getSharedPreferences(NOME_PREFERENCES, 0);
        listaVip = sharedPreferences.edit();
    }

    public void salvarLocalmente(Pessoa pessoa){
        listaVip.putString("Primeiro Nome", pessoa.getPrimeiroNome());
        listaVip.putString("Sobrenome", pessoa.getSobrenome());
        listaVip.putString("Curso", pessoa.getCurso());
        listaVip.putString("Telefone", pessoa.getTelefone());
        listaVip.apply();
    }
    public void limpar(){
        listaVip.clear();
        listaVip.apply();
    }
    public Pessoa buscarLocalmente(){
        Pessoa pessoa = new Pessoa();
        pessoa.setPrimeiroNome(sharedPreferences.getString("Primeiro Nome","-"));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome","-"));
        pessoa.setCurso(sharedPreferences.getString("Curso","-"));
        pessoa.setTelefone(sharedPreferences.getString("Telefone","-"));

    return pessoa;}
    public void finalizar(){}

    public void salvarBD(Pessoa pessoa) {
        PessoaApi pessoaApi = RetrofitClient.getRetrofitInstance().create(PessoaApi.class);
        Call<Pessoa> call = pessoaApi.cadastrar(pessoa);
        call.enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                if (response.isSuccessful()) {
                    Log.e("conexão","salvo"+pessoa);
                } else {
                    Log.e("conexão","Não foi possível conectar na api"+pessoa);
                }
            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {
                Log.e("conexão","Não foi possível conectar na api"+pessoa);
            }
        });
    }
}
