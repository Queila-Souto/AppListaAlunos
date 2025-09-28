package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AlunoApi {
    @POST("alunos")
    Call<Aluno> cadastrar(@Body Aluno pessoa);

    @GET("alunos")
    Call<List<Aluno>> listarPessoas();
}
