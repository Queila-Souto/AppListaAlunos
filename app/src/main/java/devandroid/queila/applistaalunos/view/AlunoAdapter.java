package devandroid.queila.applistaalunos.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.model.Aluno;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> lista;

    public AlunoAdapter(List<Aluno> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno pessoa = lista.get(position);
        holder.textNome.setText(pessoa.getPrimeiroNome() + " " + pessoa.getSobrenome());
        holder.textTelefone.setText(pessoa.getTelefone());
        holder.textCurso.setText(pessoa.getCurso());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textTelefone, textCurso;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.txtNome);
            textTelefone = itemView.findViewById(R.id.txtTelefone);
            textCurso= itemView.findViewById(R.id.txtCurso);
        }
    }
}
