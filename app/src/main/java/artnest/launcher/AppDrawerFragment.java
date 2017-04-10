package artnest.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import artnest.launcher.dummy.DummyContent;

import static android.content.Context.MODE_PRIVATE;

public class AppDrawerFragment extends Fragment {

    private static int gridType;

    private static final int ICONS_COUNT = 10;
    private List<Integer> imageResources = new LinkedList<>();

    public static int mColumnCount = 2;
    private GridLayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;
    private AppDrawerAdapter mAdapter;

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;

    public AppDrawerFragment() {
    }

    public static AppDrawerFragment newInstance() {
        return new AppDrawerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveSharedPreferences();
        gridType = mPrefs.getInt(SettingsActivity.GRID_TYPE, 0);
        switch (gridType) {
            case 0:
                mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_standard);
                break;
            case 1:
                mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_extended);
                break;
        }

        if (DummyContent.ITEMS.isEmpty()) {
            for (int i = 0; i < ICONS_COUNT; i++) {
                imageResources.add(getResources().getIdentifier("@drawable/app_" + (i + 1),
                        "drawable",
                        getActivity().getPackageName()));
            }


            for (int i = 0; i < DummyContent.COUNT; i += mColumnCount) {
                Collections.shuffle(imageResources);
                if (i + mColumnCount < DummyContent.COUNT) {
                    for (int k = 0; k < mColumnCount; k++) {
                        DummyContent.populate(imageResources.get(k), i + 1 + k);
                    }
                } else {
                    for (int k = 0; k < DummyContent.COUNT - DummyContent.ITEMS.size(); k++) {
                        DummyContent.populate(imageResources.get(k), i + 1 + k);
                    }
                }
            }

            for (int i = mColumnCount; i < mColumnCount * 2; i++) {
                DummyContent.POPULAR_ALL_ITEMS.get(i).clicks++;
            }
            Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
        }

        DummyContent.POPULAR_ITEMS.clear();
        DummyContent.NEW_ITEMS.clear();

        /*for (int i = 0; i < DummyContent.POPULAR_ALL_ITEMS.size(); i++) {
            DummyContent.POPULAR_ALL_ITEMS.get(i).clicks = 0;
        }
        Collections.sort(DummyContent.POPULAR_ALL_ITEMS, new Comparator<DummyContent.DummyItem>() {
            @Override
            public int compare(DummyContent.DummyItem o1, DummyContent.DummyItem o2) {
                return Integer.valueOf(Integer.parseInt(o1.name, 16))
                        .compareTo(Integer.parseInt(o2.name, 16));
            }
        });

        for (int i = mColumnCount; i < mColumnCount * 2; i++) {
            DummyContent.POPULAR_ALL_ITEMS.get(i).clicks++;
        }
        Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());*/

        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(i));
        }
        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.NEW_ITEMS.add(DummyContent.NEW_ALL_ITEMS.get(i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        mAdapter = new AppDrawerAdapter(context);
        mLayoutManager = new GridLayoutManager(context, mColumnCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        /*((DragScrollBar) view.findViewById(R.id.drag_scroll_bar))
                .setIndicator(new AlphabetIndicator(view.getContext()), true); // FIXME: Make sections scroll work*/
        mRecyclerView.getItemAnimator().setChangeDuration(0);

        return view;
    }

    private void retrieveSharedPreferences() {
        mPrefs = getActivity().getSharedPreferences(PrefsManager.PREFS_NAME, MODE_PRIVATE);
        mEditor = mPrefs.edit();
        mEditor.apply();
    }
}
