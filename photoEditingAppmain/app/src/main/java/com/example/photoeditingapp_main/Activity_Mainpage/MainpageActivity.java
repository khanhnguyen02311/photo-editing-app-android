package com.example.photoeditingapp_main.Activity_Mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photoeditingapp_main.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainpageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_mainpage);
        TextView pageName = findViewById(R.id.pageName);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_page:
                        navController.navigate(R.id.action_global_home_page);
                        pageName.setText("HOMEPAGE");
                        break;
                    case R.id.discover_page:
                        navController.navigate(R.id.action_global_discover_page);
                        pageName.setText("DISCOVER");
                        break;
                    case R.id.account_page:
                        navController.navigate(R.id.action_global_account_page);
                        pageName.setText("ACCOUNT");
                        break;
                    case R.id.studio_page:
                        navController.navigate(R.id.action_global_studio_page);
                        pageName.setText("STUDIO");
                        break;
                }
                return true;
            }
        });

        ImageButton moreButton = findViewById(R.id.btn_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu= new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.more_button_menu_mainpage,popupMenu.getMenu());

                // Set icon for popup menu
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.sign_out:
                                break;
                            default:
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }
}