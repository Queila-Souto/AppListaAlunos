package devandroid.queila.applistaalunos.api;

import devandroid.queila.applistaalunos.model.Pessoa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PessoaApi {
    @POST("pessoas")
    Call<Pessoa> cadastrar(@Body Pessoa pessoa);
}
