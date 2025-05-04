package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Pessoa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PessoaApi {
    @POST("pessoas")
    Call<Pessoa> cadastrar(@Body Pessoa pessoa);

    @GET("pessoas")
    Call<List<Pessoa>> listarPessoas();
}
