package com.sabo.monaksi.ui.task;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.Model.MonitoringModel;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private MutableLiveData<List<MonitoringModel>> mutableLiveData;

    public TaskViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MonitoringModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<MonitoringModel> monitoringModelList) {
        mutableLiveData.setValue(monitoringModelList);
    }
}