package com.sabo.monaksi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private APIRequestData mService;
    private EditText etNIP, etPassword;
    private TextInputLayout tilPassword;
    private Button btnLogin;
    private CardView cvLogin;
    private ProgressBar progressBar;
    private SweetAlertDialog sweetLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mService = Common.getAPI();

        sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...");
        sweetLoading.setCancelable(false);

        initViews();
    }

    private void initViews() {
        etNIP = findViewById(R.id.etNIP);
        etPassword = findViewById(R.id.etPassword);
        tilPassword = findViewById(R.id.tilPassword);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        cvLogin = findViewById(R.id.cvLogin);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

    private void login() {
        String nip, password;
        nip = etNIP.getText().toString();
        password = etPassword.getText().toString();

        if (checkFields(true, nip, password)) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
            cvLogin.setCardBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            mService.checkLogin(nip, password)
                    .enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful()){
                                if (response.body().isExists()) {
                                    checkStatusLogin(nip);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    btnLogin.setEnabled(true);
                                    cvLogin.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Tidak Valid!")
                                            .setContentText(response.body().getMessage())
                                            .setConfirmText("Close")
                                            .setConfirmClickListener(sweetAlertDialog -> {
                                                sweetAlertDialog.dismissWithAnimation();
                                            })
                                            .show();

                                    /** INSERT LOG USER - Failed LOGIN */
                                    String aksi = "Login - gagal";
                                    logLogin(nip, aksi);
                                }
                            }
                            else {
                                Log.d("check", response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setEnabled(true);
                            cvLogin.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            Log.d("checkLogin", t.getMessage());
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops!")
                                    .setContentText(t.getLocalizedMessage())
                                    .setConfirmText("Close")
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .show();
                        }
                    });
        }
    }

    /**
     * Check Status User (On/Off)
     */
    private void checkStatusLogin(String nip) {
        sweetLoading.show();
        mService.getUserInformation(nip)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            btnLogin.setEnabled(true);
                            cvLogin.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            int idUser = response.body().getId_user();
                            String status = response.body().getStatus();
                            if (status.equals("off"))
                                updateStatusUser(idUser, nip);
                            else {
                                sweetLoading.setTitleText("Oops!")
                                        .setContentText("User Telah Login.")
                                        .setConfirmText("Close")
                                        .setConfirmClickListener(sweetAlertDialog -> {
                                            sweetAlertDialog.dismissWithAnimation();
                                        })
                                        .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        sweetLoading.setTitleText("Error - Check Status Login")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
    }


    /**
     * Update Status User Login
     */
    private void updateStatusUser(int idUser, String nip) {
        String lastLogin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Preferences.setLastLogin(getBaseContext(), lastLogin);

        mService.updateStatusUser("on", lastLogin, idUser)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            getUserInformation(nip, lastLogin);
                        } else {
                            sweetLoading.setTitleText("Warning - Update Status User")
                                    .setContentText(response.body().getMessage())
                                    .setConfirmText("Close")
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        sweetLoading.setTitleText("Error - Update Status User")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
    }

    /**
     * Get All Information of User by Username/nip from SharedPreferences
     */
    private void getUserInformation(String nip, String lastLogin) {
        mService.getUserInformation(nip)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            UserModel userModel = response.body();

                            /** Set Data on Preferences */
                            Preferences.setIDUser(getBaseContext(), userModel.getId_user());
                            Preferences.setUsername(getBaseContext(), userModel.getUsername());
                            Preferences.setNama(getBaseContext(), userModel.getNama());
                            Preferences.setEmail(getBaseContext(), userModel.getEmail());
                            Preferences.setLevel(getBaseContext(), Common.formatLevel(userModel.getLevel()));
                            Preferences.setLoggedInStatus(getBaseContext(), true);

                            /** Log Login - Berhasil */
                            String aksi = "Login";
                            logLogin(nip, aksi);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        sweetLoading.setTitleText("Error - Get User Information")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    }
                });
    }

    /**
     * LOG User (Login / Login - Gagal)
     */
    private void logLogin(String nip, String aksi) {
        mService.checkUsername(nip).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body().isExists()) {

                    /** Insert Log User if Username Already Exist */
                    Common.insertLogAction(LoginActivity.this, aksi, nip);
                    sweetLoading.dismiss();

                } else
                    Log.d("N", "Nothing Happen!");
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("Failure", t.getMessage());
            }
        });
    }

    /**
     * Check Fields
     */
    private boolean checkFields(boolean checked, String nip, String password) {
        if (TextUtils.isEmpty(nip)) {
            checked = false;
            etNIP.setError("Please fill out this field.");
            etNIP.requestFocus();
        }
        if (TextUtils.isEmpty(password)) {
            checked = false;
            tilPassword.setPasswordVisibilityToggleEnabled(false);
            etPassword.setError("Please fill out this field.");
            etNIP.requestFocus();
        }
        if (!TextUtils.isEmpty(password)) {
            tilPassword.setPasswordVisibilityToggleEnabled(true);
        }
        return checked;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}