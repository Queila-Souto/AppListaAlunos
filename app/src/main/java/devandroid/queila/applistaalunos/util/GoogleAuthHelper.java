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

public class GoogleAuthHelper {
    private final Activity activity;
    private final GoogleSignInClient googleSignInClient;

    public int getRC_SIGN_IN() {
        return RC_SIGN_IN;
    }

    private final int RC_SIGN_IN = 100;

    public GoogleAuthHelper(Activity activity, String clientId) {
        this.activity = activity;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleResult(int requestCode, Intent data, OnGoogleLoginListener listener) {
        if (requestCode != RC_SIGN_IN) return;

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            listener.onSuccess(account.getIdToken());
            Log.d("GOOGLE_LOGIN", "Token: " + account.getIdToken());

        } catch (ApiException e) {
            listener.onError(e);
            Log.d("GOOGLE_LOGIN", "nao consegui logar " + e);

        }
    }

    public interface OnGoogleLoginListener {
        void onSuccess(String idToken);
        void onError(Exception e);
    }
}
