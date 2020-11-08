package com.sabo.monaksi.API;

import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.JabatanModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.RapatModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.StatusModel;
import com.sabo.monaksi.Model.SubrapatModel;
import com.sabo.monaksi.Model.UserModel;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIRequestData {
    /**
     * Pie Chart
     */
    @FormUrlEncoded
    @POST("getCount.php")
    Call<StatusModel> getCount(@Field("id_subrapat") int ID_SUBRAPAT);

    /**
     * Get Monitoring By ID (Detail Task | Approval | Verified)
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("getMonByID.php")
    Call<MonitoringModel> getMonByID(@Field("ID_MON") String ID_MON);

    @FormUrlEncoded
    @POST("getJabatan.php")
    Call<JabatanModel> getJabatanByIdUser(@Field("id_user") int id_user);


    /**
     * Spinner Rapat Aktif
     * ----------------------------------------
     * Get All Rapat Aktif
     */
    @FormUrlEncoded
    @POST("getRapatAktif.php")
    Call<ResponseModel> getRapatAktif(@Field("id_jabatan") int id_jabatan);

    /**
     * GET ID RAPAT
     */
    @FormUrlEncoded
    @POST("getIdRapat.php")
    Call<RapatModel> getIdRapat(@Field("nama_rapat") String nama_rapat);

    /**
     * Get All Subrapat Aktif
     */
    @FormUrlEncoded
    @POST("getSubrapatAktif.php")
    Call<ResponseModel> getSubrapatAktif(@Field("id_jabatan") int id_jabatan,
                                         @Field("id_rapat") int id_rapat);

    /**
     * GET ID SUBRAPAT
     */
    @FormUrlEncoded
    @POST("getIdSubrapat.php")
    Call<SubrapatModel> getIdSubrapat(@Field("nama_subrapat") String nama_subrapat,
                                      @Field("id_rapat") int id_rapat);


    /**
     * List
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("getAllMon.php")
    Call<ResponseModel> getList(@Field("ID_SUBRAPAT") int ID_SUBRAPAT);

    @FormUrlEncoded
    @POST("getMonByStatus.php")
    Call<ResponseModel> getListByStatus(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                        @Field("LAST_STATUS") int LAST_STATUS);

    @FormUrlEncoded
    @POST("getMonByDate.php")
    Call<ResponseModel> getListByDate(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                      @Field("TGL_NOW") String TGL_NOW);

    /**
     * Task
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("getTask.php")
    Call<ResponseModel> getTask(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                @Field("PIC") String PIC);

    /**
     * Approval
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("getApproval.php")
    Call<ResponseModel> getApproval(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                    @Field("APPROVAL") String APPROVAL);

    /**
     * Verifikasi
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("getVerifikasi.php")
    Call<ResponseModel> getVerifikasi(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                      @Field("VERIFIKATOR") String VERIFIKATOR);

    /** Dialog Input / Edit Keputusan
     * =============================================================================================
     */
    /**
     * Get Nama Agenda
     */
    @FormUrlEncoded
    @POST("getNamaAgenda.php")
    Call<AgendaModel> getNamaAgenda(@Field("id_agenda") String id_agenda);

    /**
     * Get PROG_ID (Program ID)
     */
    @FormUrlEncoded
    @POST("getProgId.php")
    Call<MonitoringModel> getPROGID(@Field("nama_rapat") String nama_rapat,
                                    @Field("nama_subrapat") String nama_subrapat,
                                    @Field("id_subrapat") int id_subrapat);

    /**
     * Spinner Agenda
     */
    @FormUrlEncoded
    @POST("getAllAgenda.php")
    Call<ResponseModel> getAllAgenda(@Field("id_subrapat") int id_subrapat);

    @FormUrlEncoded
    @POST("getIdAgenda.php")
    Call<AgendaModel> getIdAgenda(@Field("nama_agenda") String nama_agenda);

    /**
     * Spinner PIC, APPROVAL, VERIFIKATOR
     */
    @GET("getAllUser.php")
    Call<ResponseModel> getAllUser();

    @FormUrlEncoded
    @POST("getUserByNama.php")
    Call<UserModel> getUserByNama(@Field("nama") String nama);

    /**
     * Input Keputusan Admin / Operator
     */
    @FormUrlEncoded
    @POST("inputKeputusan.php")
    Call<ResponseModel> inputKeputusan(@Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                       @Field("ID_AGENDA") int ID_AGENDA,
                                       @Field("PROG_ID") String PROG_ID,
                                       @Field("TGL_REPORT") String TGL_REPORT,
                                       @Field("TGL_TARGET") String TGL_TARGET,
                                       @Field("KEPUTUSAN") String KEPUTUSAN,
                                       @Field("PIC") String PIC,
                                       @Field("APPROVAL") String APPROVAL,
                                       @Field("VERIFIKATOR") String VERIFIKATOR,
                                       @Field("LAST_STATUS") int LAST_STATUS,
                                       @Field("LAST_UPDATES") String LAST_UPDATES);

    /**
     * Update Keputusan
     */
    @FormUrlEncoded
    @POST("updateKeputusan.php")
    Call<ResponseModel> updateKeputusan(@Field("ID_MON") int ID_MON,
                                        @Field("ID_SUBRAPAT") int ID_SUBRAPAT,
                                        @Field("ID_AGENDA") int ID_AGENDA,
                                        @Field("PROG_ID") String PROG_ID,
                                        @Field("TGL_REPORT") String TGL_REPORT,
                                        @Field("TGL_TARGET") String TGL_TARGET,
                                        @Field("KEPUTUSAN") String KEPUTUSAN,
                                        @Field("PIC") String PIC,
                                        @Field("APPROVAL") String APPROVAL,
                                        @Field("VERIFIKATOR") String VERIFIKATOR,
                                        @Field("LAST_STATUS") int LAST_STATUS,
                                        @Field("LAST_UPDATES") String LAST_UPDATES);

    @FormUrlEncoded
    @POST("deleteKeputusan.php")
    Call<ResponseModel> deleteKeputusan(@Field("ID_MON") int ID_MON,
                                        @Field("LAMPIRAN") String LAMPIRAN);


    /**
     * Upload File
     */
    @Multipart
    @POST("uploadFile.php")
    Call<ResponseModel> uploadFile(@Part MultipartBody.Part ID_MON,
                                   @Part MultipartBody.Part file,
                                   @Part MultipartBody.Part oldFileName);

    /**
     * Update Rencana Aksi
     */
    @FormUrlEncoded
    @POST("updateRencanaAksi.php")
    Call<ResponseModel> updateRencanaAksiMonitoring(@Field("ID_MON") String ID_MON,
                                                    @Field("RENCANA_AKSI") String RENCANA_AKSI);


    /**
     * Update Status Monitoring
     * - Revisi
     * - On Progress
     */
    @FormUrlEncoded
    @POST("updateStatusMon.php")
    Call<ResponseModel> updateStatusMonitoring(@Field("ID_MON") String ID_MON,
                                               @Field("LAST_STATUS") int LAST_STATUS,
                                               @Field("KOMENTAR") String KOMENTAR);


    /**
     * Update Status Monitoring = 3 "Selesai"
     */
    @FormUrlEncoded
    @POST("updateStatusMon3.php")
    Call<ResponseModel> updateStatusMon3(@Field("ID_MON") String ID_MON,
                                         @Field("TGL_SELESAI") String TGL_SELESAI,
                                         @Field("LAST_STATUS") int LAST_STATUS);

    /**
     * Update Status Monitoring = 4 "Approved"
     */
    @FormUrlEncoded
    @POST("updateStatusMon4.php")
    Call<ResponseModel> updateStatusMon4(@Field("ID_MON") String ID_MON,
                                         @Field("TGL_APPROVAL") String TGL_APPROVAL,
                                         @Field("KOMENTAR") String KOMENTAR,
                                         @Field("LAST_STATUS") int LAST_STATUS);

    /**
     * Update Status Monitoring = 5 "Verified | Closed"
     */
    @FormUrlEncoded
    @POST("updateStatusMon5.php")
    Call<ResponseModel> updateStatusMon5(@Field("ID_MON") String ID_MON,
                                         @Field("TGL_CLOSED") String TGL_CLOSED,
                                         @Field("KOMENTAR") String KOMENTAR,
                                         @Field("LAST_STATUS") int LAST_STATUS);

    /**
     * User
     * =============================================================================================
     */
    @FormUrlEncoded
    @POST("checkLogin.php")
    Call<ResponseModel> checkLogin(@Field("username") String username,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("checkUsername.php")
    Call<ResponseModel> checkUsername(@Field("username") String username);

    @FormUrlEncoded
    @POST("getUser.php")
    Call<UserModel> getUserInformation(@Field("username") String username);

    @FormUrlEncoded
    @POST("updateStatusUser.php")
    Call<ResponseModel> updateStatusUser(@Field("status") String status,
                                         @Field("lastLogin") String lastLogin,
                                         @Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("updatePassword.php")
    Call<ResponseModel> updatePasswordUser(@Field("password") String password,
                                           @Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("updateProfile.php")
    Call<ResponseModel> updateProfileUser(@Field("nama") String nama,
                                          @Field("username") String username,
                                          @Field("email") String email,
                                          @Field("id_user") int id_user);

    /**
     * LOG User
     */
    @FormUrlEncoded
    @POST("insertLogUser.php")
    Call<ResponseModel> insertLOGUser(@Field("nama") String NAMA,
                                      @Field("aksi") String AKSI,
                                      @Field("waktu") String WAKTU);
}
