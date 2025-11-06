package devandroid.queila.applistaalunos.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.controller.AuthCallBack;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.util.GoogleAuthHelper;

public class Login extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextSenha;
    ImageButton buttonLogar;
    Button googleSignInButton;
    TextView buttonNovoUsuario;
    private UsuarioController usuarioController;
    private GoogleAuthHelper googleAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarComponentes();
        inicializarControllers();
        configurarBotoes();
    }

    private void inicializarComponentes() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextPassword);
        buttonLogar = findViewById(R.id.imageView3);
        buttonNovoUsuario = findViewById(R.id.textView2);
        googleSignInButton = findViewById(R.id.button2);
    }

    private void inicializarControllers() {
        usuarioController = new UsuarioController();
        String serverClientId = getString(R.string.googleClientId);
        googleAuthHelper = new GoogleAuthHelper(this, serverClientId);
    }

    private void configurarBotoes() {
        // --- Botão de Cadastro de Usuário ---
        buttonNovoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CadastroUsuario.class);
            startActivity(intent);
        });

        // --- Botão de Login com Email e Senha ---
        buttonLogar.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String senha = editTextSenha.getText().toString().trim();
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            usuarioController.login(this, email, senha, new AuthCallBack() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                    navegarParaTelaPrincipal();
                }
                @Override
                public void onError(String message) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // --- Botão de Login com Google ---
        googleSignInButton.setOnClickListener(v -> usuarioController.loginComGoogle(this, googleAuthHelper, new AuthCallBack() {
            @Override
            public void onSuccess(String jwtToken) {
                Log.d("LOGIN_GOOGLE", "Login com Google via API bem-sucedido. Token: " + jwtToken);
                Toast.makeText(Login.this, "Login com Google realizado com sucesso!", Toast.LENGTH_SHORT).show();
                navegarParaTelaPrincipal();
            }

            @Override
            public void onError(String message) {
                Log.e("LOGIN_GOOGLE", "Erro no fluxo de login com Google: " + message);
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (googleAuthHelper != null) {
            googleAuthHelper.handleResult(requestCode, data);
        }
    }

    private void navegarParaTelaPrincipal() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
