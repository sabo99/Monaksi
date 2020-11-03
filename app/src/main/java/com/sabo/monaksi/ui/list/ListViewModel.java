package com.sabo.monaksi.ui.list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Model.MonitoringModel;

import java.util.List;

public class ListViewModel extends ViewModel {

    private MutableLiveData<List<MonitoringModel>> mutableLiveData;

    public ListViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MonitoringModel>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void setMutableLiveData(List<MonitoringModel> monitoringModelList) {
        mutableLiveData.setValue(monitoringModelList);
    }
}



