package com.sabo.monaksi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.R;
import com.sabo.monaksi.ui.list.DetailListActivity;
import com.sabo.monaksi.ui.task.DetailTaskActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<MonitoringModel> monitoringModelList;
    private APIRequestData mService;

    public TaskAdapter(Context context, List<MonitoringModel> monitoringModelList) {
        this.context = context;
        this.monitoringModelList = monitoringModelList;
        mService = Common.getAPI();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MonitoringModel list = monitoringModelList.get(position);

        holder.tvProgID.setText(list.getPROG_ID());
        holder.tvKeputusan.setText(list.getKEPUTUSAN());
        holder.tvTglTarget.setText(new StringBuilder("Target: \n")
                .append(Common.formatTgl(list.getTGL_TARGET())).toString());

        String status = Common.formatStatusMonitoringToString(list.getLAST_STATUS());
        holder.tvStatus.setText(status);

        Common.checkColorStatus(context, status, holder.cvColorStatus, holder.vColorStatus, holder.tvStatus);

        /** Event Detail */
        holder.itemView.setOnClickListener(v -> {
            Common.selectedMonitoring = list;
            context.startActivity(new Intent(context, DetailTaskActivity.class));

            /** Insert Log */
            mService.getNamaAgenda(list.getID_AGENDA()).enqueue(new Callback<AgendaModel>() {
                @Override
                public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                    if (response.isSuccessful()){
                        String namaAgenda = response.body().getNama_agenda();
                        String aksi = "Lihat Detail Task Rapat " + namaAgenda + " - " + list.getKEPUTUSAN();
                        Common.insertLogAction(context, aksi, "");
                    }
                }

                @Override
                public void onFailure(Call<AgendaModel> call, Throwable t) {
                    Log.d("LOG - Task Detail", t.getMessage());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return monitoringModelList.size();
    }
}
