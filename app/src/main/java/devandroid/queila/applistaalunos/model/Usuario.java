package devandroid.queila.applistaalunos.model;

public class Usuario {

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    private String nome;
    private String email;
    private String senha;

    public Usuario() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Nome='" + nome + '\'' +
                ", Login='" + email + '\'' +
                ", Senha='" + senha + '\'' +
                '}';
    }
}
