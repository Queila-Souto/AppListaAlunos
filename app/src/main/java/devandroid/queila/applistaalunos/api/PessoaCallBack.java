package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Aluno;

public interface PessoaCallBack {
    void onSuccess(String mensagem);
    void onSuccess(List<Aluno> pessoas);
    void onError(String mensagem);
}
