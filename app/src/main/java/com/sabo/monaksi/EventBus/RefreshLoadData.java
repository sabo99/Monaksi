package com.sabo.monaksi.EventBus;

public class RefreshLoadData {
    private boolean monitoring, chart;
    private int idSubrapat;
    private String selectedRapat;

    public RefreshLoadData(boolean monitoring, boolean chart, int idSubrapat, String selectedRapat) {
        this.monitoring = monitoring;
        this.chart = chart;
        this.idSubrapat = idSubrapat;
        this.selectedRapat = selectedRapat;
    }

    public boolean isMonitoring() {
        return monitoring;
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }

    public boolean isChart() {
        return chart;
    }

    public void setChart(boolean chart) {
        this.chart = chart;
    }

    public int getIdSubrapat() {
        return idSubrapat;
    }

    public void setIdSubrapat(int idSubrapat) {
        this.idSubrapat = idSubrapat;
    }

    public String getSelectedRapat() {
        return selectedRapat;
    }

    public void setSelectedRapat(String selectedRapat) {
        this.selectedRapat = selectedRapat;
    }
}
