package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.AlunoCallBack;
import devandroid.queila.applistaalunos.controller.AlunoController;
import devandroid.queila.applistaalunos.controller.UsuarioController;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.util.GoogleAuthHelper;
import devandroid.queila.applistaalunos.util.PessoaValidador;
import devandroid.queila.applistaalunos.util.TelefoneMascara;

public class MainActivity extends AppCompatActivity {

Aluno pessoa;
AlunoController alunoController;

UsuarioController usuarioController;
EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;
Button btnlimpar;
Button btnsalvar;
Button btnlistar;
Button btnfinalizar;
TextView textViewNome;
TextView textViewEmail;
private GoogleAuthHelper googleAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarObjetos();
        recuperarUsuario();
        configurarBotoes();
    }

    private void recuperarUsuario() {
        SharedPreferences preferences = getSharedPreferences("APP_PREFS", MODE_PRIVATE);

        String nome = preferences.getString("USER_NAME", "Usuário");
        String email = preferences.getString("USER_EMAIL", "Email não encontrado");

        textViewEmail.setText(email);
        textViewNome.setText(nome);
    }

    private void configurarBotoes() {
        btnfinalizar.setOnClickListener(v -> {
            usuarioController.logout(MainActivity.this, googleAuthHelper);
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Usuário desconectado", Toast.LENGTH_SHORT).show();

        });
        btnsalvar.setOnClickListener(v -> {
            pessoa.setPrimeiroNome(edittxtnome.getText().toString());
            pessoa.setSobrenome(edittxtsobrenome.getText().toString());
            pessoa.setCurso(edittxtcurso.getText().toString());
            pessoa.setTelefone(edittxttelefone.getText().toString());

            if (!PessoaValidador.validarCamposObrigatorios(pessoa)) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                return;
            }

            if (!PessoaValidador.telefoneValido(pessoa.getTelefone())) {
                edittxttelefone.setError("Telefone inválido");
                return;
            }

            pessoa.setTelefone(TelefoneMascara.limpar(pessoa.getTelefone()));
            alunoController.salvarLocalmente(pessoa);
            alunoController.salvarBD(pessoa, new AlunoCallBack() {

                @Override
                public void onSuccess(String mensagem) {
                    Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
                    limparCampos();
                }

                @Override
                public void onSuccess(List<Aluno> pessoas) {

                }

                @Override
                public void onError(String mensagem) {
                    Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
                }
            });
        });

        btnlimpar.setOnClickListener(v -> {
            limparCampos();
            alunoController.limparLocal();
        });


        btnlistar.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ListagemActivity.class);
            startActivity(intent);
        });

    }

    private void limparCampos() {
        edittxtnome.setText("");
        edittxtsobrenome.setText("");
        edittxtcurso.setText("");
        edittxttelefone.setText("");
    }

    private void inicializarObjetos() {
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewNome = findViewById(R.id.textViewName);
        pessoa = new Aluno();
        String serverClientId = getString(R.string.googleClientId);
        googleAuthHelper = new GoogleAuthHelper(this, serverClientId);
        alunoController = new AlunoController(MainActivity.this);
        usuarioController = new UsuarioController();
        edittxtnome = findViewById(R.id.editTextText2);
        edittxtsobrenome = findViewById(R.id.editTextText3);
        edittxtcurso = findViewById(R.id.editTextText4);
        edittxttelefone = findViewById(R.id.editTextText5);
        edittxttelefone.addTextChangedListener(TelefoneMascara.insert(edittxttelefone));
        btnlimpar = findViewById(R.id.buttonLimpar);
        btnsalvar = findViewById(R.id.buttonSalvar);
        btnlistar = findViewById(R.id.buttonListar);
        btnfinalizar = findViewById(R.id.buttonFinalizar);
    }
}
