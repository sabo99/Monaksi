package com.sabo.monaksi.Model;

public class SubrapatModel {
    private int id_subrapat, id_rapat, status;
    private String nama_subrapat;

    public int getId_subrapat() {
        return id_subrapat;
    }

    public void setId_subrapat(int id_subrapat) {
        this.id_subrapat = id_subrapat;
    }

    public int getId_rapat() {
        return id_rapat;
    }

    public void setId_rapat(int id_rapat) {
        this.id_rapat = id_rapat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNama_subrapat() {
        return nama_subrapat;
    }

    public void setNama_subrapat(String nama_subrapat) {
        this.nama_subrapat = nama_subrapat;
    }
}
