package devandroid.queila.applistaalunos.view;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import devandroid.queila.applistaalunos.R;

public class SplashScreen extends AppCompatActivity {

    // Duração da tela splash (em milissegundos)
    private static final int SPLASH_DURATION = 3000; // 3 segundos

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen); // seu layout contendo o ImageView

        ImageView imageView = findViewById(R.id.splash_anim);
        Drawable drawable = imageView.getDrawable();

        // Inicia a animação AVD
        if (drawable instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawable;
            avd.start();
        }

        // Transição automática para a próxima Activity após o tempo definido
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish(); // Fecha a splash para não voltar a ela
            }
        }, SPLASH_DURATION);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Garante que a animação reinicie se a splash for exibida novamente
        ImageView imageView = findViewById(R.id.splash_anim);
        Drawable drawable = imageView.getDrawable();

        if (drawable instanceof AnimatedVectorDrawable) {
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) drawable;
            avd.start();
        }
    }
}