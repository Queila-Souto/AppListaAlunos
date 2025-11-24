package devandroid.queila.applistaalunos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.model.Usuario;
import devandroid.queila.applistaalunos.util.LoadingManager;

public class CadastroUsuario extends AppCompatActivity {

    private Button buttonVoltar;
    private ImageView buttonCadastrar;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);

        inicializarObjetos();
        configurarBotoes();
    }

    private void configurarBotoes() {

        buttonVoltar.setOnClickListener(v -> finish());

        buttonCadastrar.setOnClickListener(v -> {
            LoadingManager.show(this);

            String nome = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String senha = editTextPassword.getText().toString().trim();
            String confirmarSenha = editTextConfirmPassword.getText().toString().trim();

            String erro = validarCampos(nome, email, senha, confirmarSenha);

            if (erro != null) {
                Toast.makeText(this, erro, Toast.LENGTH_LONG).show();
                LoadingManager.hide();
                return;
            }

            Usuario usuario = new Usuario(nome, email, senha);
            UsuarioController usuarioController = new UsuarioController();

            usuarioController.cadastrarUsuario(usuario, this, new UsuarioCallBack() {
                @Override
                public void onSuccess(String mensagem) {
                    Toast.makeText(CadastroUsuario.this, mensagem, Toast.LENGTH_LONG).show();
                    limparCampos();
                    LoadingManager.hide();
                    finish();
                }

                @Override
                public void onError(String mensagem) {
                    Toast.makeText(CadastroUsuario.this, mensagem, Toast.LENGTH_LONG).show();
                    LoadingManager.hide();
                }
            });
        });
    }

    private void limparCampos() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");
    }

    /**
     * Retorna null se estiver tudo válido.
     * Retorna a mensagem de erro caso alguma validação falhe.
     */
    private String validarCampos(String nome, String email, String senha, String confirmarSenha) {

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            return "Preencha todos os campos";
        }

        if (!senha.equals(confirmarSenha)) {
            return "As senhas não coincidem";
        }

        if (!isSenhaForte(senha)) {
            return "A senha deve conter no mínimo 8 caracteres, com letras maiúsculas, minúsculas, número e símbolo.";
        }

        return null; // tudo OK
    }

    private boolean isSenhaForte(String senha) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return senha.matches(regex);
    }

    private void inicializarObjetos() {
        buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonCadastrar = findViewById(R.id.imageView3);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }
}
