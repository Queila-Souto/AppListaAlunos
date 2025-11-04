package devandroid.queila.applistaalunos.api;

import devandroid.queila.applistaalunos.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UsuarioApi {
    @POST("/usuario/create")
    @Headers("Content-Type: application/json")
    Call<Void> cadastrar(@Body Usuario usuario);


}
