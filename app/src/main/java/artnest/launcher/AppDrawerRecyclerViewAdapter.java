package artnest.launcher;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import artnest.launcher.dummy.DummyContent;
import artnest.launcher.dummy.DummyContent.DummyItem;

public class AppDrawerRecyclerViewAdapter extends RecyclerView.Adapter<AppDrawerRecyclerViewAdapter.ViewHolder> {

    public final List<DummyItem> mValues;
    public final List<DummyItem> mPopularValues;
    public final List<DummyItem> mNewValues;
//    private final OnListFragmentInteractionListener mListener;

    public AppDrawerRecyclerViewAdapter(List<DummyItem> items/*, OnListFragmentInteractionListener listener*/) {
        mValues = items;
//        mListener = listener;

        mPopularValues = DummyContent.POPULAR_ITEMS;
        mNewValues = DummyContent.NEW_ITEMS;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(holder.getAdapterPosition());
        holder.mImageView.setImageResource(mValues.get(holder.getAdapterPosition()).iconId);
        holder.mNameView.setText(mValues.get(holder.getAdapterPosition()).name);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                    updatePopularAppsRow(holder);
                }*/
            }
        });

        holder.mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.mImageView, Gravity.END);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_info:
                                Toast.makeText(v.getContext(), "Info", Toast.LENGTH_SHORT).show();
                                updatePopularAppsRow(holder);
                                return true;
                            case R.id.menu_item_delete:
                                mValues.remove(holder.getAdapterPosition());
                                mPopularValues.remove(holder.mItem);
                                mNewValues.remove(holder.getAdapterPosition());

                                // notifyItemRemoved(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Removed", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
                return true;
            }
        });
    }

    private void updatePopularAppsRow(ViewHolder holder) {
        holder.mItem.clicks++;
        Collections.sort(mPopularValues, Collections.<DummyItem>reverseOrder());

        for (int i = 0; i < AppDrawerFragment.mColumnCount; i++) {
            mValues.remove(i);
        }
        for (int i = 0; i < AppDrawerFragment.mColumnCount; i++) {
            mValues.add(0, mPopularValues.get(AppDrawerFragment.mColumnCount - i - 1));
        }

        notifyItemRangeChanged(0, AppDrawerFragment.mColumnCount);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;
        public final TextView mNameView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.icon);
            mNameView = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
