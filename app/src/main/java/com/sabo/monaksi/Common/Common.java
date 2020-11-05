package com.sabo.monaksi.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.API.RetrofitClient;
import com.sabo.monaksi.EventBus.RefreshLoadData;
import com.sabo.monaksi.EventBus.SpinnerStatusMonitoring;
import com.sabo.monaksi.LoginActivity;
import com.sabo.monaksi.MainActivity;
import com.sabo.monaksi.Model.JabatanModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.R;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.github.mikephil.charting.utils.ColorTemplate.rgb;

public class Common {
    public static final String DOWNLOAD_URL = "https://monaksi-v001.000webhostapp.com/android/monaksi/assets/";
    /**
     * Key SharedPreferences
     */
    public static final String SF_ID_USER = "idUser";
    public static final String SF_USERNAME = "username";
    public static final String SF_NAMA = "nama";
    public static final String SF_LEVEL = "level";
    public static final String SF_EMAIL = "email";
    public static final String SF_LAST_LOGIN = "lastLogin";
    public static final String SF_ID_JABATAN = "idJabatan";
    public static final String SF_LOGGED_IN = "loggedIn";
    /**
     * Request Code
     */
    public static final int REQUEST_PERMISSION_UPLOAD = 100;
    public static final int REQUEST_PERMISSION_DOWNLOAD = 101;
    public static final int PICK_FILE_REQUEST = 10;
    public static final int MAX_SIZE_UPLOAD = (1024 * 1024) * 10; // 10 MB
    /**
     * Color of Pie Chart
     */
    public static final int[] COLOR_STATUS_MON = {
            colorStatus0(), colorStatus1(), colorStatus2(), colorStatus3(), colorStatus4(), colorStatus5()
    };
    /**
     * BASE URL HOSTING / LocalHost
     */
    private static final String BASE_URL = "https://monaksi-v001.000webhostapp.com/android/monaksi/";
    /**
     * Default Spinner Global
     */
    public static String selectDefault = "-- Pilih Default --";
    /**
     * Selected List Item
     */
    public static MonitoringModel selectedMonitoring;
    public static String namaRapat;
    public static String namaSubrapat;
    public static int idSubrapat;
    private static final APIRequestData mService = getAPI();

    /**
     * Create API Request Data
     */
    public static APIRequestData getAPI() {
        return RetrofitClient.getInstance(BASE_URL).create(APIRequestData.class);
    }

    /**
     * API WhatsApp
     */
    public static String getAPIWhatsApp(Context context) {
        return "https://api.whatsapp.com/send?phone=" + context.getResources().getString(R.string.maction_phone) + "&text=Aplikasi%20M-Action-Android%20UIKSBS";
    }

    /**
     * Color Status Pie Chart
     */
    public static int colorStatus0() {
        return rgb("DD4B39");
    }

    public static int colorStatus1() {
        return rgb("D2D6DE");
    }

    public static int colorStatus2() {
        return rgb("F39C12");
    }

    public static int colorStatus3() {
        return rgb("14A2BA");
    }

    public static int colorStatus4() {
        return rgb("00C0EF");
    }

    public static int colorStatus5() {
        return rgb("39CCCC");
    }

    /**
     * Convert Format Status Monitoring from Int to String
     */
    public static String formatStatusMonitoringToString(int lastStatus) {
        switch (lastStatus) {
            case 0:
                return "Revisi";
            case 1:
                return "Belum Dikerjakan";
            case 2:
                return "On Progress";
            case 3:
                return "Selesai";
            case 4:
                return "Approved";
            case 5:
                return "Verified";
            default:
                return "Unknown";
        }
    }

    /**
     * Convert Format Status Monitoring from String to Int
     */
    public static int formatStatusMonitoringToInt(String lastStatus) {
        switch (lastStatus) {
            case "Revisi":
                return 0;
            case "Belum Dikerjakan":
                return 1;
            case "On Progress":
                return 2;
            case "Selesai":
                return 3;
            case "Approved":
                return 4;
            case "Verified":
                return 5;
            case "Backlog":
                return 6;
            case "-":
                return 1000;
            default:
                return 999;
        }
    }

    /**
     * Convert Format Level User from Int to String
     */
    public static String formatLevel(int level) {
        switch (level) {
            case 0:
                return "-";
            case 1:
                return "Admin";
            case 2:
                return "User";
            case 3:
                return "Operator";
            case 4:
                return "Viewer";
            default:
                return "Unknown";
        }
    }

    /**
     * Convert Format Tanggal from "yyyy-MM-dd" to "dd-MM-yyyy"
     */
    public static String formatTgl(String tgl) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date;
        String result = null;

