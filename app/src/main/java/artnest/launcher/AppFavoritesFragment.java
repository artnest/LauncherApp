package artnest.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AppFavoritesFragment extends Fragment {

    public static final int SECTION_COUNT = 3;
    public static int mColumnCount = 2;
    private GridLayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;
    private AppDrawerAdapter mAdapter;

    public AppFavoritesFragment() {
    }

    public static AppFavoritesFragment newInstance() {
        return new AppFavoritesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        mAdapter = new AppDrawerAdapter(context);
        mAdapter.setHasStableIds(true);
        mLayoutManager = new GridLayoutManager(context, mColumnCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
