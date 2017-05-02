package artnest.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

public class AppDrawerAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>
        /*implements INameableAdapter*/ {

    private final Context mContext;
    private ContextMenu.ContextMenuInfo mContextMenuInfo;
    private final List<ResolveInfo> mActivities;
    private final PackageManager mPackageManager;

    public AppDrawerAdapter(Context context, List<ResolveInfo> activities) {
        this.mContext = context;
        this.mActivities = activities;
        this.mPackageManager = context.getPackageManager();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return AppDrawerFragment.SECTION_COUNT;
    }

    @Override
    public int getItemCount(int section) {
        /*switch (section) {
            case 0:
                return DummyContent.POPULAR_ITEMS.size();
            case 1:
                return DummyContent.NEW_ITEMS.size();
            default:
                return DummyContent.ITEMS.size();
        }*/

        switch (section) {
            case 0:
                return 4; // TODO change to actual size
            case 1:
                return 4; // TODO change to actual size
            default:
                return mActivities.size();
        }
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        switch (section) {
            case 0:
                viewHolder.mTextView.setText(R.string.popular_section);
                break;
            case 1:
                viewHolder.mTextView.setText(R.string.new_section);
                break;
            default:
                viewHolder.mTextView.setText(R.string.all_section);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        final AppViewHolder viewHolder = (AppViewHolder) holder;
        /*switch (section) {
            case 0:
                viewHolder.mImageView.setImageResource(DummyContent.POPULAR_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.POPULAR_ITEMS.get(relativePosition).name);
//                viewHolder.mItem = DummyContent.POPULAR_ITEMS.get(relativePosition);
                break;
            case 1:
                viewHolder.mImageView.setImageResource(DummyContent.NEW_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.NEW_ITEMS.get(relativePosition).name);
//                viewHolder.mItem = DummyContent.NEW_ITEMS.get(relativePosition);
                break;
            default:
                viewHolder.mImageView.setImageResource(DummyContent.ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.ITEMS.get(relativePosition).name);
//                viewHolder.mItem = DummyContent.ITEMS.get(relativePosition);
                viewHolder.mItem = mActivities.get(relativePosition);
        }*/

        viewHolder.bindApp(mActivities.get(relativePosition));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_header, parent, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);
                return new AppViewHolder(view);
        }
    }

    /*@Override
    public Character getCharacterForElement(int element) {
        Character c = mItems.get(element).name.charAt(0); // all items
        Character c = DummyContent.ITEMS.get(element).name.charAt(0); // TODO check (it should exclude popular and new)
        if (Character.isDigit(c)) {
            c = '#';
        }

        return c;
    }*/

    public class AppViewHolder extends RecyclerView.ViewHolder {

        ResolveInfo mResolveInfo;

        final ImageView mImageView;
        final TextView mTextView;

        public AppViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.icon);
            mTextView = (TextView) itemView.findViewById(R.id.label);

            itemView.setOnClickListener(clickListener);
            itemView.setOnLongClickListener(longClickListener);
        }

        public void bindApp(ResolveInfo resolveInfo) {
            mResolveInfo = resolveInfo;

            Drawable appIcon = mResolveInfo.loadIcon(mPackageManager);
            mImageView.setImageDrawable(appIcon);
            String appName = mResolveInfo.loadLabel(mPackageManager).toString();
            mTextView.setText(appName);
        }

        private View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mPackageManager.getLaunchIntentForPackage(mResolveInfo.activityInfo.applicationInfo.packageName);
                mContext.startActivity(intent);

//                mItem.clicks++;
//                AppDrawerFragment.notifyPopularItemRangeUpdated();
            }
        };

        private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openContextMenu(getAdapterPosition(), AppViewHolder.this, v);
                return true;
            }
        };
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        final TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.section_name);
        }
    }

    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
        final int position;
        final int relativePosition;
        final int absolutePosition;
        final AppViewHolder viewHolder;
        final View targetView;

        public RecyclerContextMenuInfo(int absolutePosition, AppViewHolder viewHolder, View targetView) {
            int position = absolutePosition;
            if (position <= AppDrawerFragment.mColumnCount) {
                position -= AppDrawerFragment.SECTION_COUNT - (AppDrawerFragment.SECTION_COUNT - 1);
            } else if (position <= AppDrawerFragment.mColumnCount * 2 + 1) {
                position -= AppDrawerFragment.SECTION_COUNT - (AppDrawerFragment.SECTION_COUNT - 2);
            } else {
                position -= AppDrawerFragment.SECTION_COUNT;
            }

            int relativePosition = position;
            if (relativePosition >= AppDrawerFragment.mColumnCount) {
                if (relativePosition <= AppDrawerFragment.mColumnCount * 2) {
                    relativePosition -= AppDrawerFragment.mColumnCount;
                } else {
                    relativePosition -= AppDrawerFragment.mColumnCount * 2;
                }
            }

            this.position = position;
            this.relativePosition = relativePosition;
            this.absolutePosition = absolutePosition;
            this.viewHolder = viewHolder;
            this.targetView = targetView;
        }
    }

    public ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    public void openContextMenu(int position, AppViewHolder viewHolder, View targetView) {
        mContextMenuInfo = createContextMenuInfo(position, viewHolder, targetView);
        targetView.showContextMenu();
    }

    private ContextMenu.ContextMenuInfo createContextMenuInfo(int position, AppViewHolder viewHolder, View targetView) {
        return new RecyclerContextMenuInfo(position, viewHolder, targetView);
    }
}
