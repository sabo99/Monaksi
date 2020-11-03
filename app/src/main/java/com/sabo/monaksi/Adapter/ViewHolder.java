package com.sabo.monaksi.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabo.monaksi.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    View vColorStatus;
    CardView cvColorStatus;
    TextView tvProgID, tvKeputusan, tvTglTarget, tvStatus;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        tvProgID = itemView.findViewById(R.id.tvProgID);
        tvKeputusan = itemView.findViewById(R.id.tvKeputusan);
        tvStatus = itemView.findViewById(R.id.tvStatus);
        tvTglTarget = itemView.findViewById(R.id.tvTglTarget);
        vColorStatus = itemView.findViewById(R.id.vColorStatus);
        cvColorStatus = itemView.findViewById(R.id.cvColorStatus);
    }
}
