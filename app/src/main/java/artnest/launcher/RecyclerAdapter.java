package artnest.launcher;

import android.content.Context;
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

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<RecyclerItem> mItems;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mAppIcon;
        public TextView mAppName;
        public ViewHolder(View v) {
            super(v);

            mAppIcon = (ImageView) v.findViewById(R.id.item_icon);
            mAppName = (TextView) v.findViewById(R.id.item_name);
        }
    }

    public RecyclerAdapter(List<RecyclerItem> mItems, Context mContext) {
        this.mItems = mItems;
        this.mContext = mContext;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).hashCode();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 4) {
            return 1;
        }
        return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        final RecyclerItem item = mItems.get(position);
        holder.mAppIcon.setImageDrawable(item.getIcon());
        holder.mAppName.setText(item.getName());

        holder.mAppIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.mAppIcon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(mContext, holder.mAppIcon, Gravity.END);
                popupMenu.inflate(R.menu.options_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_info:
                                Toast.makeText(mContext, "Info", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.menu_item_delete:
                                mItems.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
