package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.controller.PessoaController;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.util.PessoaValidador;
import devandroid.queila.applistaalunos.util.TelefoneMascara;

public class MainActivity extends AppCompatActivity {

Aluno pessoa;
PessoaController pessoaController;
Toolbar toolbar;
EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;
Button btnlimpar;
Button btnRecuperar;
Button btnsalvar;
Button btnlistar;
Button btnfinalizar;
Button btncadastrarusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarObjetos();
        configurarBotoes();
    }

    private void configurarBotoes() {
        btnfinalizar.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Até logo!", Toast.LENGTH_LONG).show();
            finish();
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
            pessoaController.salvarLocalmente(pessoa);
            pessoaController.salvarBD(pessoa, new PessoaCallBack() {

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
            pessoaController.limparLocal();
        });

        btnRecuperar.setOnClickListener(v -> {
            pessoa = pessoaController.buscarLocalmente();
            edittxtnome.setText(pessoa.getPrimeiroNome());
            edittxtsobrenome.setText(pessoa.getSobrenome());
            edittxtcurso.setText(pessoa.getCurso());
            edittxttelefone.setText(pessoa.getTelefone());
            Toast.makeText(MainActivity.this, "Dados recuperados",Toast.LENGTH_LONG).show();
        });
        btnlistar.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
        btncadastrarusuario.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, CadastroUsuario.class);
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
        pessoa = new Aluno();
        pessoaController = new PessoaController(MainActivity.this);
        edittxtnome = findViewById(R.id.editTextText2);
        edittxtsobrenome = findViewById(R.id.editTextText3);
        edittxtcurso = findViewById(R.id.editTextText4);
        edittxttelefone = findViewById(R.id.editTextText5);
        edittxttelefone.addTextChangedListener(TelefoneMascara.insert(edittxttelefone));
        btnlimpar = findViewById(R.id.buttonLimpar);
        btnRecuperar = findViewById(R.id.buttonRecuperar);
        btnsalvar = findViewById(R.id.buttonSalvar);
        btnlistar = findViewById(R.id.buttonListar);
        btnfinalizar = findViewById(R.id.buttonFinalizar);
        btncadastrarusuario = findViewById(R.id.buttonCadastrarUsuario);
    }
}
