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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
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

public class UpdateKeputusanActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private Toolbar toolbar;
    private SweetAlertDialog sweetLoading;
    private EditText etKeputusan;
    private TextView tvProgramID, tvNamaAgenda, tvTglTarget, tvPIC, tvApproval, tvVerifikator, tvStatusMonitoring;
    private Spinner spNamaAgenda, spPIC, spApproval, spVerifikator, spStatusMonitoring;
    private LinearLayout statusMonitoring;
    private Button btnCancel, btnUpdate;

    private final List<String> agendaList = new ArrayList<>();
    private final List<String> userList = new ArrayList<>();
    private String resultPIC, resultApproval, resultVerifikator, resultKeputusan, resultTglTarget, resultTglReport, resultProgramID, resultTglUpdates;
    private int resultIDAgenda = 0, resultStatusMonitoring = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_keputusan);

        mService = Common.getAPI();
        initViews();

        sweetLoading.show();
        loadData();
    }

    private void loadData() {
        String level = Preferences.getLevel(this);
        if (level.equals("Admin"))
            statusMonitoring.setVisibility(View.VISIBLE);
        if (level.equals("Operator"))
            statusMonitoring.setVisibility(View.GONE);

        MonitoringModel monitoringModel = Common.selectedMonitoring;

        tvProgramID.setText(monitoringModel.getPROG_ID());
        etKeputusan.setText(monitoringModel.getKEPUTUSAN());
        tvTglTarget.setText(Common.formatTgl(monitoringModel.getTGL_TARGET()));

        resultTglTarget = monitoringModel.getTGL_TARGET();

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.status_mon, R.layout.spinner_transparent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatusMonitoring.setAdapter(adapter);

        /** PIC */
        mService.getUserInformation(monitoringModel.getPIC()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful())
                    tvPIC.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        /** Approval */
        mService.getUserInformation(monitoringModel.getAPPROVAL()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful())
                    tvApproval.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });

        /** Verifikator */
        mService.getUserInformation(monitoringModel.getVERIFIKATOR()).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful())
                    tvVerifikator.setText(response.body().getNama());
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });


        /** Spinner Nama Agenda */
        int ID_SUBRAPAT = Integer.parseInt(monitoringModel.getID_SUBRAPAT());
        mService.getAllAgenda(ID_SUBRAPAT).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body().getAllAgenda() != null) {
                    agendaList.add(0, Common.selectDefault);
                    for (AgendaModel agenda : response.body().getAllAgenda()) {
                        String namaAgenda = agenda.getNama_agenda();
                        agendaList.add(namaAgenda);
                    }

                    ArrayAdapter<String> adapterAgenda = new ArrayAdapter<>(UpdateKeputusanActivity.this, R.layout.spinner_transparent, agendaList);
                    adapterAgenda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spNamaAgenda.setAdapter(adapterAgenda);
                } else
                    tvNamaAgenda.setText("-");
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
                    userList.add(0, Common.selectDefault);
                    for (UserModel user : response.body().getAllUser()) {
                        String namaUser = user.getNama();
                        userList.add(namaUser);
                    }

                    ArrayAdapter<String> adapterUser = new ArrayAdapter<>(UpdateKeputusanActivity.this, R.layout.spinner_transparent, userList);
                    adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        /** Spinner NamaAgenda */
        spNamaAgenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String namaAgenda = String.valueOf(parent.getItemAtPosition(position));

                if (!namaAgenda.equals(Common.selectDefault)) {
                    tvNamaAgenda.setText(namaAgenda);
                    mService.getIdAgenda(namaAgenda).enqueue(new Callback<AgendaModel>() {
                        @Override
                        public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                resultIDAgenda = response.body().getId_agenda();
                            }
                        }

                        @Override
                        public void onFailure(Call<AgendaModel> call, Throwable t) {

                        }
                    });
                } else {
                    resultIDAgenda = Integer.parseInt(monitoringModel.getID_AGENDA());
                    mService.getNamaAgenda(monitoringModel.getID_AGENDA()).enqueue(new Callback<AgendaModel>() {
                        @Override
                        public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                tvNamaAgenda.setText(response.body().getNama_agenda());
                            }
                        }

                        @Override
                        public void onFailure(Call<AgendaModel> call, Throwable t) {

                        }
                    });
                }
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

                if (!PIC.equals(Common.selectDefault)) {
                    tvPIC.setText(PIC);
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
                } else {
                    resultPIC = monitoringModel.getPIC();
                    mService.getUserInformation(monitoringModel.getPIC()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                tvPIC.setText(response.body().getNama());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {

                        }
                    });
                }
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

                if (!Approval.equals(Common.selectDefault)) {
                    tvApproval.setText(Approval);
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
                } else {
                    resultApproval = monitoringModel.getAPPROVAL();
                    mService.getUserInformation(monitoringModel.getAPPROVAL()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                tvApproval.setText(response.body().getNama());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {

                        }
                    });
                }
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

                if (!Verifikator.equals(Common.selectDefault)) {
                    tvVerifikator.setText(Verifikator);
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
                } else {
                    resultVerifikator = monitoringModel.getVERIFIKATOR();
                    mService.getUserInformation(monitoringModel.getVERIFIKATOR()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                tvVerifikator.setText(response.body().getNama());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /** Spinner Status Monitoring */
        spStatusMonitoring.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = String.valueOf(parent.getItemAtPosition(position));
                if (!status.equals(Common.selectDefault)) {
                    tvStatusMonitoring.setText(status);
                    resultStatusMonitoring = Common.formatStatusMonitoringToInt(status);
                } else {
                    tvStatusMonitoring.setText(Common.formatStatusMonitoringToString(monitoringModel.getLAST_STATUS()));
                    resultStatusMonitoring = monitoringModel.getLAST_STATUS();
                }
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
        tvNamaAgenda = findViewById(R.id.tvNamaAgenda);
        tvTglTarget = findViewById(R.id.tvTglTarget);
        tvPIC = findViewById(R.id.tvPIC);
        tvApproval = findViewById(R.id.tvApproval);
        tvVerifikator = findViewById(R.id.tvVerifikator);
        tvStatusMonitoring = findViewById(R.id.tvStatusMonitoring);
        spNamaAgenda = findViewById(R.id.spNamaAgenda);
        spPIC = findViewById(R.id.spPIC);
        spApproval = findViewById(R.id.spApproval);
        spVerifikator = findViewById(R.id.spVerifikator);
        spStatusMonitoring = findViewById(R.id.spStatusMonitoring);
        statusMonitoring = findViewById(R.id.statusMonitoring);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpdate = findViewById(R.id.btnUpdate);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...");

        tvTglTarget.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
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
            case R.id.btnUpdate:
                updateKeputusan();
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
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void updateKeputusan() {
        resultProgramID = tvProgramID.getText().toString();
        resultKeputusan = etKeputusan.getText().toString();
        resultTglReport = Common.selectedMonitoring.getTGL_REPORT();
        resultTglUpdates = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).format(new Date());

        String aksi = "Update Keputusan " + resultKeputusan;

        String level = Preferences.getLevel(this);

        if (checkForm(true, etKeputusan.getText().toString())){
            etKeputusan.setBackground(getResources().getDrawable(R.drawable.border_edit_text_success));
            SweetAlertDialog sweetUpdate = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Update Keputusan!")
                    .setContentText("Apakah anda yakin ingin mengubah keputusan ini.")
                    .showCancelButton(true)
                    .setCancelText("Cancel")
                    .setCancelClickListener(sweetAlertDialog -> {
                        sweetAlertDialog.dismiss();
                    })
                    .setConfirmText("Update")
                    .setConfirmClickListener(sweetAlertDialog -> {

                        sweetAlertDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                        sweetAlertDialog.setTitleText("Updating...").showCancelButton(false).showContentText(false)
                                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

                        int ID_MON = Integer.valueOf(Common.selectedMonitoring.getID_MON()),
                                ID_SUBRAPAT = Integer.valueOf(Common.selectedMonitoring.getID_SUBRAPAT()),
                                STATUS_MONITORING = Common.selectedMonitoring.getLAST_STATUS();


                        /** Update by Admin Change Status Monitoring */
                        if (level.equals("Admin")){
                            mService.updateKeputusan(ID_MON, ID_SUBRAPAT, resultIDAgenda, resultProgramID, resultTglReport, resultTglTarget, resultKeputusan,
                                    resultPIC, resultApproval, resultVerifikator, resultStatusMonitoring, resultTglUpdates).enqueue(new Callback<ResponseModel>() {
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

                                        Common.insertLogAction(UpdateKeputusanActivity.this, aksi, "");

                                        /** Set Default */
                                        resultStatusMonitoring = -1;
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
                        }

                        /** Update by Operator NOT Change Status Monitoring */
                        if (level.equals("Operator")) {
                            mService.updateKeputusan(ID_MON, ID_SUBRAPAT, resultIDAgenda, resultProgramID, resultTglReport, resultTglTarget, resultKeputusan,
                                    resultPIC, resultApproval, resultVerifikator, STATUS_MONITORING, resultTglUpdates).enqueue(new Callback<ResponseModel>() {
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

                                        Common.insertLogAction(UpdateKeputusanActivity.this, aksi, "");

                                        /** Set Default */
                                        resultStatusMonitoring = -1;
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
                        }


                    });
            sweetUpdate.setCanceledOnTouchOutside(false);
            sweetUpdate.show();
            Button confirm = sweetUpdate.findViewById(R.id.confirm_button), cancel = sweetUpdate.findViewById(R.id.cancel_button);
            confirm.setBackgroundResource(R.drawable.blue_button_background);
            cancel.setBackgroundResource(R.drawable.gray_button_background);
        }
    }

    private boolean checkForm(boolean checked, String resultKeputusan) {
        if (TextUtils.isEmpty(resultKeputusan)) {
            checked = false;
            etKeputusan.setBackground(getResources().getDrawable(R.drawable.border_edit_text_error));
            etKeputusan.setError("Keputusan tidak boleh kosong!");
        }
        return checked;
    }
}