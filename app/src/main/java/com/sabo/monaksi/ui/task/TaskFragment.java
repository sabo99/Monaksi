package com.sabo.monaksi.ui.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Adapter.TaskAdapter;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.EventBus.HideAllContentHeader;
import com.sabo.monaksi.EventBus.HideFab;
import com.sabo.monaksi.EventBus.HideSearchMenu;
import com.sabo.monaksi.EventBus.HideStatusContentHeader;
import com.sabo.monaksi.EventBus.RefreshLoadData;
import com.sabo.monaksi.EventBus.SearchQuery;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskFragment extends Fragment {

    private TaskViewModel taskViewModel;

    private APIRequestData mService;
    private RecyclerView rvTask;
    private TaskAdapter adapter, searchAdapter;
    private List<MonitoringModel> tempList = new ArrayList<>();
    private List<MonitoringModel> searchList = new ArrayList<>();
    private List<MonitoringModel> searchResult;
    private TextView tvEmptySearch, tvEmptyTask;
    private ProgressBar progressBar;

    private String querySearch = "";
    private int sessionSubrapat = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        taskViewModel =
                ViewModelProviders.of(this).get(TaskViewModel.class);
        View root = inflater.inflate(R.layout.fragment_task, container, false);

        mService = Common.getAPI();
        initViews(root);

        taskViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), monitoringModels -> {
            tempList.clear();

            rvTask.setVisibility(View.VISIBLE);
            tempList = monitoringModels;
            searchList = tempList;
            adapter = new TaskAdapter(getContext(), tempList);
            rvTask.setAdapter(adapter);

            progressBar.setVisibility(View.GONE);
            tvEmptyTask.setText("");
        });

        return root;
    }

    private void initViews(View root) {
        tvEmptyTask = root.findViewById(R.id.tvEmptyTask);
        tvEmptySearch = root.findViewById(R.id.tvEmptySearch);
        progressBar = root.findViewById(R.id.progressBar);
        rvTask = root.findViewById(R.id.rvTask);
        rvTask.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        mService.getTask(sessionSubrapat, Preferences.getUsername(getContext()))
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null)
                                taskViewModel.setMutableLiveData(response.body().getData());
                            else {
                                progressBar.setVisibility(View.GONE);
                                rvTask.setVisibility(View.GONE);
                                tvEmptyTask.setText("Daftar Task tidak ditemukan \nSilahkan Pilih Rapat.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("task", t.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

        EventBus.getDefault().postSticky(new HideAllContentHeader(false));
        EventBus.getDefault().postSticky(new HideStatusContentHeader(true));
        EventBus.getDefault().postSticky(new HideSearchMenu(false));
        EventBus.getDefault().postSticky(new HideFab(true));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        querySearch = "";
        super.onStop();
    }

    /**
     * Search
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSearchQuery(SearchQuery event) {
        if (event.isSearch() && !event.getQuery().equals("")) {
            querySearch = event.getQuery();
            tvEmptyTask.setVisibility(View.GONE);
            search(querySearch);
        } else {
            tvEmptySearch.setText("");
            tvEmptyTask.setVisibility(View.VISIBLE);
            loadData();
        }
    }

    private void search(String querySearch) {
        searchResult = new ArrayList<>();
        for (MonitoringModel item : searchList) {
            if (item.getPROG_ID().toLowerCase().contains(querySearch) || item.getPROG_ID().contains(querySearch) ||
                    item.getKEPUTUSAN().toLowerCase().contains(querySearch) || item.getKEPUTUSAN().contains(querySearch) ||
                    Common.formatTgl(item.getTGL_TARGET()).toLowerCase().contains(querySearch) || Common.formatTgl(item.getTGL_TARGET()).contains(querySearch) ||
                    Common.formatStatusMonitoringToString(item.getLAST_STATUS()).toLowerCase().contains(querySearch) || Common.formatStatusMonitoringToString(item.getLAST_STATUS()).contains(querySearch)) {
                searchResult.add(item);
            }
        }
        if (searchResult.isEmpty())
            tvEmptySearch.setText("No results found for '" + querySearch + "'");
        else
            tvEmptySearch.setText("");

        searchAdapter = new TaskAdapter(getContext(), searchResult);
        rvTask.setAdapter(searchAdapter);
    }

    /**
     * Spinner Rapat Aktif
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSelectedRapatAktif(RefreshLoadData event) {
        if (event.isMonitoring()) {
            sessionSubrapat = event.getIdSubrapat();
            loadData();

            progressBar.setVisibility(View.VISIBLE);
        } else
            sessionSubrapat = event.getIdSubrapat();
    }
}