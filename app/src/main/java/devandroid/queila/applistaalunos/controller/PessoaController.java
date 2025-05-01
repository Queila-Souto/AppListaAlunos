package devandroid.queila.applistaalunos.controller;

import android.util.Log;

import devandroid.queila.applistaalunos.model.Pessoa;

public class PessoaController {
    public void salvar(Pessoa pessoa) {
        Log.d("controller","Pessoa está no controller"+pessoa.toString());
    }
}
