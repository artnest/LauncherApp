package artnest.launcher;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable icon;
    private String name;

    public RecyclerItem(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
