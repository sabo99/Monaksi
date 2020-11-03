package com.sabo.monaksi.ui.dashboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.Common.Common;
import com.sabo.monaksi.EventBus.HideAllContentHeader;
import com.sabo.monaksi.EventBus.HideFab;
import com.sabo.monaksi.EventBus.HideSearchMenu;
import com.sabo.monaksi.EventBus.HideStatusContentHeader;
import com.sabo.monaksi.EventBus.RefreshLoadData;
import com.sabo.monaksi.Model.StatusModel;
import com.sabo.monaksi.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment implements OnChartValueSelectedListener {

    private DashboardViewModel dashboardViewModel;

    private APIRequestData mService;
    private PieChart pieChart;
    private TextView tvDashboard;
    private Typeface tfRegular, tfLight;

    private String sessionSelectedRapat = "Silahkan Pilih Rapat.";
    private int sessionSubrapat = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        mService = Common.getAPI();
        initViews(root);
        getEntriesChart();
        return root;
    }

    private void initViews(View root) {

        tfRegular = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Light.ttf");

        pieChart = root.findViewById(R.id.pieCart);
        tvDashboard = root.findViewById(R.id.tvDashboard);

        tvDashboard.setTypeface(tfLight);
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        //pieCart.setHighlightPerTapEnabled(true);

        pieChart.setOnChartValueSelectedListener(this);

        /** Legend */
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
        legend.setTypeface(tfRegular);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(10f);
        pieChart.setEntryLabelTypeface(tfRegular);
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MONAKSI \nMonitoring Action Plan");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 7, 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 22, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), s.length() - 22, s.length(), 0);
        return s;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chart, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionToggleHole: {
                pieChart.highlightValue(null);
                if (pieChart.isDrawHoleEnabled())
                    pieChart.setDrawHoleEnabled(false);
                else
                    pieChart.setDrawHoleEnabled(true);
                pieChart.invalidate();
            }
            break;

            case R.id.actionToggleCurvedSlices: {
                pieChart.highlightValue(null);
                boolean toSet = !pieChart.isDrawRoundedSlicesEnabled() || !pieChart.isDrawHoleEnabled();
                pieChart.setDrawRoundedSlices(toSet);
                if (toSet && !pieChart.isDrawHoleEnabled()) {
                    pieChart.setDrawHoleEnabled(true);
                }
                if (toSet && pieChart.isDrawSlicesUnderHoleEnabled()) {
                    pieChart.setDrawSlicesUnderHole(false);
                }
                pieChart.invalidate();
            }
            break;

            case R.id.actionToggleSpin: {
                pieChart.highlightValue(null);
                pieChart.spin(1000, pieChart.getRotationAngle(),
                        pieChart.getRotationAngle() + 360, Easing.EaseInOutCubic);
            }
            break;

            case R.id.actionSave: {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            Common.REQUEST_PERMISSION_DOWNLOAD);
                else
                    saveToGallery();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Common.REQUEST_PERMISSION_DOWNLOAD && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            saveToGallery();
        } else
            Toast.makeText(getContext(), "Saving Failed!", Toast.LENGTH_SHORT).show();

    }

    private void saveToGallery() {
        String fileName = sessionSelectedRapat + "_" + System.currentTimeMillis();
        if (pieChart.saveToGallery(fileName, 90)) {
            /** INSERT LOG User */
            String aksi = "Download Chart - " + fileName;
            Common.insertLogAction(getContext(), aksi, "");
        } else
            Toast.makeText(getContext(), "Saving Failed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();

        EventBus.getDefault().postSticky(new HideAllContentHeader(false));
        EventBus.getDefault().postSticky(new HideStatusContentHeader(true));
        EventBus.getDefault().postSticky(new HideSearchMenu(true));
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
        super.onStop();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());

        pieChart.setUsePercentValues(!pieChart.isUsePercentValuesEnabled());
        pieChart.invalidate();
    }

    @Override
    public void onNothingSelected() {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRefreshLoadData(RefreshLoadData event) {
        if (event.isChart() && !event.getSelectedRapat().equals("")) {
            setHasOptionsMenu(true);
            sessionSelectedRapat = event.getSelectedRapat();
            sessionSubrapat = event.getIdSubrapat();
            loadData();
        } else {
            sessionSelectedRapat = event.getSelectedRapat();
            sessionSubrapat = event.getIdSubrapat();
        }
    }

    private void loadData() {
        mService.getCount(sessionSubrapat).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                if (response.isSuccessful() && response.body() != null)
                    dashboardViewModel.setMutableLiveData(response.body());
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Log.d("chart", t.getMessage());
            }
        });
    }

    private void getEntriesChart() {
        dashboardViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), statusModel -> {
            if (statusModel != null) {
                pieChart.clearAnimation();
                pieChart.animateY(1400, Easing.EaseInOutQuad);

                /** Description */
                Description description = pieChart.getDescription();
                description.setTextSize(11f);
                description.setTypeface(tfLight);

                String desc;
                int s0, s1, s2, s3, s4, s5, count;
                s0 = statusModel.getRevisi();
                s1 = statusModel.getBelumdikerjakan();
                s2 = statusModel.getOnprogress();
                s3 = statusModel.getSelesai();
                s4 = statusModel.getApproved();
                s5 = statusModel.getVerified();

                count = s0 + s1 + s2 + s3 + s4 + s5;
                if (count != 0) {
                    desc = new StringBuilder().append(sessionSelectedRapat).append(" : Total - ").append(count).toString();
                    description.setText(desc);
                } else
                    description.setText(sessionSelectedRapat);

                if (!sessionSelectedRapat.equals("Silahkan Pilih Rapat."))
                    tvDashboard.setText(new StringBuilder("Dashboard - ").append(sessionSelectedRapat).toString());

                pieChart.setDescription(description);

                ArrayList pieEntries = new ArrayList();
                pieEntries.add(new PieEntry(Float.valueOf(s0), "Revisi", "Revisi"));
                pieEntries.add(new PieEntry(Float.valueOf(s1), "Belum Dikerjakan", "Belum dikerjakan"));
                pieEntries.add(new PieEntry(Float.valueOf(s2), "On Progress", "On Progress"));
                pieEntries.add(new PieEntry(Float.valueOf(s3), "Selesai", "Selesai"));
                pieEntries.add(new PieEntry(Float.valueOf(s4), "Approved", "Approved"));
                pieEntries.add(new PieEntry(Float.valueOf(s5), "Verified", "Verified"));

                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                pieDataSet.setSliceSpace(5f);
                pieDataSet.setSelectionShift(5f);

                ArrayList<Integer> colors = new ArrayList<>();
                for (int c : Common.COLOR_STATUS_MON)
                    colors.add(c);

                colors.add(ColorTemplate.getHoloBlue());
                pieDataSet.setColors(colors);

                PieData pieData = new PieData(pieDataSet);
                pieData.setValueFormatter(new PercentFormatter(pieChart));
                pieData.setValueTextSize(11f);
                pieData.setValueTextColor(Color.WHITE);
                pieData.setValueTypeface(tfLight);
                pieChart.setData(pieData);

                pieChart.highlightValue(null);
                pieChart.invalidate();
            }
        });
    }

}