        try {
            date = inputFormat.parse(tgl);
            result = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Get ID Jabatan for Session ID Jabatan
     */
    public static void getIDJabatan(Context context) {
        int IDUser = Preferences.getIDUser(context);
        SweetAlertDialog sweetLoading = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Loading").setCancelable(false);
        sweetLoading.show();

        /** ID Jabatan */
        mService.getJabatanByIdUser(IDUser)
                .enqueue(new Callback<JabatanModel>() {
                    @Override
                    public void onResponse(Call<JabatanModel> call, Response<JabatanModel> response) {
                        if (response.isSuccessful()) {
                            JabatanModel jabatanModel = response.body();

                            /** Set Session ID JABATAN */
                            Preferences.setIDJabatan(context, jabatanModel.getId_jabatan());
                            sweetLoading.dismissWithAnimation();
                        }
                    }

                    @Override
                    public void onFailure(Call<JabatanModel> call, Throwable t) {
                        if (t.toString().contains("timeout"))
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(t.getMessage())
                                    .setConfirmText(context.getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                        else if (t.toString().contains("Unable to resolve host"))
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(context.getString(R.string.noConnection))
                                    .setConfirmText(context.getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                        else
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(t.getMessage())
                                    .setConfirmText(context.getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);

                    }
                });
    }

    /**
     * Show Detail Status Monitoring
     */
    public static void showDetailStatusMonitoring(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_info_status, null);
        SweetAlertDialog sweetInfoStatus = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.logo_pln)
                .setTitleText(context.getString(R.string.titleDialogInfoStatus))
                .setContentText(context.getString(R.string.contentInfoStatus))
                .setConfirmText(context.getString(R.string.textClose));
        sweetInfoStatus.setCanceledOnTouchOutside(true);
        sweetInfoStatus.show();

        LinearLayout linearLayout = sweetInfoStatus.findViewById(R.id.loading);
        linearLayout.setGravity(Gravity.CENTER);
        TextView title = sweetInfoStatus.findViewById(R.id.title_text);
        TextView content = sweetInfoStatus.findViewById(R.id.content_text);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setTextColor(context.getResources().getColor(android.R.color.black));
        content.setTextSize(12);
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index + 1);
    }

