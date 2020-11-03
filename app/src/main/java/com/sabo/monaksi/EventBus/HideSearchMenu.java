package com.sabo.monaksi.EventBus;

public class HideSearchMenu {
    private boolean hidden;

    public HideSearchMenu(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
