package com.sabo.monaksi.Model;

import java.util.List;

public class ResponseModel {
    private String message;
    private boolean exists;
    private List<MonitoringModel> data;
    private List<UserModel> user;

    /** Spinner PIC | APPROVAL | VERIFIKASI & AGENDA*/
    private List<UserModel> allUser;
    private List<AgendaModel> allAgenda;

    /** Spinner Rapat & SubRapat Aktif */
    private List<RapatModel> allRapat;
    private List<SubrapatModel> allSubrapat;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public List<MonitoringModel> getData() {
        return data;
    }

    public void setData(List<MonitoringModel> data) {
        this.data = data;
    }

    public List<UserModel> getUser() {
        return user;
    }

    public void setUser(List<UserModel> user) {
        this.user = user;
    }

    public List<UserModel> getAllUser() {
        return allUser;
    }

    public void setAllUser(List<UserModel> allUser) {
        this.allUser = allUser;
    }

    public List<AgendaModel> getAllAgenda() {
        return allAgenda;
    }

    public void setAllAgenda(List<AgendaModel> allAgenda) {
        this.allAgenda = allAgenda;
    }

    public List<RapatModel> getAllRapat() {
        return allRapat;
    }

    public void setAllRapat(List<RapatModel> allRapat) {
        this.allRapat = allRapat;
    }

    public List<SubrapatModel> getAllSubrapat() {
        return allSubrapat;
    }

    public void setAllSubrapat(List<SubrapatModel> allSubrapat) {
        this.allSubrapat = allSubrapat;
    }
}
