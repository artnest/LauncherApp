package artnest.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private ImageView mStandardGridImageView;
    private ImageView mExtendedGridImageView;
    private Switch mSwitch;
    private Button mButtonStart;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    public static final String GRID_TYPE = "grid_type";

    private static boolean isToggled = false;
    private static boolean isGridTypeToggled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_prefs);

        mRadioButton1 = (RadioButton) findViewById(R.id.standard_grid_radio_btn);
        mRadioButton2 = (RadioButton) findViewById(R.id.extended_grid_radio_btn);
        mStandardGridImageView = (ImageView) findViewById(R.id.standard_grid_icon);
        mExtendedGridImageView = (ImageView) findViewById(R.id.extended_grid_icon);
        mSwitch = (Switch) findViewById(R.id.switch_toggle);
        mButtonStart = (Button) findViewById(R.id.btn_start);
        retrieveSharedPreferences();

        mStandardGridImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStandardGridListener();
            }
        });
        mRadioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStandardGridListener();
            }
        });
        mExtendedGridImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExtendedGridListener();
            }
        });
        mRadioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setExtendedGridListener();
            }
        });
        setRadioButtonsChecked();

        mSwitch.setChecked(isToggled);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    isToggled = true;
                } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    isToggled = false;
                }*/

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                isToggled = isChecked;

                recreateActivity();
            }
        });

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void retrieveSharedPreferences() {
        mPrefs = getSharedPreferences(PrefsManager.PREFS_NAME, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.apply();
    }

    private void recreateActivity() {
        Intent intent = getIntent();
        //intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    private void setRadioButtonsChecked() {
        mRadioButton1.setChecked(isGridTypeToggled);
        mRadioButton2.setChecked(!isGridTypeToggled);
    }

    private void setStandardGridListener() {
        isGridTypeToggled = true;
        setRadioButtonsChecked();

        mEditor.putInt(GRID_TYPE, 0);
        mEditor.commit();
    }

    private void setExtendedGridListener() {
        isGridTypeToggled = false;
        setRadioButtonsChecked();

        mEditor.putInt(GRID_TYPE, 1);
        mEditor.commit();
    }
}
