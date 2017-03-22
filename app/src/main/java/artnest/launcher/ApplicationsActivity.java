package artnest.launcher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mGridLayoutManager;
    private List<RecyclerItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final int columns = getResources().getInteger(R.integer.drawer_columns);
        mGridLayoutManager = new GridLayoutManager(this, columns);
        ((GridLayoutManager) mGridLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 2) {
                    return 2;
                }
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mItems = new ArrayList<>();
        mItems.add(new RecyclerItem("TestApp1", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp2", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp3", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp4", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp5", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp6", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp7", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp8", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp9", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp10", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp11", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp12", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp13", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp14", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp15", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp16", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp17", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp18", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp19", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));
        mItems.add(new RecyclerItem("TestApp20", ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_launcher, getTheme())));

        mAdapter = new RecyclerAdapter(mItems, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            Paint mPaint = new Paint();

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                int count = parent.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = parent.getChildAt(i);
                    int position = parent.getChildAdapterPosition(child);
                    if (position % 2 == 0) {
                        drawBackground(c, parent, i);
                    }
                }
            }

            private void drawBackground(Canvas c, RecyclerView parent, int index) {
                int l = parent.getLeft();
                int r = parent.getRight();
                int t = parent.getChildAt(index).getTop();
                int b = parent.getChildAt(index).getBottom();

                mPaint.setColor(Color.GREEN);
                c.drawRect(l, t, r, b, mPaint);
            }
        });
    }
}
