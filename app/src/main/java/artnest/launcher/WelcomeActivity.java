package artnest.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnNext;
    private Button btnSkip;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private static final String IS_FIRST_TIME_LAUNCH = "first_time_launch";

    private static boolean firstTimeLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode());
        super.onCreate(savedInstanceState);

        retrieveSharedPreferences();
        firstTimeLaunch = mPrefs.getBoolean(IS_FIRST_TIME_LAUNCH, true);
        if (!firstTimeLaunch) {
            launchHomeScreen();
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layout_dots);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSkip = (Button) findViewById(R.id.btn_skip);

        layouts = new int[]{R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3};
        addBottomDots(0);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        // Disable ViewPager swipes
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                } else {
                    finishIntro();
                }
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishIntro();
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(ResourcesCompat.getColor(getResources(), R.color.dot_inactive, getTheme()));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(ResourcesCompat.getColor(getResources(), R.color.dot_active, getTheme()));
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void retrieveSharedPreferences() {
        mPrefs = getSharedPreferences(PrefsManager.PREFS_NAME, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.apply();
    }

    private void finishIntro() {
//        prefsManager.setFirstTimeLaunch(false); // comment to turn off first launch check
//        prefsManager.setFirstTimeLaunch(true); // uncomment
//        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, false);
        mEditor.putBoolean(IS_FIRST_TIME_LAUNCH, true);
        mEditor.commit();
        startActivity(new Intent(WelcomeActivity.this, SettingsActivity.class));
        finish();
    }

    private void launchHomeScreen() {
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == layouts.length - 1) {
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IntroFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return layouts.length;
        }
    }
}
