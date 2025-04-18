package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.model.Pessoa;

public class MainActivity extends AppCompatActivity {
Pessoa pessoa;

EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;


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

        edittxtnome.setText(pessoa.getPrimeiroNome());
        edittxtsobrenome.setText(pessoa.getSobrenome());
        edittxtcurso.setText(pessoa.getCurso());
        edittxttelefone.setText(pessoa.getTelefone());

    }
}
