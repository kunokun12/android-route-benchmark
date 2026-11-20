package dev.androidroute.benchmark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Read-only, deterministic accessibility navigation fixture. No network or storage. */
public final class MainActivity extends Activity {
    private int step = 0;
    private static final String[] HEADINGS = {"Benchmark Home", "Benchmark Guide", "Benchmark Details", "Benchmark About"};
    private static final String[] MARKERS = {"Home Screen Verified", "Guide Screen Verified", "Details Screen Verified", "About Screen Verified"};
    private static final String[] ACTIONS = {"Open Guide", "View Details", "About Test"};

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        step = savedInstanceState == null ? 0 : Math.max(0, Math.min(3, savedInstanceState.getInt("step", 0)));
        if (getIntent() != null && "dev.androidroute.benchmark.RESET".equals(getIntent().getAction())) step = 0;
        render();
    }
    @Override protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if ("dev.androidroute.benchmark.RESET".equals(intent.getAction())) { step = 0; render(); }
    }
    @Override protected void onSaveInstanceState(Bundle state) { state.putInt("step", step); super.onSaveInstanceState(state); }
    private TextView label(String text, int sp) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(sp);
        view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_YES);
        view.setPadding(0, 16, 0, 16);
        return view;
    }
    private void render() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(40, 72, 40, 40);
        layout.addView(label(HEADINGS[step], 28));
        layout.addView(label(MARKERS[step], 20));
        if (step < 3) {
            Button next = new Button(this);
            next.setText(ACTIONS[step]);
            next.setAllCaps(false);
            next.setOnClickListener(v -> { step++; render(); });
            layout.addView(next, new LinearLayout.LayoutParams(-1, -2));
        }
        setContentView(layout);
    }
    @Override public void onBackPressed() {
        if (step > 0) { step--; render(); }
        else super.onBackPressed();
    }
}
