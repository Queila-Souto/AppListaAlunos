package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.controller.PessoaController;
import devandroid.queila.applistaalunos.model.Pessoa;
import devandroid.queila.applistaalunos.util.PessoaValidador;
import devandroid.queila.applistaalunos.util.TelefoneMascara;

public class MainActivity extends AppCompatActivity {

Pessoa pessoa;
PessoaController pessoaController;
EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;
Button btnlimpar;
Button btnRecuperar;
Button btnsalvar;
Button btnfinalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarObjetos();
        configurarBotoes();
    }

    private void configurarBotoes() {
        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Até logo!", Toast.LENGTH_LONG).show();
                finish();
            }
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
                }

                @Override
                public void onError(String mensagem) {
                    Toast.makeText(MainActivity.this, mensagem, Toast.LENGTH_LONG).show();
                }
            });
        });
        btnlimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittxtnome.setText("");
                edittxtsobrenome.setText("");
                edittxtcurso.setText("");
                edittxttelefone.setText("");
                pessoaController.limpar();
            }
        });
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa = pessoaController.buscarLocalmente();
                edittxtnome.setText(pessoa.getPrimeiroNome());
                edittxtsobrenome.setText(pessoa.getSobrenome());
                edittxtcurso.setText(pessoa.getCurso());
                edittxttelefone.setText(pessoa.getTelefone());
                Toast.makeText(MainActivity.this, "Dados recuperados",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean camposPreenchidos() {
        return !edittxtnome.getText().toString().isEmpty()
                && !edittxtsobrenome.getText().toString().isEmpty()
                && !edittxttelefone.getText().toString().isEmpty()
                && !edittxtcurso.getText().toString().isEmpty();
    }

    private void inicializarObjetos() {
        pessoa = new Pessoa();
        pessoaController = new PessoaController(MainActivity.this);
        edittxtnome = findViewById(R.id.editTextText2);
        edittxtsobrenome = findViewById(R.id.editTextText3);
        edittxtcurso = findViewById(R.id.editTextText4);
        edittxttelefone = findViewById(R.id.editTextText5);
        edittxttelefone.addTextChangedListener(TelefoneMascara.insert(edittxttelefone));
        btnlimpar = findViewById(R.id.buttonLimpar);
        btnRecuperar = findViewById(R.id.buttonRecuperar);
        btnsalvar = findViewById(R.id.buttonSalvar);
        btnfinalizar = findViewById(R.id.buttonFinalizar);
    }
}
