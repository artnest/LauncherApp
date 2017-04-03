package artnest.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IntroFragment extends Fragment {
    private static final String PAGE = "page";
    private int mPage;

    public static IntroFragment newInstance(int page) {
        IntroFragment fragment = new IntroFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, page);
        fragment.setArguments(bundle);
        return fragment;
    }

    public IntroFragment() {
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
        int layoutResId;
        switch (mPage) {
            case 0:
                layoutResId = R.layout.welcome_slide1;
                break;
            case 1:
                layoutResId = R.layout.welcome_slide2;
                break;
            default:
                layoutResId = R.layout.welcome_slide3;
        }

        return getActivity().getLayoutInflater().inflate(layoutResId, container, false);
    }
}
