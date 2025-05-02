package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.PessoaApi;
import devandroid.queila.applistaalunos.api.RetrofitClient;
import devandroid.queila.applistaalunos.model.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
Pessoa pessoa;

EditText edittxtnome;
EditText edittxtsobrenome;
EditText edittxtcurso;
EditText edittxttelefone;
Button btnlimpar;
Button btnsalvar;
Button btnfinalizar;
SharedPreferences sharedPreferences;
public static final String NOME_PREFERENCES="pref_listaVip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pessoa = new Pessoa();
        sharedPreferences = getSharedPreferences(NOME_PREFERENCES, 0);
        SharedPreferences.Editor listaVip = sharedPreferences.edit();
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
                Toast.makeText(MainActivity.this, "Salvo" + pessoa.toString(), Toast.LENGTH_LONG).show();

                //salvando localmente
                listaVip.putString("Primeiro Nome", pessoa.getPrimeiroNome());
                listaVip.putString("Sobrenome", pessoa.getSobrenome());
                listaVip.putString("Curso", pessoa.getCurso());
                listaVip.putString("Telefone", pessoa.getTelefone());
                listaVip.apply();

                //salvando no banco
                PessoaApi pessoaApi = RetrofitClient.getRetrofitInstance().create(PessoaApi.class);
                Call<Pessoa> call = pessoaApi.cadastrar(pessoa);

                call.enqueue(new Callback<Pessoa>() {
                    @Override
                    public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Salvo na API com sucesso!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Erro ao salvar na API: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Pessoa> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Falha na chamada: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("conexão","Não foi possível conectar na api"+pessoa);
                    }
                });
            }
        });
    }}
