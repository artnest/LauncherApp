package artnest.launcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import artnest.launcher.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements AppDrawerFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment fragment = AppDrawerFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main, fragment).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(MainActivity.this, item.name, Toast.LENGTH_SHORT).show();
    }
}
