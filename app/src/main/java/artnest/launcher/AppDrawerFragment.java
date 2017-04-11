package artnest.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import artnest.launcher.dummy.DummyContent;

import static android.content.Context.MODE_PRIVATE;

public class AppDrawerFragment extends Fragment {

    private static int gridType;

    private static final int ICON_COUNT = 10;
    private List<Integer> imageResources = new LinkedList<>();

    public static final int SECTION_COUNT = 3;
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
            for (int i = 0; i < ICON_COUNT; i++) {
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
        mRecyclerView.setHasFixedSize(true);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        registerForContextMenu(mRecyclerView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AppDrawerAdapter.RecyclerContextMenuInfo info =
                (AppDrawerAdapter.RecyclerContextMenuInfo) mAdapter.getContextMenuInfo();

        switch (item.getItemId()) {
            case R.id.info:
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete:
                int itemPosition = info.position;
                if (itemPosition < mColumnCount) {
                    DummyContent.POPULAR_ITEMS.remove(info.relativePosition);
                    DummyContent.POPULAR_ALL_ITEMS.remove(info.relativePosition);

                    notifyPopularItemRangedChanged();
                    mAdapter.notifyItemRangeChanged(SECTION_COUNT - (SECTION_COUNT - 1), DummyContent.POPULAR_ITEMS.size());
                } else if (itemPosition < mColumnCount * 2) {
                    DummyContent.NEW_ITEMS.remove(info.relativePosition);
                    DummyContent.NEW_ALL_ITEMS.remove(info.relativePosition);

                    notifyNewItemRangedChanged();
                    mAdapter.notifyItemRangeChanged(mColumnCount + (SECTION_COUNT - 1), DummyContent.NEW_ITEMS.size());
                } else {
                    DummyContent.ITEMS.remove(info.relativePosition);
                    mAdapter.notifyItemRemoved(info.absolutePosition);
                }

                Toast.makeText(getActivity(), getResources().getText(R.string.removed), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public static void notifyPopularItemRangedChanged() {
        Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
        DummyContent.POPULAR_ITEMS.clear();
        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(i));
        }
    }

    public static void notifyNewItemRangedChanged() {
        DummyContent.NEW_ITEMS.clear();
        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.NEW_ITEMS.add(DummyContent.NEW_ALL_ITEMS.get(i));
        }
    }
}
