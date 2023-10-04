package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.text.DecimalFormat;

import java.util.Random;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
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

        // create bitmap
        Bitmap bitmap = Bitmap.createBitmap(1024, 1024, Bitmap.Config.ARGB_8888);

        // create canvas to draw
        Canvas canvas = new Canvas(bitmap);

        // set up colours and positions
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int center_x = canvas.getWidth() / 2;
        int center_y = canvas.getHeight() / 2;
        int color_primary = ContextCompat.getColor(getContext(), R.color.primary);

        // draw circles
        paint.setColor(Color.WHITE);
        canvas.drawCircle(center_x, center_y, 400, paint);
        paint.setColor(color_primary);
        canvas.drawCircle(center_x, center_y, 380, paint);

        // attach bitmap to imageview
        ImageView imageView = view.findViewById(R.id.my_image);
        imageView.setImageBitmap(bitmap);

        // get data sent from first fragment
        float principal = getArguments().getFloat("principal");
        float interest = getArguments().getFloat("interest");
        float period = getArguments().getFloat("period");
        float frequency = getArguments().getFloat("frequency");

        // calculate monthly payments
        interest = interest / 12;
        float emi = (float) (principal * interest * (Math.pow((1+interest), frequency)/(Math.pow((1+interest), frequency)-1)));
        float total_payment = (float)(emi * frequency);
        float total_interest = (float)(total_payment-principal);

        // format
        DecimalFormat df = new DecimalFormat("###,##0.00");
        String emi_string = "$" + df.format(emi);
        String payment_string = "$" + df.format(total_payment);
        String interest_string = "$" + df.format(total_interest);

        // set output
        TextView output = view.findViewById(R.id.monthly_payment);
        output.setText(emi_string);

        TextView output_payment = view.findViewById(R.id.f2_h1_ans);
        output_payment.setText(payment_string);

        TextView output_interest = view.findViewById(R.id.f2_h2_ans);
        output_interest.setText(interest_string);

        // handle menu clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // go to main menu
            if (itemId == R.id.bot_nav_icon_1) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                return true;

            }
            // go to information page
            if (itemId == R.id.bot_nav_icon_2) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_ThirdFragment);
            }
            return false;
        });

        binding.fabSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the first fragment
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // called when fragment is about to turn visible
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    // called when moving away from the fragment
    public void onPause() {
        super.onPause();
    }
}