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

        /*if (DummyContent.ITEMS.isEmpty()) { // TODO rework orientation change
            for (int i = 0, index = 1; i < DummyContent.COUNT; i++, index++) {
                if (index > 5) {
                    index = 1;
                }
                int imageResource = getResources().getIdentifier("@drawable/app_" + index, "drawable", getPackageName());
                DummyContent.populate(imageResource, i + 1);
            }
        }*/

        Fragment fragment = ApplicationFragment.newInstance(getResources().getInteger(R.integer.drawer_columns));
        getSupportFragmentManager().beginTransaction().add(R.id.applications_activity, fragment).commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(ApplicationsActivity.this, item.name, Toast.LENGTH_SHORT).show();
    }
}
