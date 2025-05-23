package devandroid.queila.applistaalunos.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import devandroid.queila.applistaalunos.R;

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
        });
    }

    private void inicializarObjetos() {
        editTextEmail = findViewById(R.id.editTextLogin);
        editTextSenha = findViewById(R.id.editTextPassword);
        buttonLogar = findViewById(R.id.imageView3);
        buttonNovoUsuario = findViewById(R.id.textView2);

    }
}

