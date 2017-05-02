package artnest.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.Collections;
import java.util.Comparator;
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

        /*if (imageResources.isEmpty()) {
            for (int i = 0; i < ICON_COUNT; i++) {
                imageResources.add(getResources().getIdentifier("@drawable/app_" + (i + 1),
                        "drawable",
                        getActivity().getPackageName()));
            }

            int count = mColumnCount;
            for (int i = 0; i < DummyContent.COUNTINITIAL_COUNT; i += mColumnCount) {
                Collections.shuffle(imageResources);
                if (i + mColumnCount >= DummyContent.COUNTINITIAL_COUNT) {
                    count = DummyContent.COUNTINITIAL_COUNT - DummyContent.ITEMS.size();
                }

                for (int k = 0; k < count; k++) {
                    DummyContent.populate(imageResources.get(k), i + 1 + k);
                }
            }

            for (int i = mColumnCount; i < mColumnCount * 2; i++) {
                DummyContent.POPULAR_ALL_ITEMS.get(i).clicks++;
            }
            Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
        }

        DummyContent.POPULAR_ITEMS.clear();
        DummyContent.NEW_ITEMS.clear();

        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(i));
        }
        for (int i = 0; i < mColumnCount; i++) {
            DummyContent.NEW_ITEMS.add(DummyContent.NEW_ALL_ITEMS.get(i));
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        mLayoutManager = new GridLayoutManager(context, mColumnCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        setupAdapter(context);

        return view;
    }

    private void setupAdapter(Context context) {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent, 0);

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                return String.CASE_INSENSITIVE_ORDER
                        .compare(lhs.loadLabel(packageManager).toString(),
                                rhs.loadLabel(packageManager).toString());
            }
        });

        mAdapter = new AppDrawerAdapter(context, activities);
        mAdapter.setHasStableIds(true);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        /*((DragScrollBar) view.findViewById(R.id.drag_scroll_bar))
                .setIndicator(new AlphabetIndicator(view.getContext()), true); // FIXME: Make sections scroll work*/
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

    /*@Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyItemRangeChanged(1, DummyContent.POPULAR_ITEMS.size());
//        notifyNewItemRangeUpdated();
        mAdapter.notifyItemRangeChanged(mColumnCount + (SECTION_COUNT - 1), DummyContent.NEW_ITEMS.size());
    }*/

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
                openAppSettings(info);
                return true;
            case R.id.delete:
                uninstallApp(info);

                /*boolean removedFromPopular = DummyContent.POPULAR_ITEMS.remove(info.viewHolder.mItem);
                DummyContent.POPULAR_ALL_ITEMS.remove(info.viewHolder.mItem);
                boolean removedFromNew = DummyContent.NEW_ITEMS.remove(info.viewHolder.mItem);
                DummyContent.NEW_ALL_ITEMS.remove(info.viewHolder.mItem);
                DummyContent.ITEMS.remove(info.viewHolder.mItem);

                if (removedFromPopular && removedFromNew) {
                    mAdapter.notifyDataSetChanged();
                }
                if (removedFromPopular && !removedFromNew) {
                    notifyPopularItemRangeChanged();
                    mAdapter.notifyItemRangeChanged(1, DummyContent.POPULAR_ITEMS.size()); // implement indexOf() (equals()) ?
                    mAdapter.notifyItemRangeChanged(mColumnCount * 2 + SECTION_COUNT, DummyContent.ITEMS.size());
                }
                if (!removedFromPopular && removedFromNew) {
                    notifyNewItemRangeChanged();
                    mAdapter.notifyItemRangeChanged(mColumnCount + (SECTION_COUNT - 1), DummyContent.NEW_ITEMS.size());
                    mAdapter.notifyItemRangeChanged(mColumnCount * 2 + SECTION_COUNT, DummyContent.ITEMS.size());
                }
                if (!removedFromPopular && !removedFromNew) {
                    mAdapter.notifyItemRangeChanged(info.absolutePosition, DummyContent.ITEMS.size() - info.relativePosition);
                }

                Toast.makeText(getActivity(), getResources().getText(R.string.removed), Toast.LENGTH_SHORT).show();*/
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void uninstallApp(AppDrawerAdapter.RecyclerContextMenuInfo info) {
        Intent uninstallIntent = new Intent(
                Intent.ACTION_UNINSTALL_PACKAGE,
                Uri.parse("package:" + info.viewHolder.mResolveInfo.activityInfo.packageName));
        startActivity(uninstallIntent);
    }

    private void openAppSettings(AppDrawerAdapter.RecyclerContextMenuInfo info) {
        Intent settingsIntent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + info.viewHolder.mResolveInfo.activityInfo.packageName));
        settingsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        startActivity(settingsIntent);
    }

    public static void notifyPopularItemRangeChanged() {
        Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
        DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(mColumnCount - 1));
    }

    public static void notifyNewItemRangeChanged() {
        DummyContent.NEW_ITEMS.add(DummyContent.NEW_ALL_ITEMS.get(mColumnCount - 1));
    }

    public static void notifyPopularItemRangeUpdated() {
        Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
        DummyContent.POPULAR_ITEMS.clear();
        for (int i = 0; i < AppDrawerFragment.mColumnCount; i++) {
            DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(i));
        }
    }

    public static void notifyNewItemRangeUpdated() {
        Collections.shuffle(DummyContent.NEW_ALL_ITEMS);
        DummyContent.NEW_ITEMS.clear();
        for (int i = 0; i < AppDrawerFragment.mColumnCount; i++) {
            DummyContent.NEW_ITEMS.add(DummyContent.NEW_ALL_ITEMS.get(i));
        }
    }
}
