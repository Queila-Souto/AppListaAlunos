package devandroid.queila.applistaalunos.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import devandroid.queila.applistaalunos.R;
import devandroid.queila.applistaalunos.api.AlunoCallBack;
import devandroid.queila.applistaalunos.controller.AlunoController;
import devandroid.queila.applistaalunos.model.Aluno;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private final Context context;
    private List<Aluno> lista;

    public AlunoAdapter(List<Aluno> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new AlunoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Aluno aluno = lista.get(position);
        holder.textNome.setText(aluno.getPrimeiroNome() + " " + aluno.getSobrenome());
        holder.textTelefone.setText(aluno.getTelefone());
        holder.textCurso.setText(aluno.getCurso());
        holder.buttonRemover.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Remover aluno")
                    .setMessage("Tem certeza que deseja excluir este aluno?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        AlunoController alunoController = new AlunoController(context);
                        alunoController.removerAluno(aluno.getId(), new AlunoCallBack() {

                            @Override
                            public void onSuccess(String mensagem) {
                                Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
                                lista.remove(position);
                                notifyItemRemoved(position);
                            }

                            @Override
                            public void onSuccess(List<Aluno> pessoas) {

                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView textNome, textTelefone, textCurso;
        ImageButton buttonRemover;

        public AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome = itemView.findViewById(R.id.txtNome);
            textTelefone = itemView.findViewById(R.id.txtTelefone);
            textCurso= itemView.findViewById(R.id.txtCurso);
            buttonRemover = itemView.findViewById(R.id.btnExcluir);
        }
    }
}
