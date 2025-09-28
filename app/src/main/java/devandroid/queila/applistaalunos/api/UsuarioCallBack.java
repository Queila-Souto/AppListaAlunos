package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Usuario;

public interface UsuarioCallBack {
    void onSuccess(String mensagem);
    void onError(String mensagem);
}