    /**
     * Show Menu Feedback
     */
    public static void showMenuFeedback(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_feedback, null);
        TextView tvPhone, tvEmail;
        tvPhone = view.findViewById(R.id.tvPhone);
        tvEmail = view.findViewById(R.id.tvEmail);
        SweetAlertDialog sweetFeedBack = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(context.getString(R.string.titleDialogFeedback))
                .setCustomImage(context.getDrawable(R.drawable.ic_round_bug_report_black))
                .setContentText(context.getResources().getString(R.string.content_feedback))
                .setConfirmText(context.getString(R.string.textClose));
        sweetFeedBack.setOnShowListener(dialog -> {
            tvPhone.setText("+" + context.getResources().getString(R.string.maction_phone));
            tvPhone.setOnClickListener(v -> {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getAPIWhatsApp(context))));
            });

            tvEmail.setText(context.getResources().getString(R.string.maction_gmail));
            tvEmail.setOnClickListener(v -> {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getResources().getString(R.string.maction_gmail)});
                i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.subjectGmail));
                if (i.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(Intent.createChooser(i, "Choose Gmail"));
                }
            });
        });
        sweetFeedBack.setCanceledOnTouchOutside(true);
        sweetFeedBack.show();
        LinearLayout linearLayout = sweetFeedBack.findViewById(R.id.loading);
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index + 1);
    }

    /**
     * Show Menu Logout
     */
    public static void showMenuLogout(Context context) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(context.getDrawable(R.drawable.ic_round_logout_custom))
                .setTitleText(context.getString(R.string.titleDialogLogout))
                .setContentText(context.getString(R.string.contentLogout))
                .showCancelButton(true)
                .setCancelText("Tidak")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .setConfirmText("Ya")
                .setConfirmClickListener(sweetAlertDialog -> {
                    logout(context, sweetAlertDialog);
                })
                .show();
    }

    private static void logout(Context context, SweetAlertDialog sweetAlertDialog) {
        sweetAlertDialog.showCancelButton(false);
        sweetAlertDialog.showContentText(false);
        sweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Please wait...").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);


        String lastLogin = Preferences.getLastLogin(context);
        int idUser = Preferences.getIDUser(context);


        mService.updateStatusUser("off", lastLogin, idUser)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            insertLogAction(context, "Logout", "");

                            sweetAlertDialog.dismissWithAnimation();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        sweetAlertDialog.setTitleText("Oops")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    }
                });
    }

    /**
     * Update Status User (OnStop | OnStart) in MainActivity
     */
    public static void updateStatusUser(Context context, String aksi) {
        String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        int idUser = Preferences.getIDUser(context);
        Preferences.setLastLogin(context, waktu);

        mService.updateStatusUser(aksi, waktu, idUser)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("updateStatusUser", response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("updateStatusUser", t.getMessage());
                    }
                });
    }

    /**
     * Log User
     * - Login
     *      Logout, Login, Login - gagal
     *
     * - Profile ()
     *      Password User, Update Profile User
     *
     * - List (List Adapter)
     *      Tambah, Edit, Hapus & Lihat Keputusan
     *
     * - Task
     *      Update Task, Send To Approval
     *
     * - Approval
     *      Revisi Task, Approve
     *
     * - Verifikasi
     *      Revisi Task, Verified
     */

    /**
     * Show Usages (Ctrl + insertLogAction (*click))
     */
    public static void insertLogAction(Context context, String aksi, String nip) {
        String nama;
        String waktu = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()).format(new Date());

        if (nip.equals(""))
            nama = Preferences.getUsername(context); /** Other */
        else
            nama = nip; /** Login Activity*/

        mService.insertLOGUser(nama, aksi, waktu)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            Log.d("LogUser", aksi + " - " + response.body().getMessage());
                            if (aksi.equals("Login")) {
                                context.startActivity(new Intent(context, MainActivity.class));
                                ((Activity) context).finish();
                                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            }
                            if (aksi.equals("Logout")) {
                                context.startActivity(new Intent(context, LoginActivity.class));
                                ((Activity) context).finish();
                                Preferences.clearSharePreferences(context);
                                EventBus.getDefault().postSticky(new RefreshLoadData(false, false, 0, ""));
                                EventBus.getDefault().postSticky(new SpinnerStatusMonitoring(false, ""));
                            }
                            if (aksi.contains("Download Chart")) {
                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Saving Successfully!")
                                        .setContentText("Checked on your gallery.")
                                        .setConfirmText(context.getResources().getString(R.string.textClose))
                                        .show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Log.d("LogUser", t.getMessage());
                    }
                });
    }

    /**
     * checkColor Status Monitoring for Adapter
     */
    public static void checkColorStatus(Context context, String status, CardView cvColorStatus, View vColorStatus, TextView tvStatus) {
        switch (status) {
            case "Revisi": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus0());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus0));
                tvStatus.setTextColor(Color.WHITE);
            }
            break;
            case "Belum Dikerjakan": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus1());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus1));
                tvStatus.setTextColor(Color.BLACK);
            }
            break;
            case "On Progress": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus2());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus2));
                tvStatus.setTextColor(Color.WHITE);
            }
            break;
            case "Selesai": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus3());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus3));
                tvStatus.setTextColor(Color.WHITE);
            }
            break;
            case "Approved": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus4());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus4));
                tvStatus.setTextColor(Color.WHITE);
            }
            break;
            case "Verified": {
                cvColorStatus.setCardBackgroundColor(Common.colorStatus5());
                vColorStatus.setBackground(context.getDrawable(R.color.colorStatus5));
                tvStatus.setTextColor(Color.WHITE);
            }
            break;
            default:
                break;
        }
    }

    /**
     * Text Color Status in List Fragment
     */
    public static void colorTextStatus(Context context, String queryStatusSelected, TextView tvStatusMonitoring) {
        switch (queryStatusSelected) {
            case "Belum Dikerjakan":
            case "Backlog": {
                tvStatusMonitoring.setTextColor(Color.BLACK);
            }
            break;
            case "On Progress": {
                tvStatusMonitoring.setTextColor(context.getResources().getColor(R.color.colorStatus2));
            }
            break;
            case "Selesai": {
                tvStatusMonitoring.setTextColor(context.getResources().getColor(R.color.colorStatus3));
            }
            break;
            case "Approved": {
                tvStatusMonitoring.setTextColor(context.getResources().getColor(R.color.colorStatus4));
            }
            break;
            case "Verified": {
                tvStatusMonitoring.setTextColor(context.getResources().getColor(R.color.colorStatus5));
            }
            break;
            default:
                break;
        }
    }

    /**
     * Check Last Status monitoring in Detail Keputusan
     */
    public static void checkDetails(Context context, MonitoringModel selectedMonitoring, TextView tvRencanaAksi,
                                    TextView tvTglSelesai, TextView tvTglApproved, TextView tvTglClosed, TextView tvKeteranganKomentar,
                                    TextView tvStatus, TextView tvLampiran, CardView cvDownload) {

        /** Last status -> tgl Selesai, Approved, Verifikasi*/
        switch (selectedMonitoring.getLAST_STATUS()) {
            case 0: {
                tvStatus.setTextColor(context.getResources().getColor(R.color.colorStatus0));
                tvTglSelesai.setText(context.getResources().getString(R.string.blmSelesai));
                tvTglApproved.setText(context.getResources().getString(R.string.blmApproved));
                tvTglClosed.setText(context.getResources().getString(R.string.blmVerifikasi));
            }
            break;
            case 1: {
                tvStatus.setTextColor(Color.BLACK);
                tvTglSelesai.setText(context.getResources().getString(R.string.blmSelesai));
                tvTglApproved.setText(context.getResources().getString(R.string.blmApproved));
                tvTglClosed.setText(context.getResources().getString(R.string.blmVerifikasi));
            }
            break;
            case 2: {
                tvStatus.setTextColor(context.getResources().getColor(R.color.colorStatus2));
                tvTglSelesai.setText(context.getResources().getString(R.string.blmSelesai));
                tvTglApproved.setText(context.getResources().getString(R.string.blmApproved));
                tvTglClosed.setText(context.getResources().getString(R.string.blmVerifikasi));
            }
            break;
            case 3: {
                tvStatus.setTextColor(context.getResources().getColor(R.color.colorStatus3));
                tvTglSelesai.setText(formatTgl(selectedMonitoring.getTGL_SELESAI()));
                tvTglApproved.setText(context.getResources().getString(R.string.blmApproved));
                tvTglClosed.setText(context.getResources().getString(R.string.blmVerifikasi));
            }
            break;
            case 4: {
                tvStatus.setTextColor(context.getResources().getColor(R.color.colorStatus4));
                tvTglSelesai.setText(formatTgl(selectedMonitoring.getTGL_SELESAI()));
                tvTglApproved.setText(formatTgl(selectedMonitoring.getTGL_APPROVAL()));
                tvTglClosed.setText(context.getResources().getString(R.string.blmVerifikasi));
            }
            break;
            case 5: {
                tvStatus.setTextColor(context.getResources().getColor(R.color.colorStatus5));
                tvTglSelesai.setText(formatTgl(selectedMonitoring.getTGL_SELESAI()));
                tvTglApproved.setText(formatTgl(selectedMonitoring.getTGL_APPROVAL()));
                tvTglClosed.setText(formatTgl(selectedMonitoring.getTGL_CLOSED()));
            }
            break;
            default:
                break;
        }
        tvStatus.setText(formatStatusMonitoringToString(selectedMonitoring.getLAST_STATUS()));

        /** Rencana Aksi */
        String rencanaAksi = selectedMonitoring.getRENCANA_AKSI();
        if (rencanaAksi.equals("") || TextUtils.isEmpty(rencanaAksi) || rencanaAksi.equals(context.getResources().getString(R.string.blmAdaRencanaAksi)))
            tvRencanaAksi.setText(context.getResources().getString(R.string.blmAdaRencanaAksi));
        else
            tvRencanaAksi.setText(selectedMonitoring.getRENCANA_AKSI());

        /** Keterangan / Komentar */
        String komentar = selectedMonitoring.getKOMENTAR();
        if (komentar.equals("") || komentar.equals("Tidak ada komentar"))
            tvKeteranganKomentar.setText(context.getResources().getString(R.string.komentarEmpty));
        else
            tvKeteranganKomentar.setText(selectedMonitoring.getKOMENTAR());

        /** Lampiran */
        String lampiran = selectedMonitoring.getLAMPIRAN();
        if (lampiran.equals("") || lampiran.equals("Tidak ada lampiran")) {
            tvLampiran.setText(context.getResources().getString(R.string.lampiranEmpty));
            cvDownload.setVisibility(View.GONE);
        } else {
            tvLampiran.setText(selectedMonitoring.getLAMPIRAN());
            cvDownload.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Check Color Status for TextView in UpdateKeputusan
     */
    public static int colorStatusMonitoringUpdateKeputusan(Context context, String status) {
        switch (status) {
            case "Revisi":
                return context.getResources().getColor(R.color.colorStatus0);
            case "Belum Dikerjakan":
                return context.getResources().getColor(android.R.color.black);
            case "On Progress":
                return context.getResources().getColor(R.color.colorStatus2);
            case "Selesai":
                return context.getResources().getColor(R.color.colorStatus3);
            case "Approved":
                return context.getResources().getColor(R.color.colorStatus4);
            case "Verified":
                return context.getResources().getColor(R.color.colorStatus5);
            default:
                return 0;
        }
    }
}
