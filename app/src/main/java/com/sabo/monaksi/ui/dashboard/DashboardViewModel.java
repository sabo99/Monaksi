package com.sabo.monaksi.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.Model.StatusModel;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<StatusModel> mutableLiveData;

    public DashboardViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<StatusModel> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(StatusModel statusModel) {
        mutableLiveData.setValue(statusModel);
    }
}