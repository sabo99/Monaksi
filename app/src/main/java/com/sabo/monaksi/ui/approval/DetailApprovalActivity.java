package com.sabo.monaksi.ui.approval;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.UserModel;
import com.sabo.monaksi.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailApprovalActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private Toolbar toolbar;
    private TextView tvNama, tvAgendaProgramID, tvTglTarget, tvKeputusan, tvPIC, tvApproval, tvVerifikator,
            tvRencanaAksi, tvTglSelesai, tvTglApproved, tvTglClosed, tvKeteranganKomentar, tvStatus, tvLampiran;
    private CardView cvDownload;
    private Button btnDownload;
    private SpeedDialView speedDialView;
    private SweetAlertDialog sweetLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_approval);

        mService = Common.getAPI();
        initViews();

        sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...");
        sweetLoading.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        mService.getMonByID(Common.selectedMonitoring.getID_MON())
                .enqueue(new Callback<MonitoringModel>() {
                    @Override
                    public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
                        if (response.isSuccessful() && response.body() != null)
                            setData(response.body());
                    }

                    @Override
                    public void onFailure(Call<MonitoringModel> call, Throwable t) {
                        sweetLoading.dismissWithAnimation();
                    }
                });
    }

    private void setData(MonitoringModel monitoringModel) {
        /** Nama Agenda & Program ID */
        mService.getNamaAgenda(monitoringModel.getID_AGENDA()).enqueue(new Callback<AgendaModel>() {
            @Override
            public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                if (response.isSuccessful() && response.body().getNama_agenda() != null)
                    tvAgendaProgramID.setText(new StringBuilder(response.body().getNama_agenda()).append(" / ").append(monitoringModel.getPROG_ID()).toString());
                else
                    tvAgendaProgramID.setText(monitoringModel.getPROG_ID());
            }

            @Override
            public void onFailure(Call<AgendaModel> call, Throwable t) {

            }
        });

        /** Get PIC */
        mService.getUserInformation(monitoringModel.getPIC()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(monitoringModel.getPIC()).append(")").toString();
                    tvPIC.setText(s);
                    tvNama.setText(s);
                } else {
                    tvPIC.setText("");
                    tvNama.setText("");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        /** Get Approval */
        mService.getUserInformation(monitoringModel.getAPPROVAL()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(monitoringModel.getAPPROVAL()).append(")").toString();
                    tvApproval.setText(s);
                } else
                    tvApproval.setText("");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        /** Get Verifikator */
        mService.getUserInformation(monitoringModel.getVERIFIKATOR()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(monitoringModel.getVERIFIKATOR()).append(")").toString();
                    tvVerifikator.setText(s);
                } else
                    tvVerifikator.setText("");

                new Handler().postDelayed(() -> {
                    sweetLoading.dismiss();
                }, 500);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                sweetLoading.dismiss();
            }
        });

        /** Get Tgl Target, Keputusan, Rencana Aksi, Tgl Selesai, Tgl Approved, Tgl Closed, Keterangan/Komentar, Lampiran */
        tvTglTarget.setText(Common.formatTgl(monitoringModel.getTGL_TARGET()));
        tvKeputusan.setText(monitoringModel.getKEPUTUSAN());

        Common.checkDetails(this, monitoringModel, tvRencanaAksi, tvTglSelesai, tvTglApproved, tvTglClosed,
                tvKeteranganKomentar, tvStatus, tvLampiran, cvDownload);

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.action_detail_status, R.drawable.ic_outline_info)
                        .setFabBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color))
                        .setFabSize(FloatingActionButton.SIZE_AUTO)
                        .setLabel(getResources().getString(R.string.actionDetailStatus)).create());

        if (monitoringModel.getLAST_STATUS() == 3){
            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.action_send_to_verifikator, R.drawable.ic_round_send)
                            .setFabBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color))
                            .setFabSize(FloatingActionButton.SIZE_AUTO)
                            .setLabel(getResources().getString(R.string.actionSendToVerifikator)).create());

            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.action_revisi, R.drawable.ic_edit_document)
                            .setFabBackgroundColor(getResources().getColor(R.color.red_btn_bg_color))
                            .setFabSize(FloatingActionButton.SIZE_AUTO)
                            .setLabel(getResources().getString(R.string.actionRevisi)).create());

        }
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvNama = findViewById(R.id.tvNama);
        tvAgendaProgramID = findViewById(R.id.tvAgendaProgramID);
        tvTglTarget = findViewById(R.id.tvTglTarget);
        tvKeputusan = findViewById(R.id.tvKeputusan);
        tvPIC = findViewById(R.id.tvPIC);
        tvApproval = findViewById(R.id.tvApproval);
        tvVerifikator = findViewById(R.id.tvVerifikator);
        tvRencanaAksi = findViewById(R.id.tvRencanaAksi);
        tvTglSelesai = findViewById(R.id.tvTglSelesai);
        tvTglApproved = findViewById(R.id.tvTglApproved);
        tvTglClosed = findViewById(R.id.tvTglClosed);
        tvKeteranganKomentar = findViewById(R.id.tvKeteranganKomentar);
        tvStatus = findViewById(R.id.tvStatus);
        tvLampiran = findViewById(R.id.tvLampiran);
        btnDownload = findViewById(R.id.btnDownload);
        cvDownload = findViewById(R.id.cvDownload);
        speedDialView = findViewById(R.id.speedDialView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDownload.setOnClickListener(this);

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_detail_status:
                        Common.showDetailStatusMonitoring(DetailApprovalActivity.this);
                        speedDialView.close();
                        break;
                    case R.id.action_send_to_verifikator:
                        showDialogKomentar(true, false);
                        break;
                    case R.id.action_revisi:
                        showDialogKomentar(false, true);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (speedDialView.isOpen())
            speedDialView.close();
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnDownload)
            checkPermissionDownload();
    }

    /**
     * Check Permission Download File lampiran
     */
    private void checkPermissionDownload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.REQUEST_PERMISSION_DOWNLOAD);
        else
            download();
    }

    private void download() {
        Uri uri = Uri.parse(new StringBuilder(Common.DOWNLOAD_URL).append(Common.selectedMonitoring.getLAMPIRAN()).toString());
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Common.REQUEST_PERMISSION_DOWNLOAD && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            download();
//        if (requestCode == Common.REQUEST_PERMISSION_UPLOAD && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            chooseFile();
        if (grantResults[0] == PackageManager.PERMISSION_DENIED)
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Show Dialog Komentar
     */
    private void showDialogKomentar(boolean sendToVerifikator, boolean revisi) {
        speedDialView.close();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_komentar, null);
        EditText etKomentar = view.findViewById(R.id.etKomentar);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        SweetAlertDialog sweetKomentar = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Tambah Komentar")
                .showContentText(false)
                .showCancelButton(true)
                .setCancelText("Cancel")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .setConfirmText("Submit")
                .setConfirmClickListener(sweetAlertDialogMain -> {
                    String komentar = etKomentar.getText().toString();
                    /** Send To Verifikator */
                    if (sendToVerifikator) {
                        new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setCustomImage(R.drawable.ic_round_warning)
                                .setTitleText("Send To Verifikator!")
                                .setContentText(getString(R.string.contentSendTo))
                                .showCancelButton(true)
                                .setCancelText("Cancel")
                                .setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .setConfirmText("Send to Verifikator")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sendToVerifikator(komentar, sweetAlertDialogMain, sweetAlertDialog);
                                })
                                .show();
                    }

                    /** Revisi */
                    if (revisi) {
                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Revisi!")
                                .setContentText(getString(R.string.contentRevisi))
                                .showCancelButton(true)
                                .setCancelText("Tidak")
                                .setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .setConfirmText("Ya")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    revisiKeputusan(komentar, sweetAlertDialogMain, sweetAlertDialog);
                                })
                                .show();
                    }
                });
        sweetKomentar.setOnShowListener(dialog -> {
            progressBar.setVisibility(View.VISIBLE);
            mService.getMonByID(Common.selectedMonitoring.getID_MON()).enqueue(new Callback<MonitoringModel>() {
                @Override
                public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        etKomentar.setText(response.body().getKOMENTAR());
                    }
                }

                @Override
                public void onFailure(Call<MonitoringModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        });
        sweetKomentar.show();
        LinearLayout linearLayout = sweetKomentar.findViewById(R.id.loading);
        TextView title = sweetKomentar.findViewById(R.id.title_text);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setTextColor(Color.BLACK);
        linearLayout.setPadding(0, 30, 0, 50);
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index + 1);

    }

    /** Revisi Keputusan */
    private void revisiKeputusan(String komentar, SweetAlertDialog sweetAlertDialogMain, SweetAlertDialog sweetAlertDialog) {
        String ID_MON = Common.selectedMonitoring.getID_MON();
        int LAST_STATUS = 0;  /** Revised*/


        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Please wait...")
                .showContentText(false)
                .showCancelButton(false)
                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

        mService.updateStatusMonitoring(ID_MON, LAST_STATUS, komentar).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    sweetAlertDialog.setTitleText("Success!")
                            .showCancelButton(false)
                            .setContentText(getString(R.string.contentSuksesRevisi))
                            .setConfirmText("Close")
                            .setConfirmClickListener(sweetAlertDialog1 -> {
                                sweetAlertDialogMain.dismissWithAnimation();
                                sweetAlertDialog1.dismissWithAnimation();
                            })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    /** Refresh LoadData */
                    speedDialView.clearActionItems();
                    loadData();

                    /** Insert Log */
                    String aksi = "Revised Task - " + Common.selectedMonitoring.getID_MON();
                    Common.insertLogAction(DetailApprovalActivity.this, aksi, "");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    /**
     * Send To Verifikator
     */
    private void sendToVerifikator(String komentar, SweetAlertDialog sweetAlertDialogMain, SweetAlertDialog sweetAlertDialog) {
        String tgl_approved = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String ID_MON = Common.selectedMonitoring.getID_MON();
        int LAST_STATUS = 4;  /** Approved*/

        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.setTitleText("Please wait...")
                .showContentText(false)
                .showCancelButton(false)
                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

        mService.updateStatusMon4(ID_MON, tgl_approved, komentar, LAST_STATUS).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    sweetAlertDialog.setTitleText("Success!")
                            .showCancelButton(false)
                            .setContentText(getString(R.string.contentSendToVerifikator))
                            .setConfirmText("Close")
                            .setConfirmClickListener(sweetAlertDialog1 -> {
                                sweetAlertDialogMain.dismissWithAnimation();
                                sweetAlertDialog1.dismissWithAnimation();
                            })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    /** Refresh LoadData */
                    speedDialView.clearActionItems();
                    loadData();

                    /** Insert Log */
                    String aksi = "Approve Task - " + Common.selectedMonitoring.getID_MON();
                    Common.insertLogAction(DetailApprovalActivity.this, aksi, "");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                sweetAlertDialog.setTitleText("Error!")
                        .showCancelButton(false)
                        .setContentText(t.getMessage())
                        .setConfirmText("Close")
                        .setConfirmClickListener(sweetAlertDialog1 -> {
                            sweetAlertDialogMain.dismissWithAnimation();
                            sweetAlertDialog1.dismissWithAnimation();
                        })
                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            }
        });
    }
}