package artnest.launcher;

import android.app.Application;

import com.yandex.metrica.YandexMetrica;

public class App extends Application {

    private static final String API_KEY = "513e3c00-c94d-4243-aaf8-c84bea9ec54d";
    /*private static App instance = null;

    public static Context getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }*/

    @Override
    public void onCreate() {
        super.onCreate();

        YandexMetrica.activate(getApplicationContext(), API_KEY);
        YandexMetrica.enableActivityAutoTracking(this);
    }
}
