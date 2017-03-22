package artnest.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "LauncherMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, ApplicationsActivity.class);
        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WelcomeActivity.this,
                        "Hello!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }
}
