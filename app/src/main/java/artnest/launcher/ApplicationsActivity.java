package artnest.launcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import artnest.launcher.dummy.DummyContent;

public class ApplicationsActivity extends AppCompatActivity implements ApplicationFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(ApplicationsActivity.this, item.content, Toast.LENGTH_LONG).show();
    }
}
