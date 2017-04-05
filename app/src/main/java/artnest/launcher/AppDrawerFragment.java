package artnest.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import artnest.launcher.dummy.DummyContent;
import artnest.launcher.dummy.DummyContent.DummyItem;

public class AppDrawerFragment extends Fragment {

    public static boolean standardGrid = true;
    public static int themeId = 0;

    private static final int ICONS_COUNT = 8;
    private List<Integer> imageResources = new LinkedList<>();

    public static int mColumnCount = 1;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnListFragmentInteractionListener mListener;

    public AppDrawerFragment() {
    }

    public static AppDrawerFragment newInstance() {
        return new AppDrawerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switch (themeId) {
            default:
            case 0:
                getActivity().setTheme(R.style.AppTheme);
                break;
            case 1:
                getActivity().setTheme(R.style.AppThemeDark);
                break;
        }
        themeId = 0; // TODO use SharedPreferences

        if (standardGrid) {
            mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_standard);
        } else {
            mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_extended);
        }
        standardGrid = true; // TODO use SharedPreferences

        for (int i = 0; i < ICONS_COUNT; i++) {
            imageResources.add(getActivity().getResources().getIdentifier("@drawable/app_" + (i + 1),
                    "drawable",
                    getActivity().getPackageName()));
        }

        if (DummyContent.ITEMS.isEmpty()) {
            for (int i = 0; i < DummyContent.COUNT; i += mColumnCount) {
                Collections.shuffle(imageResources);
                for (int k = 0; k < mColumnCount; k++) {
                    DummyContent.populate(imageResources.get(k), i + 1 + k);
                }

                Collections.shuffle(imageResources);
                for (int k = 0; k < mColumnCount; k++) {
                    DummyContent.NEW_ITEMS.add(new DummyItem(imageResources.get(k),
                                                Integer.toHexString(i + 1 + k)));
                }
            }

            for (int i = 0; i < mColumnCount; i++) {
                DummyContent.ITEMS.add(0, DummyContent.POPULAR_ITEMS.get(mColumnCount * 2 - i - 1));
            }
            for (int i = 0; i < mColumnCount; i++) {
                DummyContent.ITEMS.add(mColumnCount, DummyContent.NEW_ITEMS.get(mColumnCount - i - 1));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_drawer_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                                            ((LinearLayoutManager) mLayoutManager).getOrientation()));
            } else {
                mLayoutManager = new GridLayoutManager(context, mColumnCount);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                                            ((GridLayoutManager) mLayoutManager).getOrientation()));
            }
            recyclerView.setAdapter(new AppDrawerRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
