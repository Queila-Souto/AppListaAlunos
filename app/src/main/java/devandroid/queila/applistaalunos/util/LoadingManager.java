package devandroid.queila.applistaalunos.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import devandroid.queila.applistaalunos.R;

public class LoadingManager {
    private static int activeRequests = 0;
    private static View loadingView;

    public static void show(Activity activity) {
        System.out.println("COMECEI O LOADING");
        activeRequests++;

        if (loadingView == null) {
            FrameLayout root = activity.findViewById(android.R.id.content);
            loadingView = LayoutInflater.from(activity).inflate(R.layout.loading, root, false);
            root.addView(loadingView);
        }

        loadingView.setVisibility(View.VISIBLE);
    }

    public static void hide() {
        System.out.println("TERMINEI O LOADING");
        activeRequests--;
        if (activeRequests <= 0 && loadingView != null) {
            loadingView.setVisibility(View.GONE);
            activeRequests = 0;
        }
    }
}
