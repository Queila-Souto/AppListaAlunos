package devandroid.queila.applistaalunos.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.AlunoCallBack;
import devandroid.queila.applistaalunos.controller.AlunoController;
import devandroid.queila.applistaalunos.model.Aluno;
import devandroid.queila.applistaalunos.util.LoadingManager;

public class ListagemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AlunoAdapter adapter;
    AlunoController alunoController;
    private List<Aluno> listaAlunos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem);
        LoadingManager.show(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AlunoAdapter(listaAlunos, this);
        recyclerView.setAdapter(adapter);
        alunoController = new AlunoController(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        carregarAlunos();
        LoadingManager.hide();

    }

    private void carregarAlunos() {

        alunoController.listarAlunos(new AlunoCallBack() {
            @Override
            public void onSuccess(String mensagem) {
                LoadingManager.hide();
            }

            @Override
            public void onSuccess(List<Aluno> pessoas) {
                LoadingManager.hide();
                listaAlunos.clear();
                listaAlunos.addAll(pessoas);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onError(String mensagem) {
            LoadingManager.hide();
            Toast.makeText(ListagemActivity.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}