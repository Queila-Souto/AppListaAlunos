package devandroid.queila.applistaalunos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listagem);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}