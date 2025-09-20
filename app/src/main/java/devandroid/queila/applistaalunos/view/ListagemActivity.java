package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.controller.PessoaController;
import devandroid.queila.applistaalunos.model.Aluno;

public class ListagemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PessoaAdapter adapter;
    PessoaController pessoaController;
    private List<Aluno> listaAlunos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PessoaAdapter(listaAlunos);
        recyclerView.setAdapter(adapter);
        pessoaController = new PessoaController(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        carregarAlunos();
    }

    private void carregarAlunos() {
        pessoaController.listarAlunos(new PessoaCallBack() {
            @Override
            public void onSuccess(String mensagem) {
                Log.e("conexão", "lista criada - " + mensagem);
            }

            @Override
            public void onSuccess(List<Aluno> pessoas) {

                listaAlunos.clear();
                listaAlunos.addAll(pessoas);
                adapter.notifyDataSetChanged();
                Log.e("conexão", "lista criada " + listaAlunos.toString());

            }

            @Override
            public void onError(String mensagem) {
                Log.e("conexão", "erro ao conectar" + listaAlunos.toString());

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}