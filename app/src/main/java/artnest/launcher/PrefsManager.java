package artnest.launcher;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String PREFS_NAME = "cloud_launcher";
    private static final String IS_FIRST_TIME_LAUNCH = "first_time_launch";

    public PrefsManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.editor = prefs.edit();
        this.editor.apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTimeLaunch);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return prefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
