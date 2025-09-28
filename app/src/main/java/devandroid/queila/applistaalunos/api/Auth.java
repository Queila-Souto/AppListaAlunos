package devandroid.queila.applistaalunos.api;

import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Auth {
    @POST("/usuario/login")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}
