package devandroid.queila.applistaalunos.api;

import java.util.List;

import devandroid.queila.applistaalunos.model.Aluno;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlunoApi {
    @POST("/alunos/cadastro")
    Call<Aluno> cadastrar(@Body Aluno pessoa);

    @GET("/alunos/lista")
    Call<List<Aluno>> listarPessoas();

    @DELETE("/alunos/remover/{id}")
    Call<Void> removerAluno(@Path("id") Long id);
}


