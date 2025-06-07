package devandroid.queila.applistaalunos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import devandroid.queila.applistaalunos.R;

public class CadastroUsuario extends AppCompatActivity {
    Button buttonVoltar;
    ImageView buttonCadastrar;
    EditText editTextName;
    EditText editTextLogin;
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
            String login = editTextLogin.getText().toString();
            String senha = editTextPassword.getText().toString();
            String confirmarSenha = editTextConfirmPassword.getText().toString();
            validarCampos(nome, login, senha, confirmarSenha);
        });


    }

    private void validarCampos(String nome, String login, String senha, String confirmarSenha) {
        if (login.isEmpty() || login.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
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
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

    }
}
