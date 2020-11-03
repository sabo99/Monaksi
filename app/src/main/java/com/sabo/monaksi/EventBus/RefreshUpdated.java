package com.sabo.monaksi.EventBus;

public class RefreshUpdated {
    private boolean profileUpdated, listUpdated;

    public RefreshUpdated(boolean profileUpdated, boolean listUpdated) {
        this.profileUpdated = profileUpdated;
        this.listUpdated = listUpdated;
    }

    public boolean isProfileUpdated() {
        return profileUpdated;
    }

    public void setProfileUpdated(boolean profileUpdated) {
        this.profileUpdated = profileUpdated;
    }

    public boolean isListUpdated() {
        return listUpdated;
    }

    public void setListUpdated(boolean listUpdated) {
        this.listUpdated = listUpdated;
    }
}
