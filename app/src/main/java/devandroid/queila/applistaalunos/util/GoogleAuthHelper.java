package devandroid.queila.applistaalunos.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;

public class GoogleAuthHelper {

    public static final int RC_SIGN_IN = 100;
    private final Activity activity;
    private final GoogleSignInClient googleSignInClient;
    private OnGoogleLoginListener onLoginListener;

    public GoogleAuthHelper(@NonNull Activity activity, @NonNull String clientId) {
        this.activity = activity;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void setOnLoginListener(OnGoogleLoginListener listener) {
        this.onLoginListener = listener;
    }

    public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleResult(int requestCode, Intent data) {
        if (requestCode != RC_SIGN_IN) return;
        if (onLoginListener == null) {
            Log.e("GOOGLE_AUTH", "OnGoogleLoginListener não foi definido. Chame setOnLoginListener() primeiro.");
            return;
        }
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            if (account != null && account.getIdToken() != null) {
                onLoginListener.onSuccess(account.getIdToken());
                Log.d("GOOGLE_LOGIN", "Login com Google bem-sucedido. Token obtido.");
            } else {
                onLoginListener.onError(new Exception("Falha ao obter o ID Token da conta Google."));
                Log.e("GOOGLE_LOGIN", "Conta Google retornada, mas o ID Token é nulo.");
            }
        } catch (ApiException e) {
            String errorMessage = "Falha no login com Google. Código: " + e.getStatusCode();
            Log.e("GOOGLE_LOGIN", errorMessage, e);
            onLoginListener.onError(e);
        }
    }

    public interface OnGoogleLoginListener {
        void onSuccess(@NonNull String idToken);
        void onError(@NonNull Exception e);
    }
}
