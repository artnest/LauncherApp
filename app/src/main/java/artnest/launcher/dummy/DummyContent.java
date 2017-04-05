package artnest.launcher.dummy;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// TODO: Replace all uses of this class before publishing your app.
public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final List<DummyItem> POPULAR_ITEMS = new ArrayList<DummyItem>();
    public static final List<DummyItem> NEW_ITEMS = new LinkedList<DummyItem>();

    public static final int COUNT = 25 * 4 * 100;

    public static void populate(int iconId, int position) {
        addItem(createDummyItem(iconId, position));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);

        POPULAR_ITEMS.add(item);
//        NEW_ITEMS.add(item);
    }

    private static DummyItem createDummyItem(int id, int position) {
        return new DummyItem(id, Integer.toHexString(position));
    }

    public static class DummyItem implements Comparable {
        public final int iconId;
        public final String name;
        public int clicks;

        public DummyItem(int iconId, String name) {
            this.iconId = iconId;
            this.name = name;
            this.clicks = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DummyItem dummyItem = (DummyItem) o;

            if (iconId != dummyItem.iconId) return false;
            return name.equals(dummyItem.name);

        }

        @Override
        public int hashCode() {
            int result = iconId;
            result = 31 * result + name.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(@NonNull Object o) {
            return Integer.valueOf(this.clicks).compareTo(((DummyItem) o).clicks);
        }
    }
}
