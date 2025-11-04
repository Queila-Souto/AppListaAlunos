package devandroid.queila.applistaalunos.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.UsuarioCallBack;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.model.Usuario;

public class CadastroUsuario extends AppCompatActivity {
    Button buttonVoltar;
    ImageView buttonCadastrar;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario);
        incializarObjetos();
        configurarBotoes();

        }

    private void configurarBotoes() {
        buttonVoltar.setOnClickListener(v->{
            finish();
        });
        buttonCadastrar.setOnClickListener(v->{
            String nome = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            String senha = editTextPassword.getText().toString();
            String confirmarSenha = editTextConfirmPassword.getText().toString();
            validarCampos(nome, email, senha, confirmarSenha);
            Usuario usuario = new Usuario(nome, email, senha);
            UsuarioController usuarioController = new UsuarioController();
            usuarioController.salvarBD(usuario, this, new UsuarioCallBack() {
                @Override
                public void onSuccess(String mensagem) {
                    Toast.makeText(CadastroUsuario.this, mensagem, Toast.LENGTH_LONG).show();
                    limparCampos();
                    finish();

                }

                @Override
                public void onError(String mensagem) {
                    Toast.makeText(CadastroUsuario.this, mensagem, Toast.LENGTH_LONG).show();
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

    private void validarCampos(String nome, String email, String senha, String confirmarSenha) {
        if (email.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isSenhaForte(senha)) {
            Toast.makeText(this, "A senha deve conter no mínimo 8 caracteres, com letras maiúsculas, minúsculas, número e símbolo.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private boolean isSenhaForte(String senha) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return senha.matches(regex);
    }

    private void incializarObjetos() {
        buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonCadastrar = findViewById(R.id.imageView3);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

    }
}
