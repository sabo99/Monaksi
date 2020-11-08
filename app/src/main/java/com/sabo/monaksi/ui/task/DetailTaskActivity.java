package com.sabo.monaksi.ui.task;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.FileUtils;
import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.UserModel;
import com.sabo.monaksi.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private Toolbar toolbar;
    private TextView tvNama, tvAgendaProgramID, tvTglTarget, tvKeputusan, tvPIC, tvApproval, tvVerifikator,
            tvRencanaAksi, tvTglSelesai, tvTglApproved, tvTglClosed, tvKeteranganKomentar, tvStatus, tvLampiran;
    private CardView cvDownload;
    private Button btnDownload;
    private SpeedDialView speedDialView;
    private SweetAlertDialog sweetLoading;

    private Uri selectedFileUri = null;
    private String path = "", fileExtension = "";
    private TextView tvFileLampiran;
    private Button btnChooseFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

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

        if (monitoringModel.getLAST_STATUS() < 3)
            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.action_update_task, R.drawable.ic_edit_document)
                            .setFabBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color))
                            .setFabSize(FloatingActionButton.SIZE_AUTO)
                            .setLabel(getResources().getString(R.string.actionUpdateRencanaAksi)).create());

        /** On Progress = 2 | Selesai = 3*/
        if (monitoringModel.getLAST_STATUS() == 2)
            speedDialView.addActionItem(
                    new SpeedDialActionItem.Builder(R.id.action_send_to_approval, R.drawable.ic_round_send)
                            .setFabBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color))
                            .setFabSize(FloatingActionButton.SIZE_AUTO)
                            .setLabel(getResources().getString(R.string.actionSendToApproval)).create());
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
                    case R.id.action_send_to_approval:
                        sendToApproval();
                        break;
                    case R.id.action_update_task:
                        showDialogUpdateTask();
                        speedDialView.close();
                        break;
                    case R.id.action_detail_status:
                        Common.showDetailStatusMonitoring(DetailTaskActivity.this);
                        speedDialView.close();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Send To Approval
     */
    private void sendToApproval() {
        SweetAlertDialog sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...").show();
        mService.getMonByID(Common.selectedMonitoring.getID_MON()).enqueue(new Callback<MonitoringModel>() {
            @Override
            public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MonitoringModel monitoringModel = response.body();

                    if (monitoringModel.getLAMPIRAN().equals("") || monitoringModel.getRENCANA_AKSI().equals("")) {
                        sweetLoading.setTitleText("Oops!")
                                .showCancelButton(false)
                                .setContentText("Link akan \"AKTIF setelah\" \nRencana Aksi & File Lampiran (Eviden) diinput.")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    } else {
                        speedDialView.close();
                        sweetLoading.dismissWithAnimation();
                        new SweetAlertDialog(DetailTaskActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setCustomImage(R.drawable.ic_round_warning)
                                .setTitleText("Send To Approval")
                                .setContentText(getString(R.string.contentSendTo))
                                .showCancelButton(true)
                                .setCancelText("Cancel")
                                .setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .setConfirmText("Send to Approval")
                                .setConfirmClickListener(sweetAlertDialog -> {

                                    /** Update Status Monitoring */
                                    sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                                    sweetAlertDialog.setTitleText("Please wait...")
                                            .showCancelButton(false)
                                            .showContentText(false)
                                            .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                                    String tglSelesai = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                                    int LAST_STATUS = 3; /** Selesai*/
                                    mService.updateStatusMon3(Common.selectedMonitoring.getID_MON(), tglSelesai, LAST_STATUS).enqueue(new Callback<ResponseModel>() {
                                        @Override
                                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                            if (response.isSuccessful()) {
                                                sweetAlertDialog.setTitleText("Success!")
                                                        .setContentText(getString(R.string.contentSendToApproval))
                                                        .setConfirmText("Close")
                                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                                            sweetAlertDialog1.dismissWithAnimation();
                                                            /** Refresh LoadData */
                                                            speedDialView.clearActionItems();
                                                            loadData();

                                                            /** Insert Log */
                                                            String aksi = "Send to Approval - " + monitoringModel.getID_MON();
                                                            Common.insertLogAction(DetailTaskActivity.this, aksi, "");
                                                        })
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                                            sweetAlertDialog.setTitleText("Failed!")
                                                    .setContentText(t.getMessage())
                                                    .setConfirmClickListener(sweetAlertDialog1 -> {
                                                        sweetAlertDialog1.dismissWithAnimation();
                                                    })
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                        }
                                    });
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MonitoringModel> call, Throwable t) {
                speedDialView.close();
                sweetLoading.dismissWithAnimation();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        speedDialView.inflate(R.menu.task);
        return super.onCreateOptionsMenu(menu);
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
        if (v.getId() == R.id.btnDownload) {
            checkPermissionDownload();
        }
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
        if (requestCode == Common.REQUEST_PERMISSION_UPLOAD && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            chooseFile();
        if (grantResults[0] == PackageManager.PERMISSION_DENIED)
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Dialog Update Task / Rencana Aksi
     */
    private void showDialogUpdateTask() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_task, null);
        EditText etRencanaAksi = view.findViewById(R.id.etRencanaAksi);
        tvFileLampiran = view.findViewById(R.id.tvFileLampiran);
        btnChooseFile = view.findViewById(R.id.btnChooseFile);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        SweetAlertDialog sweetUpdateTask = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(getResources().getString(R.string.actionUpdateRencanaAksi))
                .showContentText(false)
                .showCancelButton(true)
                .setCancelText("Batal")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    selectedFileUri = null;
                    path = "";
                    btnChooseFile.setText("Choose File");
                    tvFileLampiran.setText("No file chosen.");
                })
                .setConfirmText("Submit")
                .setConfirmClickListener(sweetAlertDialog -> {
                    String rencanaAksi = etRencanaAksi.getText().toString();
                    String extension = fileExtension;

                    Log.d("extension", extension);
                    /** Update Lampiran  */
                    if (selectedFileUri != null && !rencanaAksi.equals("")) {
                        if (extension.equals("pdf") || extension.equals("rar") || extension.equals("doc") || extension.equals("docx")) {

                            /** Check File Size Max 10 MB*/
                            long fileSize = FileUtils.getFileSizeFromUri(this, selectedFileUri);
                            if (fileSize > Common.MAX_SIZE_UPLOAD) {
                                tvFileLampiran.setBackground(getResources().getDrawable(R.drawable.border_choose_file_error));
                                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops!")
                                        .setContentText("File saat ini : " + fileSize / (1024 * 1024) + " MB \nFile anda terlalu besar!")
                                        .setConfirmText("Close")
                                        .show();
                            } else {
                                uploadFile_UpdateLampiran(sweetAlertDialog);
                                updateRencanaAksi(rencanaAksi, sweetAlertDialog);
                            }
                        } else {
                            tvFileLampiran.setBackground(getResources().getDrawable(R.drawable.border_choose_file_error));
                            new SweetAlertDialog(DetailTaskActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops!")
                                    .setContentText("File extension yang diperbolehkan hanya \n*(pdf, doc, docx, rar)")
                                    .setConfirmText("Close")
                                    .show();
                        }

                    } else {
                        /** Update Rencana Aksi  */
                        updateRencanaAksi(rencanaAksi, sweetAlertDialog);
                    }
                });

        sweetUpdateTask.setOnShowListener(dialog -> {
            progressBar.setVisibility(View.VISIBLE);

            mService.getMonByID(Common.selectedMonitoring.getID_MON()).enqueue(new Callback<MonitoringModel>() {
                @Override
                public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        MonitoringModel monitoringModel = response.body();

                        /** Set File Lampiran */
                        String lampiran = monitoringModel.getLAMPIRAN();
                        if (!lampiran.equals("")) {
                            tvFileLampiran.setText(monitoringModel.getLAMPIRAN());
                            btnChooseFile.setText("Change File");
                        }
                        if (lampiran.equals("Tidak ada lampiran")) {
                            tvFileLampiran.setText("No file chosen.");
                            btnChooseFile.setText("Choose File");
                        }

                        /** Set Rencana Aksi */
                        String rencanaAksi = monitoringModel.getRENCANA_AKSI();
                        if (!rencanaAksi.equals("") && !rencanaAksi.equalsIgnoreCase("Belum ada rencana aksi"))
                            etRencanaAksi.setText(monitoringModel.getRENCANA_AKSI());
                        else
                            etRencanaAksi.setText("");

                        progressBar.setVisibility(View.GONE);

                        /** Show Intent ACTION VIEW Lampiran From Common.selectedMonitoring */
                        if (selectedFileUri == null) {
                            tvFileLampiran.setOnClickListener(v -> {
                                Uri uri = Uri.parse(new StringBuilder(Common.DOWNLOAD_URL).append(lampiran).toString());
                                if (!lampiran.equals("") && !lampiran.equals("Tidak ada lampiran")) {
                                    String extension = lampiran.substring(lampiran.lastIndexOf('.') + 1);
                                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                                    switch (extension) {
                                        case "pdf":
                                            i.setDataAndType(uri, "application/pdf");
                                            break;
                                        case "doc":
                                        case "docx":
                                            i.setDataAndType(uri, "text/plain");
                                            break;
                                        case "rar":
                                            i.setData(uri);
                                            break;
                                    }
                                    startActivity(i);

                                    Log.d("xxx", extension);
                                } else
                                    Toast.makeText(DetailTaskActivity.this, "File Lampiran Kosong!", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<MonitoringModel> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            /** Choose File */
            btnChooseFile.setOnClickListener(v -> {
                checkPermissionUpload();
            });
        });
        sweetUpdateTask.show();
        LinearLayout linearLayout = sweetUpdateTask.findViewById(R.id.loading);
        linearLayout.setPadding(0, 30, 0, 50);
        TextView title = sweetUpdateTask.findViewById(R.id.title_text);
        title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        title.setTextColor(getResources().getColor(android.R.color.black));
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index + 1);
    }

    /**
     * Update Rencana Aksi
     */
    private void updateRencanaAksi(String rencanaAksi, SweetAlertDialog sweetAlertDialog) {
        if (rencanaAksi.equals(""))
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("Maaf, Rencana Aksi tidak boleh kosong.")
                    .show();
        else {
            updateOnProgress();
            mService.updateRencanaAksiMonitoring(Common.selectedMonitoring.getID_MON(), rencanaAksi).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        /** Refresh LoadData */
                        loadData();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            });
        }

    }

    /**
     * Update Status On Progress
     */
    private void updateOnProgress() {
        /** Update Status Monitoring -> On Progress*/
        String ID_MON = Common.selectedMonitoring.getID_MON(),
                komentar = Common.selectedMonitoring.getKOMENTAR(),
                aksi = "Update Task";

        int LAST_STATUS = 2; /** On Progress*/

        mService.updateStatusMonitoring(ID_MON, LAST_STATUS, komentar).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    /** Insert Log */
                    Common.insertLogAction(DetailTaskActivity.this, aksi, "");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    /**
     * Update Lampiran / Upload File
     *
     * @param sweetAlertDialog
     */
    boolean isFileUploaded = false;
    private void uploadFile_UpdateLampiran(SweetAlertDialog sweetAlertDialog) {
        if (!path.equals("") && !fileExtension.equals("")) {
            File file = new File(path);
            String ID_MON = Common.selectedMonitoring.getID_MON(), PROG_ID = Common.selectedMonitoring.getPROG_ID();
            String fileName = new StringBuilder("Monitoring_").append(ID_MON).append("_").append(PROG_ID).append(".").append(fileExtension).toString();
            String fileOld = Common.selectedMonitoring.getLAMPIRAN();

            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", fileName, requestBody);
            MultipartBody.Part fileID = MultipartBody.Part.createFormData("ID_MON", ID_MON);
            MultipartBody.Part oldFileName = MultipartBody.Part.createFormData("oldFileName", fileOld);

            SweetAlertDialog sweetUploading = new SweetAlertDialog(DetailTaskActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetUploading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            sweetUploading.setTitleText("Uploading...").show();

            mService.uploadFile(fileID, fileBody, oldFileName).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        sweetAlertDialog.dismissWithAnimation();
                        sweetUploading.dismissWithAnimation();

                        path = "";
                        selectedFileUri = null;
                        updateOnProgress();
                        isFileUploaded = true;
//                        sweetUploading
//                                .setContentText("fileOld : " + fileOld + "\nfileNew : " + fileName)
//                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    sweetAlertDialog.dismissWithAnimation();
                    sweetUploading.dismissWithAnimation();

                    path = "";
                    selectedFileUri = null;
                }
            });
            btnChooseFile.setText("Choose File");
            if (isFileUploaded) {
                loadData();
                cvDownload.setVisibility(View.VISIBLE);
                isFileUploaded = false;
            }

        }

    }

    private void checkPermissionUpload() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Common.REQUEST_PERMISSION_UPLOAD);
        } else
            chooseFile();
    }

    private void chooseFile() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/pdf");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, Common.PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Common.PICK_FILE_REQUEST && resultCode == RESULT_OK) {

            selectedFileUri = data.getData();

            if (selectedFileUri != null) {
                tvFileLampiran.setBackground(getResources().getDrawable(R.drawable.border_choose_file_success));

                /** File Path */
                path = FileUtils.getFilePathFromUri(this, selectedFileUri);
                Log.d("path", path);

                /** File Extension */
                fileExtension = FileUtils.getMimeType(this, selectedFileUri);
                Log.d("fileExtension", fileExtension);

                String fileName = FileUtils.getFileNameFromUri(selectedFileUri);
                //Log.d("fileName", fileName);

                /** Check File Name From Drive */
                if (fileName.contains("=") || fileName.contains("+")) {
                    String x = String.valueOf(System.currentTimeMillis());
                    String xc = x.substring(0, 4);
                    String xr = xc.replace(xc.substring(0, 1), "6");
                    Log.d("fileNameDrive", xr);
                    tvFileLampiran.setText(xr + "." + fileExtension);
                }
                /** File Name Without Extension from File Manager & other */
                else if (fileName.contains("." + fileExtension)) {
                    String dx = fileName;
                    String extension = "." + fileExtension;
                    String rep = dx.replace(extension, "");
                    Log.d("fileName", rep);
                    tvFileLampiran.setText(rep + "." + fileExtension);
                } else /** From First Intent show Download */ {
                    Log.d("fileNameDownload", fileName);
                    tvFileLampiran.setText(fileName + "." + fileExtension);
                }

                btnChooseFile.setText("Change File");

                tvFileLampiran.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, selectedFileUri);
                    startActivity(intent);
                });
            }

        }
    }


}