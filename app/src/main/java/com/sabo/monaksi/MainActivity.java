package com.sabo.monaksi;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.ferfalk.simplesearchview.SimpleSearchViewListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.sabo.monaksi.API.APIRequestData;
import com.sabo.monaksi.ActionKeputusan.TambahKeputusanActivity;
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
import com.sabo.monaksi.Model.RapatModel;
import com.sabo.monaksi.Model.ResponseModel;
import com.sabo.monaksi.Model.SubrapatModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private NavController navController;
    private APIRequestData mService;

    /**
     * Profile Header
     */
    private TextView tvNama, tvLevel;

    /**
     * Content Header
     */
    private LinearLayout contentHeader, contentMain;
    private CardView contentHeaderStatus;

    /**
     * Search Menu
     */
    private SimpleSearchView simpleSearchView;
    private boolean isHideMenu;

    /**
     * Spinner Rapat & Subrapat Aktif
     */
    private TextView tvSelectRapat, tvRapatAktif;
    private Spinner spSubrapat;
    private int idSubrapat = 0;
    private String resultNamaRapat, resultNamaSubrapat;

    /**
     * Spinner Status Monitoring
     */
    private Spinner spStatusMonitoring;

    /**
     * TypeFace font-family
     */
    private Typeface tfRegular, tfLight;
    private MenuItem menuSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getAPI();

        initViews();
        initViewsUser(navigationView);

        /** Passing each menu ID as a set of Ids because each
         * menu should be considered as top level destinations.
         */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_list, R.id.nav_task, R.id.nav_approval, R.id.nav_verifikasi,
                R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initViewsUser(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        tvNama = headerView.findViewById(R.id.tvNamaHeader);
        tvLevel = headerView.findViewById(R.id.tvLevelHeader);

        tvNama.setText(Preferences.getNama(this));
        tvLevel.setText(Preferences.getLevel(this));

        Common.getIDJabatan(this);
    }

    private void initViews() {
        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        contentHeader = findViewById(R.id.contentHeader);
        contentHeaderStatus = findViewById(R.id.contentHeaderStatus);
        contentMain = findViewById(R.id.contentMain);
        simpleSearchView = findViewById(R.id.simpleSearchView);

        tvSelectRapat = findViewById(R.id.tvSelectRapat);
        tvRapatAktif = findViewById(R.id.tvRapatAktif);
        spSubrapat = findViewById(R.id.spSubrapat);
        spStatusMonitoring = findViewById(R.id.spStatusMonitoring);
        tvRapatAktif.setTypeface(tfLight);

        /** Search Event */
        simpleSearchView.setOnSearchViewListener(new SimpleSearchViewListener() {
            @Override
            public void onSearchViewClosedAnimation() {
                Animation slideToBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_to_bottom);
                Animation slideToBottomHalf = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_to_bottom_half);
                contentHeader.setAnimation(slideToBottom);
                contentMain.setAnimation(slideToBottomHalf);
                contentHeader.setVisibility(View.VISIBLE);
                EventBus.getDefault().postSticky(new SearchQuery(false, ""));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dismissKeyboardShortcutsHelper();
                }
                super.onSearchViewClosedAnimation();
            }

            @Override
            public void onSearchViewShown() {
                Animation slideToTop = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_to_top);
                Animation slideToTopHalf = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_to_top_half);
                contentHeader.setAnimation(slideToTop);
                contentMain.setAnimation(slideToTopHalf);
                new Handler().postDelayed(() -> {
                    contentHeader.setVisibility(View.GONE);
                }, 400);
                super.onSearchViewShown();
            }
        });
        simpleSearchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchByQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                startSearchByQuery(newText);
                return true;
            }

            @Override
            public boolean onQueryTextCleared() {
                return false;
            }
        });

        /** Spinner Status Monitoring Event */
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.status_monitoring, R.layout.spinner_transparent);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatusMonitoring.setAdapter(adapter);
        spStatusMonitoring.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = String.valueOf(parent.getItemAtPosition(position));
                EventBus.getDefault().postSticky(new SpinnerStatusMonitoring(true, status));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                EventBus.getDefault().postSticky(new SpinnerStatusMonitoring(false, ""));
            }
        });

        /** Default Gone spinner SubRapat */
        spSubrapat.setVisibility(View.GONE);

        /** Event Click Listener */
        fab.setOnClickListener(this);
        tvSelectRapat.setOnClickListener(this);
    }

    private void startSearchByQuery(String query) {
        EventBus.getDefault().postSticky(new SearchQuery(true, query));
        Log.d("query", query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        menuSearch = menu.findItem(R.id.actionSearch);

        if (isHideMenu)
            menuSearch.setVisible(false);
        else
            menuSearch.setVisible(true);

        simpleSearchView.setMenuItem(menuSearch);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (simpleSearchView.onBackPressed()) {
            EventBus.getDefault().postSticky(new SearchQuery(false, ""));
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Menu FeedBack/BugReport
     */
    public void menuFeedback(MenuItem item) {
        drawer.closeDrawers();
        Common.showMenuFeedback(this);
    }

    /**
     * Menu Logout
     */
    public void menuLogout(MenuItem item) {
        drawer.closeDrawers();
        Common.showMenuLogout(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Common.updateStatusUser(this, "on");
    }

    @Override
    protected void onStop() {
        simpleSearchView.closeSearch();
        Common.updateStatusUser(this, "off");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHideStatusContentHeader(HideStatusContentHeader event) {
        if (event.isHidden())
            contentHeaderStatus.setVisibility(View.GONE);
        else
            contentHeaderStatus.setVisibility(View.VISIBLE);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHideAllContentHeader(HideAllContentHeader event) {
        if (event.isAllHidden())
            contentHeader.setVisibility(View.GONE);
        else
            contentHeader.setVisibility(View.VISIBLE);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHideSearchMenu(HideSearchMenu event) {
        isHideMenu = event.isHidden();
        if (event.isHidden())
            menuSearch.setVisible(false);
        else
            menuSearch.setVisible(true);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHideFab(HideFab event) {
        if (event.isHidden())
            fab.hide();
        else
            fab.show();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onRefreshProfile(RefreshUpdated event) {
        if (event.isProfileUpdated()) {
            initViewsUser(navigationView);
            event.setProfileUpdated(false);
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResetSelectesStatusMonitoring(SpinnerStatusMonitoring event) {
        if (!event.isSelected())
            spStatusMonitoring.setSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSelectRapat:
                dialogSelectRapat();
                break;
            case R.id.fab:
                if (idSubrapat == 0)
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Info!")
                            .setContentText("Silahkan Pilih Rapat terelebih dahulu!")
                            .show();
                else {
                    Common.namaRapat = resultNamaRapat;
                    Common.namaSubrapat = resultNamaSubrapat;
                    Common.idSubrapat = idSubrapat;
                    startActivity(new Intent(this, TambahKeputusanActivity.class));
                }

                break;
        }
    }

    /**
     * Dialog Select
     */
    private void dialogSelectRapat() {
        SweetAlertDialog sweetLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        sweetLoading.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        sweetLoading.setTitleText("Please wait...").show();

        List<String> rapatList = new ArrayList<>();
        mService.getRapatAktif(Preferences.getIDJabatan(getBaseContext()))
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getAllRapat() != null) {
                            for (RapatModel rapat : response.body().getAllRapat()) {
                                String namaRapat = rapat.getNama_rapat();
                                rapatList.add(namaRapat);
                            }

                            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_select_rapat, null);
                            Spinner spRapat = view.findViewById(R.id.spRapat);

                            SweetAlertDialog sweetSelectRapat = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.NORMAL_TYPE)
                                    .setTitleText("Pilih Rapat")
                                    .setConfirmText("Choose")
                                    .showContentText(false)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        String selectedRapat = spRapat.getSelectedItem().toString();
                                        tvSelectRapat.setText(selectedRapat);

                                        /** Get ID Subrapat */
                                        getIDRapat(selectedRapat, sweetAlertDialog);
                                    });
                            sweetSelectRapat.setCanceledOnTouchOutside(true);
                            sweetSelectRapat.setOnShowListener(dialog -> {
                                ArrayAdapter<String> adapterRapat = new ArrayAdapter<>(MainActivity.this,
                                        R.layout.spinner_white, rapatList);
                                adapterRapat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spRapat.setAdapter(adapterRapat);
                            });
                            sweetSelectRapat.show();
                            LinearLayout linearLayout = sweetSelectRapat.findViewById(R.id.loading);
                            int index = linearLayout.indexOfChild(linearLayout.findViewById(R.id.content_text));
                            linearLayout.setPadding(0, 0, 0, 30);
                            linearLayout.addView(view, index + 1);

                            sweetLoading.dismissWithAnimation();
                        } else
                            sweetLoading
                                    .setTitleText("Oops!")
                                    .setContentText("Anda tidak memiliki role, Silahkan hubungi ADMIN!")
                                    .setConfirmText("Close")
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        if (t.toString().contains("timeout"))
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(t.getMessage())
                                    .setConfirmText(getResources().getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);

                        else if (t.toString().contains("Unable to resolve host"))
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(getResources().getString(R.string.noConnection))
                                    .setConfirmText(getResources().getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                        else
                            sweetLoading.setTitleText("Oops!")
                                    .setContentText(t.getMessage())
                                    .setConfirmText(getResources().getString(R.string.textClose))
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        sweetAlertDialog.dismissWithAnimation();
                                    })
                                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    }
                });


    }

    private void getIDRapat(String selectedRapat, SweetAlertDialog sweetAlertDialog) {
        mService.getIdRapat(selectedRapat)
                .enqueue(new Callback<RapatModel>() {
                    @Override
                    public void onResponse(Call<RapatModel> call, Response<RapatModel> response) {
                        if (response.isSuccessful()) {
                            RapatModel rapatModel = response.body();

                            /** Set SubRapat */
                            setSubRapat(rapatModel.getId_rapat(), selectedRapat, sweetAlertDialog);
                        }
                    }

                    @Override
                    public void onFailure(Call<RapatModel> call, Throwable t) {
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog1 -> {
                                    sweetAlertDialog1.dismissWithAnimation();
                                })
                                .show();
                    }
                });
    }

    private void setSubRapat(int id_rapat, String selectedRapat, SweetAlertDialog sweetAlertDialog) {
        List<String> subrapatList = new ArrayList<>();
        mService.getSubrapatAktif(Preferences.getIDJabatan(getBaseContext()), id_rapat)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body().getAllSubrapat() != null) {
                            for (SubrapatModel subrapat : response.body().getAllSubrapat()) {
                                String namaSubrapat = subrapat.getNama_subrapat();
                                subrapatList.add(namaSubrapat);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, subrapatList);
                            spSubrapat.setAdapter(adapter);
                            spSubrapat.setVisibility(View.VISIBLE);

                            /** Set Result Rapat & Subrapat (Rapat Aktif)*/
                            setResultRapatAktif(selectedRapat, id_rapat, sweetAlertDialog);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText(t.getMessage())
                                .setConfirmText("Close")
                                .setConfirmClickListener(sweetAlertDialog1 -> {
                                    sweetAlertDialog1.dismissWithAnimation();
                                })
                                .show();
                    }
                });
    }

    private void setResultRapatAktif(String selectedRapat, int id_rapat, SweetAlertDialog sweetAlertDialog) {
        spSubrapat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubrapat = String.valueOf(parent.getItemAtPosition(position));

                mService.getIdSubrapat(selectedSubrapat, id_rapat)
                        .enqueue(new Callback<SubrapatModel>() {
                            @Override
                            public void onResponse(Call<SubrapatModel> call, Response<SubrapatModel> response) {
                                if (response.isSuccessful()) {
                                    /**
                                     * TODO RefreshLoadDataEvent (Update Session Subrapat)
                                     * Set Session ID SUBRAPAT
                                     */
                                    SubrapatModel subrapatModel = response.body();
                                    idSubrapat = subrapatModel.getId_subrapat();
                                    String rapatAktif = selectedRapat + " " + selectedSubrapat;
                                    /** Send Session Subrapat */
                                    EventBus.getDefault().postSticky(new RefreshLoadData(true, false, idSubrapat, ""));
                                    EventBus.getDefault().postSticky(new RefreshLoadData(false, true, idSubrapat, rapatAktif));

                                    /** Set Result Rapat Aktif */
                                    tvRapatAktif.setText(rapatAktif);
                                    resultNamaRapat = selectedRapat;
                                    resultNamaSubrapat = selectedSubrapat;

                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }

                            @Override
                            public void onFailure(Call<SubrapatModel> call, Throwable t) {
                                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops!")
                                        .setContentText(t.getMessage())
                                        .setConfirmText("Close")
                                        .setConfirmClickListener(sweetAlertDialog1 -> {
                                            sweetAlertDialog1.dismissWithAnimation();
                                        })
                                        .show();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}