package devandroid.queila.applistaalunos.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
