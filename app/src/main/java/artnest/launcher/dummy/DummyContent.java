package artnest.launcher.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static final int COUNT = 25;

    public static void populate(int iconId, int position) {
        addItem(createDummyItem(iconId, position));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
    }

    private static DummyItem createDummyItem(int id, int position) {
        return new DummyItem(id, "Item " + position);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int iconId;
        public final String name;

        public DummyItem(int iconId, String name) {
            this.iconId = iconId;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
