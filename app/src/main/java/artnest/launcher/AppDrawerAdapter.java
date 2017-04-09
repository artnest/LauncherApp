package artnest.launcher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.Collections;

import artnest.launcher.dummy.DummyContent;

public class AppDrawerAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder>
        /*implements INameableAdapter*/ {

    private final Context context;
    private static final int SECTION_COUNT = 3;

    public AppDrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getSectionCount() {
        return SECTION_COUNT;
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
                viewHolder.mTextView.setText("popular");
                break;
            case 1:
                viewHolder.mTextView.setText("new");
                break;
            default:
                viewHolder.mTextView.setText("all");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {
        final AppViewHolder viewHolder = (AppViewHolder) holder;
        switch (section) {
            case 0:
                viewHolder.mImageView.setImageResource(DummyContent.POPULAR_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.POPULAR_ITEMS.get(relativePosition).name);
                break;
            case 1:
                viewHolder.mImageView.setImageResource(DummyContent.NEW_ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.NEW_ITEMS.get(relativePosition).name);
                break;
            default:
                viewHolder.mImageView.setImageResource(DummyContent.ITEMS.get(relativePosition).iconId);
                viewHolder.mTextView.setText(DummyContent.ITEMS.get(relativePosition).name);
        }

        viewHolder.mItem = DummyContent.ITEMS.get(relativePosition);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, viewHolder.mTextView.getText(), Toast.LENGTH_SHORT).show();

                viewHolder.mItem.clicks++;
                Collections.sort(DummyContent.POPULAR_ALL_ITEMS, Collections.<DummyContent.DummyItem>reverseOrder());
                DummyContent.POPULAR_ITEMS.clear();
                for (int i = 0; i < AppDrawerFragment.mColumnCount; i++) {
                    DummyContent.POPULAR_ITEMS.add(DummyContent.POPULAR_ALL_ITEMS.get(i));
                }

                notifyItemRangeChanged(0, DummyContent.POPULAR_ITEMS.size() + 1);
            }
        };

        viewHolder.mImageView.setOnClickListener(listener);
        viewHolder.mTextView.setOnClickListener(listener);
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
                        .inflate(R.layout.fragment_app_drawer_item, parent, false);
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

    public static class AppViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImageView;
        final TextView mTextView;
        DummyContent.DummyItem mItem;

        public AppViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.icon);
            mTextView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        final TextView mTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.section_name);
        }

    }
}
