package com.sabo.monaksi.ui.approval;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.Model.MonitoringModel;

import java.util.List;

public class ApprovalViewModel extends ViewModel {
    private MutableLiveData<List<MonitoringModel>> mutableLiveData;

    public ApprovalViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MonitoringModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<MonitoringModel> monitoringModelList) {
        mutableLiveData.setValue(monitoringModelList);
    }
}