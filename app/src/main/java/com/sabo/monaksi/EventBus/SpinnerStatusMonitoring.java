package com.sabo.monaksi.EventBus;

public class SpinnerStatusMonitoring {
    private boolean selected;
    private String resultSelected;

    public SpinnerStatusMonitoring(boolean selected, String resultSelected) {
        this.selected = selected;
        this.resultSelected = resultSelected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getResultSelected() {
        return resultSelected;
    }

    public void setResultSelected(String resultSelected) {
        this.resultSelected = resultSelected;
    }
}
