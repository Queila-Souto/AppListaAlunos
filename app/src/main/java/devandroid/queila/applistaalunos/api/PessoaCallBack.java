package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Pessoa;

public interface PessoaCallBack {
    void onSuccess(String mensagem);
    void onSuccess(List<Pessoa> pessoas);
    void onError(String mensagem);
}
