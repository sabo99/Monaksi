package com.sabo.monaksi.ActionKeputusan;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.UserModel;
import com.sabo.monaksi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKeputusanActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private Toolbar toolbar;
    private SweetAlertDialog sweetLoading;
    private EditText etKeputusan;
    private TextView tvProgramID, tvTglTarget, tvTglTarget_ErrorMessage;
    private View vTglTarget;
    private Spinner spNamaAgenda, spPIC, spApproval, spVerifikator;
    private Button btnCancel, btnTambah;

    private final List<String> agendaList = new ArrayList<>();
    private final List<String> userList = new ArrayList<>();
    private String resultPIC, resultApproval, resultVerifikator, resultKeputusan, resultTglTarget, resultTglReport, resultProgramID, resultTglUpdates;
    private int resultIDAgenda = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_keputusan);

        mService = Common.getAPI();
        initViews();

        sweetLoading.show();
        loadData();
    }

    private void loadData() {
        int idSubrapat = Common.idSubrapat;
        String namaRapat = Common.namaRapat;
        String namaSubrapat = Common.namaSubrapat;

        resultTglTarget = "";

        /** ProgID */
        mService.getPROGID(namaRapat, namaSubrapat, idSubrapat).enqueue(new Callback<MonitoringModel>() {
            @Override
            public void onResponse(Call<MonitoringModel> call, Response<MonitoringModel> response) {
                if (response.isSuccessful()) {
                    tvProgramID.setText(response.body().getPROG_ID());
                }
            }

            @Override
            public void onFailure(Call<MonitoringModel> call, Throwable t) {

            }
        });

        /** Spinner Nama Agenda */
        mService.getAllAgenda(idSubrapat).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getAllAgenda() != null) {
                    for (AgendaModel agenda : response.body().getAllAgenda()) {
                        String namaAgenda = agenda.getNama_agenda();
                        agendaList.add(namaAgenda);
                    }

                    ArrayAdapter<String> adapterAgenda = new ArrayAdapter<>(TambahKeputusanActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, agendaList);

                    spNamaAgenda.setAdapter(adapterAgenda);
                } else {
                    List<String> empty = new ArrayList<>();
                    empty.add("-");
                    ArrayAdapter<String> adapterAgenda = new ArrayAdapter<>(TambahKeputusanActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, empty);
                    spNamaAgenda.setAdapter(adapterAgenda);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

        /** Spinner PIC, Approval, Verifikator */
        mService.getAllUser().enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getAllUser() != null) {
                    for (UserModel user : response.body().getAllUser()) {
                        String namaUser = user.getNama();
                        userList.add(namaUser);
                    }

                    ArrayAdapter<String> adapterUser = new ArrayAdapter<>(TambahKeputusanActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, userList);

                    spPIC.setAdapter(adapterUser);
                    spApproval.setAdapter(adapterUser);
                    spVerifikator.setAdapter(adapterUser);

                    sweetLoading.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                sweetLoading.setTitleText("Oops!")
                        .setContentText(t.getMessage())
                        .setConfirmText("Close")
                        .changeAlertType(SweetAlertDialog.WARNING_TYPE);
            }
        });


        /** Get Value From Spinner */
        /** Spinner Agenda */
        spNamaAgenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String namaAgenda = String.valueOf(parent.getItemAtPosition(position));
                mService.getIdAgenda(namaAgenda).enqueue(new Callback<AgendaModel>() {
                    @Override
                    public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                        if (response.isSuccessful())
                            if (response.body() != null)
                                resultIDAgenda = response.body().getId_agenda();
                            else
                                resultIDAgenda = 0;
                        else
                            resultIDAgenda = 0;

                    }

                    @Override
                    public void onFailure(Call<AgendaModel> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** Spinner PIC */
        spPIC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String PIC = String.valueOf(parent.getItemAtPosition(position));

                mService.getUserByNama(PIC).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            resultPIC = response.body().getUsername();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** Spinner Approval */
        spApproval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Approval = String.valueOf(parent.getItemAtPosition(position));

                mService.getUserByNama(Approval).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            resultApproval = response.body().getUsername();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** Spinner Verifikator */
        spVerifikator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Verifikator = String.valueOf(parent.getItemAtPosition(position));

                mService.getUserByNama(Verifikator).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            resultVerifikator = response.body().getUsername();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        tvProgramID = findViewById(R.id.tvProgramID);
        etKeputusan = findViewById(R.id.etKeputusan);
        tvTglTarget = findViewById(R.id.tvTglTarget);
        vTglTarget = findViewById(R.id.vTglTarget);
        tvTglTarget_ErrorMessage = findViewById(R.id.tvTglTarget_ErrorMessage);
        spNamaAgenda = findViewById(R.id.spNamaAgenda);
        spPIC = findViewById(R.id.spPIC);
        spApproval = findViewById(R.id.spApproval);
        spVerifikator = findViewById(R.id.spVerifikator);
        btnCancel = findViewById(R.id.btnCancel);
        btnTambah = findViewById(R.id.btnTambah);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...");

        tvTglTarget.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnTambah.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTglTarget:
                openDateTimePicker();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnTambah:
                tambahKeputusan();
                break;
        }
    }

    /**
     * Get Tgl Target From DateTimePicker
     */
    private void openDateTimePicker() {
        int mMonth, mDay, mYear;
        final Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mMonth = c.get(Calendar.MONTH);
        mYear = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view1, year1, month1, dayOfMonth) -> {
                    resultTglTarget = new StringBuilder()
                            .append(year1).append("-").append(month1 + 1).append("-").append(dayOfMonth).toString();
                    tvTglTarget.setText(Common.formatTgl(resultTglTarget));
                    vTglTarget.setBackgroundColor(getResources().getColor(R.color.blue_btn_bg_color));
                    tvTglTarget_ErrorMessage.setVisibility(View.GONE);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /**
     * Tambah Keputusan
     */
    private void tambahKeputusan() {
        resultProgramID = tvProgramID.getText().toString();
        resultKeputusan = etKeputusan.getText().toString();
        resultTglReport = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        resultTglUpdates = "0000-00-00 00:00:00";

        if (checkForm(true, resultTglTarget, tvTglTarget.getText().toString(), resultKeputusan)){
            etKeputusan.setBackground(getResources().getDrawable(R.drawable.border_edit_text_success));
            SweetAlertDialog sweetTambah = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Tambah Keputusan!")
                    .setContentText("Apakah anda yakin ingin menambahkan keputusan ini.")
                    .showCancelButton(true)
                    .setCancelText("Cancel")
                    .setCancelClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                    })
                    .setConfirmText("Tambah")
                    .setConfirmClickListener(sweetAlertDialog -> {

                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                        sweetAlertDialog.setTitleText("Please wait...").showCancelButton(false).showContentText(false)
                                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                        mService.inputKeputusan(Common.idSubrapat, resultIDAgenda, resultProgramID, resultTglReport, resultTglTarget, resultKeputusan, resultPIC, resultApproval, resultVerifikator,
                                1, resultTglUpdates).enqueue(new Callback<ResponseModel>() {
                            @Override
                            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                                if (response.isSuccessful()) {
                                    sweetAlertDialog.setTitleText("Success!")
                                            .setContentText(response.body().getMessage())
                                            .setConfirmText("Close")
                                            .setConfirmClickListener(sweetAlertDialog1 -> {
                                                finish();
                                            })
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                    String aksi = "Tambah Keputusan " + resultKeputusan;
                                    Common.insertLogAction(TambahKeputusanActivity.this, aksi, "");

                                    /** Set Default */
                                    resultIDAgenda = 0;
                                    resultTglTarget = "";
                                    resultKeputusan = "";
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseModel> call, Throwable t) {
                                sweetAlertDialog.setTitleText("Oops!")
                                        .setContentText(t.getMessage())
                                        .setConfirmText("Close")
                                        .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                            }
                        });
                    });
            sweetTambah.setCanceledOnTouchOutside(true);
            sweetTambah.show();
            Button confirm = sweetTambah.findViewById(R.id.confirm_button), cancel = sweetTambah.findViewById(R.id.cancel_button);
            confirm.setBackgroundResource(R.drawable.blue_button_background);
            cancel.setBackgroundResource(R.drawable.gray_button_background);
        }
    }

    private boolean checkForm(boolean checked, String resultTglTarget, String tvTglTarget, String resultKeputusan) {
        if (resultTglTarget.equals("") || tvTglTarget.equals("Pilih Tanggal Target")) {
            checked = false;
            vTglTarget.setBackgroundColor(getResources().getColor(R.color.red_btn_bg_color));
            tvTglTarget_ErrorMessage.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(resultKeputusan)) {
            checked = false;
            etKeputusan.setBackground(getResources().getDrawable(R.drawable.border_edit_text_error));
            etKeputusan.setError("Keputusan tidak boleh kosong!");
        }
        return checked;
    }
}