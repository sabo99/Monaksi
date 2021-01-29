package com.sabo.monaksi.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.EventBus.HideAllContentHeader;
import com.sabo.monaksi.EventBus.HideFab;
import com.sabo.monaksi.EventBus.HideSearchMenu;
import com.sabo.monaksi.EventBus.RefreshUpdated;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.UserModel;
import com.sabo.monaksi.R;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;
    private APIRequestData mService;
    private TextView tvNama, tvUsername, tvEmail, tvLastLogin;
    private Button btnGantiPassword, btnEditProfile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        mService = Common.getAPI();
        initViews(root);

        profileViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), userModel -> {
            if (userModel != null){
                tvNama.setText(userModel.getNama());
                tvUsername.setText(userModel.getUsername());
                tvEmail.setText(userModel.getEmail());
                tvLastLogin.setText(userModel.getLastLogin());
            }
        });

        return root;
    }

    private void loadData() {
        UserModel userModel = new UserModel();
        userModel.setNama(Preferences.getNama(getContext()));
        userModel.setUsername(Preferences.getUsername(getContext()));
        userModel.setEmail(Preferences.getEmail(getContext()));
        userModel.setLastLogin(Preferences.getLastLogin(getContext()));

        profileViewModel.setMutableLiveData(userModel);
    }

    private void initViews(View root) {
        tvNama = root.findViewById(R.id.tvNama);
        tvUsername = root.findViewById(R.id.tvUsername);
        tvEmail = root.findViewById(R.id.tvEmail);
        tvLastLogin = root.findViewById(R.id.tvLastLogin);
        btnGantiPassword = root.findViewById(R.id.btnGantiPassword);
        btnEditProfile = root.findViewById(R.id.btnEditProfile);

        btnGantiPassword.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().postSticky(new HideAllContentHeader(true));
        EventBus.getDefault().postSticky(new HideSearchMenu(true));
        EventBus.getDefault().postSticky(new HideFab(true));

        loadData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGantiPassword :
                gantiPassword();
                break;
            case R.id.btnEditProfile :
                editProfile();
                break;
        }
    }

    /** Sweet Dialog Edit Profile */
    private void editProfile() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_profile, null);
        EditText etNamaUser, etUsername, etEmail;
        etNamaUser = view.findViewById(R.id.etNamaUser);
        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        SweetAlertDialog sweetEditProfile = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.ic_edit)
                .setTitleText("Edit Profile")
                .showCancelButton(false)
                .setCancelText("Batal")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .setConfirmText("Simpan")
                .setConfirmClickListener(sweetAlertDialogMain -> {
                    String nama, username, email, message = "Tidak boleh kosong!";
                    nama = etNamaUser.getText().toString();
                    username = etUsername.getText().toString();
                    email = etEmail.getText().toString();
                    if (checkFields(true, etNamaUser, etUsername, etEmail, message)){
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Edit Profile!")
                                .setContentText("Apakah anda yakin ingin mengubah profile.")
                                .showCancelButton(true)
                                .setCancelText("Cancel")
                                .setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    updateProfile(nama, username, email, sweetAlertDialogMain, sweetAlertDialog);
                                })
                                .show();
                    }
                });
        sweetEditProfile.setOnShowListener(dialog -> {
            etNamaUser.requestFocus();
            etNamaUser.setText(Preferences.getNama(getContext()));
            etUsername.setText(Preferences.getUsername(getContext()));
            etEmail.setText(Preferences.getEmail(getContext()));
        });
        sweetEditProfile.show();
        LinearLayout linearLayout = sweetEditProfile.findViewById(R.id.loading);
        linearLayout.setPadding(0, 30 , 0, 50);
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index+1);
    }

    /** Update Profile */
    private void updateProfile(String nama, String username, String email, SweetAlertDialog sweetAlertDialogMain, SweetAlertDialog sweetAlertDialog) {
        mService.updateProfileUser(nama, username, email, Preferences.getIDUser(getContext()))
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()){
                            Common.insertLogAction(getContext(), "Update Profile", "");
                            updatePreferences(nama, username,  email);
                            sweetAlertDialog.setTitleText("Success!")
                                    .setContentText(response.body().getMessage())
                                    .showCancelButton(false)
                                    .setConfirmText("Close")
                                    .setConfirmClickListener(sweetAlertDialog1 -> {
                                        sweetAlertDialog1.dismissWithAnimation();
                                        sweetAlertDialogMain.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        sweetAlertDialog.setTitleText("Error!")
                                .setContentText(t.getMessage())
                                .showCancelButton(false)
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog1 -> {
                                    sweetAlertDialog1.dismissWithAnimation();
                                })
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
    }

    /** Update Preferences */
    private void updatePreferences(String nama, String username, String email) {
        tvNama.setText(nama);
        tvUsername.setText(username);
        tvEmail.setText(email);
        Preferences.setNama(getContext(), nama);
        Preferences.setUsername(getContext(), username);
        Preferences.setEmail(getContext(), email);

        EventBus.getDefault().postSticky(new RefreshUpdated(true, false));
    }

    /** Check Fields Edit Profile */
    private boolean checkFields(boolean checked, EditText etNamaUser, EditText etUsername, EditText etEmail, String message) {
        if (TextUtils.isEmpty(etNamaUser.getText().toString())){
            checked = false;
            etNamaUser.setError(message);
        }
        if (TextUtils.isEmpty(etUsername.getText().toString())){
            checked = false;
            etUsername.setError(message);
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())){
            checked = false;
            etEmail.setError(message);
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            checked = false;
            etEmail.setError("Invalid Email Address!");
        }

        return checked;
    }

    /** Sweet Dialog Ganti Password */
    private void gantiPassword() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ganti_password, null);
        EditText etNamaUser, etPassword;
        TextInputLayout tilPassword;
        etNamaUser = view.findViewById(R.id.etNamaUser);
        etPassword = view.findViewById(R.id.etPassword);
        tilPassword = view.findViewById(R.id.tilPassword);
        SweetAlertDialog sweetGantiPassword = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setCustomImage(R.drawable.ic_edit)
                .setTitleText("Ganti Password")
                .showCancelButton(false)
                .setCancelText("Batal")
                .setCancelClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                })
                .setConfirmText("Simpan")
                .setConfirmClickListener(sweetAlertDialogMain -> {
                    String password = etPassword.getText().toString();
                    if (password.length() < 6){
                        tilPassword.setHelperTextEnabled(true);
                        tilPassword.setHelperText("Minimal 6 digit!");
                        tilPassword.setHelperTextColor(ColorStateList.valueOf(getResources().getColor(R.color.red_btn_bg_color)));
                    }
                    else {
                        tilPassword.setHelperTextEnabled(false);
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Ganti Password!")
                                .setContentText("Apakah anda yakin ingin mengganti password.")
                                .showCancelButton(true)
                                .setCancelText("Cancel")
                                .setCancelClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismissWithAnimation();
                                })
                                .setConfirmClickListener(sweetAlertDialog-> {
                                    updatePassword(password, sweetAlertDialogMain, sweetAlertDialog);
                                })
                                .show();

                    }

                });
        sweetGantiPassword.setOnShowListener(dialog -> {
            etNamaUser.setText(Preferences.getNama(getContext()));
            etPassword.requestFocus();
        });
        sweetGantiPassword.show();
        LinearLayout linearLayout = sweetGantiPassword.findViewById(R.id.loading);
        linearLayout.setPadding(0, 30 , 0, 50);
        int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
        linearLayout.addView(view, index+1);
    }

    /** Update Password */
    private void updatePassword(String password, SweetAlertDialog sweetAlertDialogMain, SweetAlertDialog sweetAlertDialog) {
        mService.updatePasswordUser(password, Preferences.getIDUser(getContext()))
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()){
                            Common.insertLogAction(getContext(), "Update Password", "");
                            sweetAlertDialog.setTitleText("Success!")
                                    .setContentText(response.body().getMessage())
                                    .showCancelButton(false)
                                    .setConfirmText("Close")
                                    .setConfirmClickListener(sweetAlertDialog1 -> {
                                        sweetAlertDialog1.dismissWithAnimation();
                                        sweetAlertDialogMain.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        sweetAlertDialog.setTitleText("Error - Update Password")
                                .setContentText(t.getMessage())
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                });
    }
}