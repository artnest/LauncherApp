package artnest.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import artnest.launcher.dummy.DummyContent;

public class ApplicationsActivity extends AppCompatActivity implements ApplicationFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        if (savedInstanceState == null) {
//            Fragment fragment = ApplicationFragment.newInstance(getResources().getInteger(R.integer.drawer_columns));
            Fragment fragment = ApplicationFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_applications, fragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(ApplicationsActivity.this, item.name, Toast.LENGTH_SHORT).show();
    }
}
