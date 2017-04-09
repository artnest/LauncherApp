package artnest.launcher;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "cloud_launcher-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefsManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = prefs.edit();
        this.editor.apply();
    }

    public void setIsFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
