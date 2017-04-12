package artnest.launcher.dummy;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<>();
    public static final List<DummyItem> POPULAR_ITEMS = new LinkedList<>();
    public static final List<DummyItem> POPULAR_ALL_ITEMS = new LinkedList<>();
    public static final List<DummyItem> NEW_ITEMS = new LinkedList<>();
    public static final List<DummyItem> NEW_ALL_ITEMS = new LinkedList<>();

    public static final int COUNT = Integer.MAX_VALUE;

    public static void populate(int iconId, int position) {
        DummyItem item = createDummyItem(iconId, position);
        ITEMS.add(item);
        POPULAR_ALL_ITEMS.add(item);
        NEW_ALL_ITEMS.add(0, item);
    }

    private static DummyItem createDummyItem(int id, int position) {
        return new DummyItem(id, Integer.toHexString(position));
    }

    public static class DummyItem implements Comparable<DummyItem> {
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

            return iconId == dummyItem.iconId && name.equals(dummyItem.name);

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
        public int compareTo(@NonNull DummyItem o) {
            return Integer.valueOf(clicks).compareTo(o.clicks);
        }
    }
}
