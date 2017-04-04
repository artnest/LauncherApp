package artnest.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class PrefsFragment extends Fragment {
    private static final String PAGE = "page";
    private int mPage;

    private int layoutResId;

    private RadioButton mRadioButton1;
    private RadioButton mRadioButton2;

    public static IntroFragment newInstance(int page) {
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    public PrefsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(PAGE)) {
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" + argument!");
        }
        mPage = getArguments().getInt(PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        switch (mPage) {
            case 3:
                layoutResId = R.layout.welcome_prefs_slide1;
                break;
            case 4:
                layoutResId = R.layout.welcome_prefs_slide2;
                break;
            default:
                layoutResId = R.layout.welcome_prefs_slide2;
        }

        return getActivity().getLayoutInflater().inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (layoutResId == R.layout.welcome_prefs_slide1) {
            mRadioButton1 = (RadioButton) view.findViewById(R.id.standard_grid_radio_btn);
            mRadioButton2 = (RadioButton) view.findViewById(R.id.extended_grid_radio_btn);

            mRadioButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRadioButton1.setChecked(true);
                    mRadioButton2.setChecked(false);

                    getResources().getInteger(R.integer.drawer_columns) // TODO use flag
                }
            });
        }
    }
}
