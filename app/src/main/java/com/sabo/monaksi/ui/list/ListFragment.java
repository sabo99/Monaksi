package com.sabo.monaksi.ui.list;

import android.os.Bundle;
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
import com.sabo.monaksi.Adapter.ListAdapter;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.EventBus.HideAllContentHeader;
import com.sabo.monaksi.EventBus.HideFab;
import com.sabo.monaksi.EventBus.HideSearchMenu;
import com.sabo.monaksi.EventBus.HideStatusContentHeader;
import com.sabo.monaksi.EventBus.RefreshLoadData;
import com.sabo.monaksi.EventBus.RefreshUpdated;
import com.sabo.monaksi.EventBus.SearchQuery;
import com.sabo.monaksi.EventBus.SpinnerStatusMonitoring;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;

    private APIRequestData mService;
    private RecyclerView rvList;
    private ListAdapter adapter, searchAdapter;
    private List<MonitoringModel> tempList = new ArrayList<>();
    private List<MonitoringModel> searchList = new ArrayList<>();
    private List<MonitoringModel> searchResult;
    private TextView tvStatusMonitoring, tvEmptySearch, tvEmptyList, tvAction;
    private ProgressBar progressBar;

    private String querySearch = "", queryStatusSelected = "- None -";
    private int sessionSubrapat = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                ViewModelProviders.of(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);

        mService = Common.getAPI();
        initViews(root);


        listViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), monitoringModels -> {
            tempList.clear();
            rvList.setVisibility(View.VISIBLE);
            tempList = monitoringModels;
            searchList = tempList;
            adapter = new ListAdapter(getContext(), tempList);
            rvList.setAdapter(adapter);

            progressBar.setVisibility(View.GONE);
            tvEmptyList.setText("");
        });

        return root;
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        mService.getList(sessionSubrapat)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null)
                                listViewModel.setMutableLiveData(response.body().getData());
                            else {
                                rvList.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                tvEmptyList.setText("");
                                tvEmptyList.setText("Daftar List tidak ditemukan \nSilahkan Pilih Rapat.");
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void loadDataBackLog() {
        progressBar.setVisibility(View.VISIBLE);
        String now = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mService.getListByDate(sessionSubrapat, now)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null)
                                listViewModel.setMutableLiveData(response.body().getData());
                            else {
                                rvList.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                tvEmptyList.setText("Daftar List - Backlog tidak ditemukan.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void loadDataByStatus(String queryStatusSelected) {
        progressBar.setVisibility(View.VISIBLE);
        mService.getListByStatus(sessionSubrapat, Common.formatStatusMonitoringToInt(queryStatusSelected))
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getData() != null)
                                listViewModel.setMutableLiveData(response.body().getData());
                            else {
                                rvList.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                tvEmptyList.setText("Daftar List - "+queryStatusSelected+" tidak ditemukan.");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void initViews(View root) {
        tvAction = root.findViewById(R.id.tvAction);
        tvEmptyList = root.findViewById(R.id.tvEmptyList);
        tvEmptySearch = root.findViewById(R.id.tvEmptySearch);
        progressBar = root.findViewById(R.id.progressBar);
        tvStatusMonitoring = root.findViewById(R.id.tvStatusMonitoring);
        rvList = root.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onResume() {
        super.onResume();

        loadDataByQueryStatus();
        checkLevel();

        EventBus.getDefault().postSticky(new HideAllContentHeader(false));
        EventBus.getDefault().postSticky(new HideStatusContentHeader(false));
        EventBus.getDefault().postSticky(new HideSearchMenu(false));
        //EventBus.getDefault().postSticky(new HideFab(false));
    }

    private void checkLevel() {
        String level = Preferences.getLevel(getContext());
        if (level.equals("Admin") || level.equals("Operator")){
            tvAction.setVisibility(View.VISIBLE);
            EventBus.getDefault().postSticky(new HideFab(false));
        } else {
            tvAction.setVisibility(View.GONE);
            EventBus.getDefault().postSticky(new HideFab(true));
        }
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
            tvEmptyList.setVisibility(View.GONE);
            search(querySearch);
        } else {
            tvEmptySearch.setText("");
            tvEmptyList.setVisibility(View.VISIBLE);
            loadDataByQueryStatus();
        }
    }

    /**
     * Search By Query Session
     */
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

        searchAdapter = new ListAdapter(getContext(), searchResult);
        rvList.setAdapter(searchAdapter);
    }

    /**
     * Spinner Pilih Status
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onSelectedStatusMonitoring(SpinnerStatusMonitoring event) {
        if (event.isSelected() && !event.getResultSelected().equals("")) {
            queryStatusSelected = event.getResultSelected();

            //Toast.makeText(getContext(), "" + queryStatusSelected, Toast.LENGTH_SHORT).show();
            if (queryStatusSelected.equals("- None -"))
                tvStatusMonitoring.setText("");
            else
                tvStatusMonitoring.setText(queryStatusSelected + " - ");

            loadDataByQueryStatus();

            Common.colorTextStatus(getContext(), queryStatusSelected, tvStatusMonitoring);
        }
    }

    private void loadDataByQueryStatus() {
        if (queryStatusSelected.equals("- None -"))
            loadData();
        else if (queryStatusSelected.equals("Backlog"))
            loadDataBackLog();
        else
            loadDataByStatus(queryStatusSelected);
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
            queryStatusSelected = "- None -";
            tvStatusMonitoring.setText("");
            EventBus.getDefault().postSticky(new SpinnerStatusMonitoring(false, ""));
        } else
            sessionSubrapat = event.getIdSubrapat();

    }

    /** Refresh After Updated */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRefreshLoadData(RefreshUpdated event){
        if (event.isListUpdated()){
            onResume();
            event.setListUpdated(false);
        }

    }
}