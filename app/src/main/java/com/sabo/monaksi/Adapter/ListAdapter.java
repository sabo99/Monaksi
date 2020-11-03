package com.sabo.monaksi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.ActionKeputusan.UpdateKeputusanActivity;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.Common.Preferences;
import com.sabo.monaksi.EventBus.RefreshLoadData;
import com.sabo.monaksi.EventBus.RefreshUpdated;
import com.sabo.monaksi.Model.AgendaModel;
import com.sabo.monaksi.Model.MonitoringModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.R;
import com.sabo.monaksi.ui.list.DetailListActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private List<MonitoringModel> monitoringModelList;
    private APIRequestData mService;

    public ListAdapter(Context context, List<MonitoringModel> monitoringModelList) {
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
            context.startActivity(new Intent(context, DetailListActivity.class));

            /** Insert Log */
            mService.getNamaAgenda(list.getID_AGENDA()).enqueue(new Callback<AgendaModel>() {
                @Override
                public void onResponse(Call<AgendaModel> call, Response<AgendaModel> response) {
                    if (response.isSuccessful()){
                        String namaAgenda = response.body().getNama_agenda();
                        String aksi = "Lihat Detail Rapat " + namaAgenda + " - " + list.getKEPUTUSAN();
                        Common.insertLogAction(context, aksi, "");
                    }
                }

                @Override
                public void onFailure(Call<AgendaModel> call, Throwable t) {
                    Log.d("LOG - List Detail", t.getMessage());
                }
            });

        });

        /** Event Menu Action Keputusan */
        holder.itemView.setOnLongClickListener(v -> {
            String level = Preferences.getLevel(context);
            if (level.equals("Admin") || level.equals("Operator")){
                SweetAlertDialog sweetActionKeputusan = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Action")
                        .setContentText(list.getPROG_ID())
                        .showCancelButton(true)
                        .setCancelText("Delete")
                        .setCancelClickListener(sweetAction -> {
                            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Delete Keputusan!")
                                    .setContentText("Apakah anda yakin ingin menghapus keputusan ini?")
                                    .showCancelButton(true)
                                    .setCancelText("Cancel")
                                    .setCancelClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .setConfirmText("Delete")
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        deleteKeputusan(list, sweetAction, sweetAlertDialog);
                                    })
                                    .show();
                        })
                        .setConfirmText("Edit")
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismissWithAnimation();
                            Common.selectedMonitoring = list;
                            context.startActivity(new Intent(context, UpdateKeputusanActivity.class));
                        });
                sweetActionKeputusan.setCanceledOnTouchOutside(true);
                sweetActionKeputusan.show();
                LinearLayout linearLayout = sweetActionKeputusan.findViewById(R.id.loading);
                TextView title = sweetActionKeputusan.findViewById(R.id.title_text);
                Button confirm = sweetActionKeputusan.findViewById(R.id.confirm_button)
                        , cancel = sweetActionKeputusan.findViewById(R.id.cancel_button);
                linearLayout.setPadding(0, 20, 0, 30);
                title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                confirm.setBackgroundResource(R.drawable.blue_button_background);
                cancel.setBackgroundResource(R.drawable.red_button_background);
            }
            return true;
        });
    }

    /** Delete Keputusan */
    private void deleteKeputusan(MonitoringModel list, SweetAlertDialog sweetAction, SweetAlertDialog sweetAlertDialog) {
        sweetAlertDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.colorPrimary));
        sweetAlertDialog.showContentText(false).showCancelButton(false);
        sweetAlertDialog.setTitleText("Deleting...").changeAlertType(SweetAlertDialog.PROGRESS_TYPE);

        int ID_MON = Integer.parseInt(list.getID_MON());
        mService.deleteKeputusan(ID_MON).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()){
                    sweetAction.dismissWithAnimation();
                    sweetAlertDialog.setTitleText("Success!")
                            .setContentText(response.body().getMessage())
                            .setConfirmText("Close")
                            .setConfirmClickListener(sweetClose -> {
                                sweetClose.dismissWithAnimation();
                            })
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                    /** Refresh Load Data*/
                    EventBus.getDefault().postSticky(new RefreshUpdated(false, true));

                    /** Insert Log */
                    String aksi = "Hapus Keputusan " + list.getKEPUTUSAN();
                    Common.insertLogAction(context, aksi, "");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                sweetAction.dismissWithAnimation();
                sweetAlertDialog.setTitleText("Oops!")
                        .setContentText(t.getMessage())
                        .changeAlertType(SweetAlertDialog.WARNING_TYPE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monitoringModelList.size();
    }

}
