package devandroid.queila.applistaalunos.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.controller.AuthCallBack;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.model.LoginRequest;
import devandroid.queila.applistaalunos.util.GoogleAuthHelper;

public class Login extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextSenha;
    ImageButton buttonLogar;
    Button googleSignInButton;
    TextView buttonNovoUsuario;

    private GoogleSignInClient googleSignInClient;
    GoogleAuthHelper googleAuthHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarObjetos();
        configurarBotoes();
    }

    private void configurarBotoes() {
        buttonNovoUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, CadastroUsuario.class);
            startActivity(intent);
        });
        buttonLogar.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String senha = editTextSenha.getText().toString();
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            UsuarioController usuarioController = new UsuarioController();
            usuarioController.login(email, senha, this, new AuthCallBack() {
                @Override
                public void onSuccess(String message) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onError(String message) {
                    Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();

                }
            });
        });
        googleSignInButton.setOnClickListener(v -> {
            googleAuthHelper.signIn();

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == googleAuthHelper.getRC_SIGN_IN()) { // Usando a constante pública
            googleAuthHelper.handleResult(requestCode, data, new GoogleAuthHelper.OnGoogleLoginListener() {
                @Override
                public void onSuccess(@NonNull String idToken) {
                    // SUCESSO!
                    // Agora você pode enviar este 'idToken' para o seu backend para validação
                    // e para criar uma sessão de usuário no seu sistema.
                    Log.d("LoginActivity", "Token recebido: " + idToken);
                    // Ex: backendApi.loginWithGoogle(idToken);
                }

                @Override
                public void onError(@NonNull Exception e) {
                    // ERRO!
                    // Trate o erro, por exemplo, mostrando uma mensagem para o usuário.
                    Log.e("LoginActivity", "Erro no login com Google", e);
                    Toast.makeText(Login.this, "Falha no login com Google.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void inicializarObjetos() {
        googleAuthHelper= new GoogleAuthHelper(this, "10222241811-7cbs3gmi7g782nmdlb3ust46lgou8mod.apps.googleusercontent.com");
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextPassword);
        buttonLogar = findViewById(R.id.imageView3);
        buttonNovoUsuario = findViewById(R.id.textView2);
        googleSignInButton = findViewById(R.id.button2);

    }
}

