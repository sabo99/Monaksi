package com.sabo.monaksi.ui.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.sabo.monaksi.Model.UserModel;
import com.sabo.monaksi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private Toolbar toolbar;
    private SweetAlertDialog sweetLoading;
    private TextView tvNama, tvAgendaProgramID, tvTglTarget, tvKeputusan, tvPIC, tvApproval, tvVerifikator,
            tvRencanaAksi, tvTglSelesai, tvTglApproved, tvTglClosed, tvKeteranganKomentar, tvStatus, tvLampiran;
    private CardView cvDownload;
    private Button btnDownload;
    private SpeedDialView speedDialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        mService = Common.getAPI();
        initViews();

        loadData();
    }

    private void loadData() {
        sweetLoading.show();

        /** Nama Agenda & Program ID */
        mService.getNamaAgenda(Common.selectedMonitoring.getID_AGENDA()).enqueue(new Callback<AgendaModel>() {
            @Override
            public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                if (response.isSuccessful() && response.body().getNama_agenda() != null)
                    tvAgendaProgramID.setText(new StringBuilder(response.body().getNama_agenda()).append(" / ").append(Common.selectedMonitoring.getPROG_ID()).toString());
                else
                    tvAgendaProgramID.setText(Common.selectedMonitoring.getPROG_ID());
            }

            @Override
            public void onFailure(Call<AgendaModel> call, Throwable t) {

            }
        });

        /** Get PIC */
        mService.getUserInformation(Common.selectedMonitoring.getPIC()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(Common.selectedMonitoring.getPIC()).append(")").toString();
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
        mService.getUserInformation(Common.selectedMonitoring.getAPPROVAL()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(Common.selectedMonitoring.getAPPROVAL()).append(")").toString();
                    tvApproval.setText(s);
                } else
                    tvApproval.setText("");
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        /** Get Verifikator */
        mService.getUserInformation(Common.selectedMonitoring.getVERIFIKATOR()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body().getNama() != null) {
                    String s = new StringBuilder(response.body().getNama()).append(" (").append(Common.selectedMonitoring.getVERIFIKATOR()).append(")").toString();
                    tvVerifikator.setText(s);
                } else
                    tvVerifikator.setText("");
                sweetLoading.dismissWithAnimation();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                sweetLoading.dismissWithAnimation();
            }
        });

        /** Get Tgl Target, Keputusan, Rencana Aksi, Tgl Selesai, Tgl Approved, Tgl Closed, Keterangan/Komentar, Lampiran */
        tvTglTarget.setText(Common.formatTgl(Common.selectedMonitoring.getTGL_TARGET()));
        tvKeputusan.setText(Common.selectedMonitoring.getKEPUTUSAN());

        Common.checkDetails(this, Common.selectedMonitoring, tvRencanaAksi, tvTglSelesai, tvTglApproved, tvTglClosed,
                tvKeteranganKomentar, tvStatus, tvLampiran, cvDownload);

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.action_detail_status, R.drawable.ic_outline_info)
                        .setFabBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color))
                        .setFabSize(FloatingActionButton.SIZE_AUTO)
                        .setLabel(getResources().getString(R.string.actionDetailStatus)).create());
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
        sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...");

        btnDownload.setOnClickListener(this);

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.action_detail_status:
                        Common.showDetailStatusMonitoring(DetailListActivity.this);
                        speedDialView.close();
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
    public void onClick(View v) {
        if (v.getId() == R.id.btnDownload) {
            checkPermissionDownload();
        }
    }

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
        if (grantResults[0] == PackageManager.PERMISSION_DENIED)
            Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
    }
}