package devandroid.queila.applistaalunos.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.model.Pessoa;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder> {

    private List<Pessoa> lista;

    public PessoaAdapter(List<Pessoa> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public PessoaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new PessoaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaViewHolder holder, int position) {
        Pessoa pessoa = lista.get(position);
        holder.textNome.setText(pessoa.getPrimeiroNome() + " " + pessoa.getSobrenome());
        holder.textTelefone.setText(pessoa.getTelefone());
        holder.textCurso.setText(pessoa.getCurso());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class PessoaViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textTelefone, textCurso;

        public PessoaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.txtNome);
            textTelefone = itemView.findViewById(R.id.txtTelefone);
            textCurso= itemView.findViewById(R.id.txtCurso);
        }
    }
}
