package artnest.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import artnest.launcher.dummy.DummyContent;

public class AppDrawerFragment extends Fragment {

    public static boolean standardGrid = true;
    public static int themeId = 0;

    private static final int ICONS_COUNT = 8;
    private List<Integer> imageResources = new LinkedList<>();

    public static int mColumnCount = 1;
    private RecyclerView.LayoutManager mLayoutManager;
//    private OnListFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;

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
                themeId = 0; // TODO use SharedPreferences
                break;
            case 1:
                getActivity().setTheme(R.style.AppThemeDark);
                themeId = 0; // TODO use SharedPreferences
                break;
        }

        if (standardGrid) {
            mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_standard);
        } else {
            mColumnCount = getActivity().getResources().getInteger(R.integer.drawer_columns_extended);
        }
        standardGrid = true; // TODO use SharedPreferences

        /*for (int i = 0; i < ICONS_COUNT; i++) {
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
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_drawer_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;

            if (mColumnCount <= 1) {
                mLayoutManager = new LinearLayoutManager(context);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),
                                            ((LinearLayoutManager) mLayoutManager).getOrientation()));
            } else {
                mLayoutManager = new GridLayoutManager(context, mColumnCount);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(),
                                            ((GridLayoutManager) mLayoutManager).getOrientation()));
            }
            setupAdapter();
        }
        return view;
    }

    private void setupAdapter() {
        Intent startupIntent = new Intent(Intent.ACTION_MAIN);
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        final PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(startupIntent, 0);

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                PackageManager packageManager = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER
                        .compare(lhs.loadLabel(packageManager).toString(),
                                rhs.loadLabel(packageManager).toString());
            }
        });

        mRecyclerView.setAdapter(new ActivitiesAdapter(activities));
    }


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyContent.DummyItem item);
    }

    public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.ActivityHolder> {
        private final List<ResolveInfo> mActivities;

        public ActivitiesAdapter(List<ResolveInfo> mActivities) {
            this.mActivities = mActivities;
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ActivityHolder(view);
        }

        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }

        public class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private ResolveInfo mResolveInfo;
            private TextView mNameTextView;
            private ImageView mIconImageView;

            public ActivityHolder(View itemView) {
                super(itemView);
                mNameTextView = (TextView) itemView;
                mNameTextView.setOnClickListener(this);
            }

            public void bindActivity(ResolveInfo resolveInfo) {
                mResolveInfo = resolveInfo;

                PackageManager packageManager = getActivity().getPackageManager();
                String appName = mResolveInfo.loadLabel(packageManager).toString();
                mNameTextView.setText(appName);
            }

            @Override
            public void onClick(View v) {
                ActivityInfo activityInfo = mResolveInfo.activityInfo;
                Intent intent = new Intent(Intent.ACTION_MAIN)
                        .setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
