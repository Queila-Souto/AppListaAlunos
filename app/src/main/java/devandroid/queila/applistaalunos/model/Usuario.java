package devandroid.queila.applistaalunos.model;

public class Usuario {
    private String Nome;
    private String Login;
    private String Senha;

    public Usuario() {}

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Nome='" + Nome + '\'' +
                ", Login='" + Login + '\'' +
                ", Senha='" + Senha + '\'' +
                '}';
    }
}
