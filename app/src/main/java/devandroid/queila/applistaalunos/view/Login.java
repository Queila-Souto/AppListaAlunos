package devandroid.queila.applistaalunos.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.model.LoginRequest;

public class Login extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextSenha;
    ImageView buttonLogar;
    TextView buttonNovoUsuario;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializarObjetos();
        configurarBotoes();
    }

    private void configurarBotoes() {
        buttonNovoUsuario.setOnClickListener(v -> {
        });
        buttonLogar.setOnClickListener(v -> {
           Log.e("AUTH","APERTEI O BOTAO");
            String email = editTextEmail.getText().toString();
            String senha = editTextSenha.getText().toString();
            UsuarioController usuarioController = new UsuarioController();
            usuarioController.login(email,senha,this);
        });
    }

    private void inicializarObjetos() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextPassword);
        buttonLogar = findViewById(R.id.imageView3);
        buttonNovoUsuario = findViewById(R.id.textView2);

    }
}

