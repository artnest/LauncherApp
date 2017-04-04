package artnest.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

public class PrefsFragment extends Fragment {

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;
    private RadioButton mRadioButtonLight;
    private RadioButton mRadioButtonDark;
    private ImageView mStandardGridImageView;
    private ImageView mExtendedGridImageView;

    public static PrefsFragment newInstance() {
        return new PrefsFragment();
    }

    public PrefsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return getActivity().getLayoutInflater().inflate(R.layout.welcome_prefs_slide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioButton1 = (RadioButton) view.findViewById(R.id.standard_grid_radio_btn);
        mRadioButton2 = (RadioButton) view.findViewById(R.id.extended_grid_radio_btn);
        mRadioButtonLight = (RadioButton) view.findViewById(R.id.light_theme_radio_btn);
        mRadioButtonDark = (RadioButton) view.findViewById(R.id.dark_theme_radio_btn);
        mStandardGridImageView = (ImageView) view.findViewById(R.id.standard_grid_icon);
        mExtendedGridImageView = (ImageView) view.findViewById(R.id.extended_grid_icon);

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

        mRadioButtonLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadioButtonLight.setChecked(true);
                mRadioButtonDark.setChecked(false);

                AppDrawerFragment.themeId = 0;
            }
        });
        mRadioButtonDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRadioButtonLight.setChecked(false);
                mRadioButtonDark.setChecked(true);

                AppDrawerFragment.themeId = 1;
            }
        });
    }

    private void setStandardGridListener() {
        mRadioButton1.setChecked(true);
        mRadioButton2.setChecked(false);

        AppDrawerFragment.standardGrid = true;
    }

    private void setExtendedGridListener() {
        mRadioButton1.setChecked(false);
        mRadioButton2.setChecked(true);

        AppDrawerFragment.standardGrid = false;
    }
}
