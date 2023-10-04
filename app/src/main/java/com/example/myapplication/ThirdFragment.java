package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentSecondBinding;
import com.example.myapplication.databinding.FragmentThirdBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.text.DecimalFormat;

import java.util.Random;

public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find bottom menu
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        // disable placeholder icon
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.placeholder);
        menuItem.setEnabled(false);

        // handle menu clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // go to main menu
            if (itemId == R.id.bot_nav_icon_1) {
                NavHostFragment.findNavController(ThirdFragment.this).navigate(R.id.action_ThirdFragment_to_FirstFragment);
                return true;
            }
            // go to information page
            if (itemId == R.id.bot_nav_icon_2) {
                // do nothing, already at information page
                return true;
            }
            return false;
        });

        // handle button clicks
        binding.intentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take user to website
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.investopedia.com/terms/e/equated_monthly_installment.asp#:~:text=An%20equated%20monthly%20installment%20(EMI)%20is%20a%20fixed%20payment%20made,is%20paid%20off%20in%20full."));
                startActivity(intent);
            }
        });
    }

}