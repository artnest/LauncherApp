package artnest.launcher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import artnest.launcher.dummy.DummyContent;

public class AppDrawerAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>
        /*implements INameableAdapter*/ {

    private final Context context;
    private ContextMenu.ContextMenuInfo mContextMenuInfo;

    public AppDrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getSectionCount() {
        return AppDrawerFragment.SECTION_COUNT;
    }

    @Override
    public int getItemCount(int section) {
        switch (section) {
            case 0:
                return DummyContent.POPULAR_ITEMS.size();
            case 1:
                return DummyContent.NEW_ITEMS.size();
            default:
                return DummyContent.ITEMS.size();
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
        switch (section) {
            case 0:
                viewHolder.mImageView.setImageResource(DummyContent.POPULAR_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.POPULAR_ITEMS.get(relativePosition).name);
                viewHolder.mItem = DummyContent.POPULAR_ITEMS.get(relativePosition);
                break;
            case 1:
                viewHolder.mImageView.setImageResource(DummyContent.NEW_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.NEW_ITEMS.get(relativePosition).name);
                viewHolder.mItem = DummyContent.NEW_ITEMS.get(relativePosition);
                break;
            default:
                viewHolder.mImageView.setImageResource(DummyContent.ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.ITEMS.get(relativePosition).name);
                viewHolder.mItem = DummyContent.ITEMS.get(relativePosition);
        }
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
                Toast.makeText(context, mTextView.getText(), Toast.LENGTH_SHORT).show();

                mItem.clicks++;
                AppDrawerFragment.notifyPopularItemRangedChanged();
                notifyItemRangeChanged(1, DummyContent.POPULAR_ITEMS.size());
            }
        };

        private View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openContextMenu(getAdapterPosition(), v);
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
        final View targetView;

        public RecyclerContextMenuInfo(int absolutePosition, View targetView) {
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
            this.targetView = targetView;
        }
    }

    public ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return mContextMenuInfo;
    }

    public void openContextMenu(int position, View targetView) {
        mContextMenuInfo = createContextMenuInfo(position, targetView);
        targetView.showContextMenu();
    }

    private ContextMenu.ContextMenuInfo createContextMenuInfo(int position, View targetView) {
        return new RecyclerContextMenuInfo(position, targetView);
    }
}
