package com.seener.usedarts.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.seener.usedarts.R;
import com.seener.usedarts.constants.FirebaseContants;
import com.seener.usedarts.constants.LoginStatus;
import com.seener.usedarts.database.DatabaseOperations;
import com.seener.usedarts.databinding.ActivityMainBinding;
import com.seener.usedarts.model.realm.CurrentUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.purple_500));
        }

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 在这里处理搜索提交事件
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 在这里处理搜索文本变化事件
                return true;
            }
        });

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setUpNavHeader();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setUpNavHeader() {
        Observable.create(emitter -> {
                    String email  = DatabaseOperations.getInstance().getCurrentUserEmail();
                    if (email != null) {
                        Log.d("LOGIN", "email:" + email);
                        emitter.onNext(email);
                    } else {
                        emitter.onNext("");
                    }
                    DatabaseOperations.getInstance().close();
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {

                    String email = String.valueOf(result);
                    if (!TextUtils.isEmpty(email)) {
                        View headerView = binding.navView.getHeaderView(0);
                        if (headerView != null) {
                            TextView userEmailTextView = headerView.findViewById(R.id.user_email);
                            if (userEmailTextView != null) {
                                userEmailTextView.setText(email);
                            }
                            TextView nickNameTextView = headerView.findViewById(R.id.nick_name);
                            if (nickNameTextView != null) {
                                nickNameTextView.setText(email);
                            }
                        }
                    }

                }, throwable -> {
                    Log.e("LOGIN", "setUpNavHeader:" + throwable);
                });
    }
}