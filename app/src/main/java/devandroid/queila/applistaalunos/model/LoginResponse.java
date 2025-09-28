package devandroid.queila.applistaalunos.model;

public class LoginResponse {
    public String getToken() {
        return token;
    }

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

}
