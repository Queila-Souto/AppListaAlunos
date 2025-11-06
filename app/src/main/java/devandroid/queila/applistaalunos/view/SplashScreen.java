// No pacote devandroid.queila.applistaalunos.view
package devandroid.queila.applistaalunos.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

// Importe o TokenManager
import devandroid.queila.applistaalunos.util.TokenManager;

public class SplashScreen extends AppCompatActivity { // Ou o nome que você deu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_splash);

        // Usar um Handler para dar um tempo para a splash screen aparecer
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Verifica se o token existe
            String token = TokenManager.getToken(this);

            if (token != null && !token.isEmpty()) {
                // Token existe: usuário está logado. Vá para a MainActivity.
                irParaTelaPrincipal();
            } else {
                // Token NÃO existe: usuário não está logado. Vá para a tela de Login.
                irParaTelaDeLogin();
            }
        }, 1500); // Delay de 1.5 segundos (ajuste se necessário)
    }

    private void irParaTelaPrincipal() {
        Intent intent = new Intent(this, MainActivity.class);
        // Essas flags limpam a pilha de navegação, impedindo o usuário de voltar para a Splash
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void irParaTelaDeLogin() {
        Intent intent = new Intent(this, Login.class);
        // O mesmo aqui: limpa a pilha para não voltar à Splash
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
