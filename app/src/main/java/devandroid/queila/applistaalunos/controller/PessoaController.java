package devandroid.queila.applistaalunos.controller;

import android.content.SharedPreferences;

import devandroid.queila.applistaalunos.model.Pessoa;
import devandroid.queila.applistaalunos.view.MainActivity;

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
    };
    public void limpar(){
        listaVip.clear();
        listaVip.apply();
    };
    public Pessoa buscarLocalmente(){
        Pessoa pessoa = new Pessoa();
        pessoa.setPrimeiroNome(sharedPreferences.getString("Primeiro Nome","-"));
        pessoa.setSobrenome(sharedPreferences.getString("Sobrenome","-"));
        pessoa.setCurso(sharedPreferences.getString("Curso","-"));
        pessoa.setTelefone(sharedPreferences.getString("Telefone","-"));

    return pessoa;}
    public void finalizar(){};

}
