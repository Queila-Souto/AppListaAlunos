package devandroid.queila.applistaalunos.view;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TelefoneMascara {
    public static TextWatcher insert(final EditText editText){
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().replaceAll("[^\\d]", "");
                if (str.length() > 11) {
                    str = str.substring(0, 11);
                }
                String formatted;
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                if (str.length() <= 10) {
                    formatted = formatFixo(str);
                } else {
                    formatted = formatCelular(str);
                }
                isUpdating = true;
                editText.setText(formatted);
                editText.setSelection(formatted.length());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private static String formatFixo(String str) {
        return str.length() >= 2
                ? "(" + str.substring(0, 2) + ")" +
                (str.length() > 6
                        ? str.substring(2, 6) + "-" + str.substring(6)
                        : str.substring(2))
                : str;
    }
    private static String formatCelular(String str) {
        return str.length() >= 2
                ? "(" + str.substring(0, 2) + ")" +
                (str.length() > 7
                        ? str.substring(2, 7) + "-" + str.substring(7)
                        : str.substring(2))
                : str;
    }

    public static String limpar(String telefoneFormatado) {
        return telefoneFormatado.replaceAll("[^\\d]", "");
    }
}
