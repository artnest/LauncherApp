package artnest.launcher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import artnest.launcher.dummy.DummyContent;

public class AppFavoritesAdapter extends RecyclerView.Adapter<AppFavoritesAdapter.AppViewHolder> {

    private final Context context;

    public AppFavoritesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final AppViewHolder viewHolder = holder;
        viewHolder.mImageView.setImageResource(DummyContent.ITEMS.get(position).iconId);
        viewHolder.mTextView.setText(DummyContent.ITEMS.get(position).name);
        viewHolder.mItem = DummyContent.ITEMS.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImageView;
        final TextView mTextView;
        DummyContent.DummyItem mItem;

        public AppViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.icon);
            mTextView = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(clickListener);
            itemView.setOnLongClickListener(longClickListener);
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), mTextView.getText(), Toast.LENGTH_SHORT).show();

                mItem.clicks++;
                AppDrawerFragment.notifyPopularItemRangeUpdated();
            }
        };

        private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                openContextMenu(getAdapterPosition(), AppFavoritesAdapter.AppViewHolder.this, v);
                return true;
            }
        };
    }
}
