package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.PessoaCallBack;
import devandroid.queila.applistaalunos.controller.PessoaController;
import devandroid.queila.applistaalunos.model.Aluno;

public class ListagemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlunoAdapter adapter;
    PessoaController pessoaController;
    private List<Aluno> listaAlunos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlunoAdapter(listaAlunos);
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
            }

            @Override
            public void onSuccess(List<Aluno> pessoas) {

                listaAlunos.clear();
                listaAlunos.addAll(pessoas);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String mensagem) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}