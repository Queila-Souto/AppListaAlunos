package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.controller.PessoaController;
import devandroid.queila.applistaalunos.model.Pessoa;

public class MainActivity extends AppCompatActivity {
Pessoa pessoa;

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
        pessoa = new Pessoa();
        PessoaController controller = new PessoaController(MainActivity.this);

        edittxtnome = findViewById(R.id.editTextText2);
        edittxtsobrenome = findViewById(R.id.editTextText3);
        edittxtcurso = findViewById(R.id.editTextText4);
        edittxttelefone = findViewById(R.id.editTextText5);
        btnlimpar = findViewById(R.id.buttonLimpar);
        btnRecuperar = findViewById(R.id.buttonRecuperar);
        btnsalvar = findViewById(R.id.buttonSalvar);
        btnfinalizar = findViewById(R.id.buttonFinalizar);

        edittxtnome.setText(pessoa.getPrimeiroNome());
        edittxtsobrenome.setText(pessoa.getSobrenome());
        edittxtcurso.setText(pessoa.getCurso());
        edittxttelefone.setText(pessoa.getTelefone());

        btnlimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittxtnome.setText("");
                edittxtsobrenome.setText("");
                edittxtcurso.setText("");
                edittxttelefone.setText("");
                controller.limpar();
            }
        });
        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa = controller.buscarLocalmente();
                edittxtnome.setText(pessoa.getPrimeiroNome());
                edittxtsobrenome.setText(pessoa.getSobrenome());
                edittxtcurso.setText(pessoa.getCurso());
                edittxttelefone.setText(pessoa.getTelefone());
                Toast.makeText(MainActivity.this, "Dados recuperados",Toast.LENGTH_LONG).show();
            }
        });
        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Até logo!", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btnsalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pessoa.setPrimeiroNome(edittxtnome.getText().toString());
                pessoa.setSobrenome(edittxtsobrenome.getText().toString());
                pessoa.setTelefone(edittxttelefone.getText().toString());
                pessoa.setCurso(edittxtcurso.getText().toString());

                controller.salvarLocalmente(pessoa);
                controller.salvarBD(pessoa);

            }
        });
    }}
