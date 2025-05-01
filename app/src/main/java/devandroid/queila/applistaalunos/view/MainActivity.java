package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.model.Pessoa;

public class MainActivity extends AppCompatActivity {
Pessoa pessoa;

EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;
Button btnlimpar;
Button btnsalvar;
Button btnfinalizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pessoa=new Pessoa();
        pessoa.setPrimeiroNome("Queila");
        pessoa.setSobrenome("Souto");
        pessoa.setCurso("Informática");
        pessoa.setTelefone("(12)996744079");

        edittxtnome = findViewById(R.id.editTextText2);
        edittxtsobrenome = findViewById(R.id.editTextText3);
        edittxtcurso = findViewById(R.id.editTextText4);
        edittxttelefone = findViewById(R.id.editTextText5);
        btnlimpar = findViewById(R.id.buttonLimpar);
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
            }
        });
        btnfinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Até logo!",Toast.LENGTH_LONG).show();
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

                Toast.makeText(MainActivity.this, "Salvo"+pessoa.toString(),Toast.LENGTH_LONG).show();

            }
        });




    }
}
