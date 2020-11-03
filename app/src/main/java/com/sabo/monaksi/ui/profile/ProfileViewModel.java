package com.sabo.monaksi.ui.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.Model.UserModel;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<UserModel> mutableLiveData;

    public ProfileViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<UserModel> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(UserModel userModel) {
        mutableLiveData.setValue(userModel);
    }
}