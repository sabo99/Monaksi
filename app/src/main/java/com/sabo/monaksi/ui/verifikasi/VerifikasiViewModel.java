package com.sabo.monaksi.ui.verifikasi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.Model.MonitoringModel;

import java.util.List;

public class VerifikasiViewModel extends ViewModel {
    private MutableLiveData<List<MonitoringModel>> mutableLiveData;

    public VerifikasiViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MonitoringModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<MonitoringModel> monitoringModelList) {
        mutableLiveData.setValue(monitoringModelList);
    }
}