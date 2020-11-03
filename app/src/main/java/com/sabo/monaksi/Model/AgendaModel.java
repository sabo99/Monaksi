package com.sabo.monaksi.Model;

public class AgendaModel {
    private int id_agenda, id_subrapat, status;
    private String nama_agenda, tanggal_agenda;

    public int getId_agenda() {
        return id_agenda;
    }

    public void setId_agenda(int id_agenda) {
        this.id_agenda = id_agenda;
    }

    public int getId_subrapat() {
        return id_subrapat;
    }

    public void setId_subrapat(int id_subrapat) {
        this.id_subrapat = id_subrapat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNama_agenda() {
        return nama_agenda;
    }

    public void setNama_agenda(String nama_agenda) {
        this.nama_agenda = nama_agenda;
    }

    public String getTanggal_agenda() {
        return tanggal_agenda;
    }

    public void setTanggal_agenda(String tanggal_agenda) {
        this.tanggal_agenda = tanggal_agenda;
    }
}
