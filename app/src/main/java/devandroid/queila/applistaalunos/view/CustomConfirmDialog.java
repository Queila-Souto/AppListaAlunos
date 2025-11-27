package devandroid.queila.applistaalunos.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import devandroid.queila.applistaalunos.R;

public class CustomConfirmDialog extends DialogFragment {

    public interface OnConfirmListener {
        void onConfirm();
        void onCancel();
    }

    private final OnConfirmListener listener;
    private final String titulo;
    private final String mensagem;

    public CustomConfirmDialog(String titulo, String mensagem, OnConfirmListener listener) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialog, null);

        // Referências
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);
        TextView txtMensagem = view.findViewById(R.id.txtMensagem);
        Button btnSim = view.findViewById(R.id.btnSim);
        Button btnNao = view.findViewById(R.id.btnNao);

        // Textos
        txtTitulo.setText(titulo);
        txtMensagem.setText(mensagem);

        btnSim.setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onConfirm();
        });

        btnNao.setOnClickListener(v -> {
            dismiss();
            if (listener != null) listener.onCancel();
        });

        builder.setView(view);

        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
