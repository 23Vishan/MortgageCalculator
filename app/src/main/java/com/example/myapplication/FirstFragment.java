package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.util.Objects;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

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
                // do nothing, already at main menu
                return true;
            }
            // go to information page
            if (itemId == R.id.bot_nav_icon_2) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_ThirdFragment);
            }
            return false;
        });

        // set up dropdown menu
        String[] dropdown_list = getResources().getStringArray(R.array.payment_frequency);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, dropdown_list);
        binding.autoCompleteFrequency.setAdapter(arrayAdapter);

        // set background of dropdown menu
        binding.autoCompleteFrequency.setDropDownBackgroundDrawable(getResources().getDrawable(R.drawable.transparent_square));

        // when user exits the principal amount text field
        TextInputEditText principal_Amount = view.findViewById(R.id.editText_Principal);
        TextInputLayout til_1 = view.findViewById(R.id.textField_Principal);
        principal_Amount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // get text
                String tmp = Objects.requireNonNull(principal_Amount.getText()).toString();
                tmp = tmp.replaceAll("[^0-9]", "");

                // reformat text
                if (tmp.length() != 0 && tmp.length() <= 7) {
                    DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
                    String updated_text = formatter.format(Long.parseLong(tmp));
                    principal_Amount.setText(updated_text);
                } else {
                    // do not reformat if input is abnormally large
                    principal_Amount.setText(tmp);
                }

                // add helper messages
                if (tmp.length() <= 7) {
                    try {
                        int value = Integer.parseInt(tmp);
                        if (value >= 9000000 || value <= 20000) {
                            til_1.setError("Select a value between 20,000 and 9,000,000");
                        } else {
                            // valid value
                            til_1.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // empty text-field
                        til_1.setError(null);
                    }
                } else {
                    // abnormally high number
                    til_1.setError("Select a value between 20,000 and 9,000,000");
                }
            }
        });

        // when user exits the interest text field
        TextInputEditText interest = view.findViewById(R.id.editText_Interest);
        TextInputLayout til_2 = view.findViewById(R.id.textField_Interest);
        interest.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // get text
                String tmp = Objects.requireNonNull(interest.getText()).toString();
                tmp = tmp.replaceAll("[^0-9]", "");

                // add helper messages
                if (tmp.length() <= 4) {
                    try {
                        float value = Float.parseFloat(tmp);
                        if (value <= 0 || value >= 100) {
                            til_2.setError("Select a value between 0 and 100");
                        } else {
                            // valid value
                            til_2.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // empty text-field
                        til_2.setError(null);
                    }
                } else {
                    // abnormally high number
                    til_2.setError("Select a value between 0 and 100");
                }
            }
        });

        // when user exits the period text field
        TextInputEditText period = view.findViewById(R.id.editText_Period);
        TextInputLayout til_3 = view.findViewById(R.id.textField_Period);
        period.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // get text
                String tmp = Objects.requireNonNull(period.getText()).toString();
                tmp = tmp.replaceAll("[^0-9]", "");

                // add helper messages
                if (tmp.length() <= 3) {
                    try {
                        int value = Integer.parseInt(tmp);
                        if (value <= 0 || value >= 100) {
                            til_3.setError("Select a value between 0 and 100");
                        } else {
                            // valid value
                            til_3.setError(null);
                        }
                    } catch (NumberFormatException e) {
                        // empty text-field
                        til_3.setError(null);
                    }
                } else {
                    // abnormally high number
                    til_3.setError("Select a value between 0 and 100");
                }
            }
        });

        binding.fabFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // turn payment frequency into a numerical value
                AutoCompleteTextView autoComplete = binding.autoCompleteFrequency;

                // check input
                if (isTextEditEmpty(principal_Amount) || isTextEditEmpty(interest) || isTextEditEmpty(period) || isOptionNotSelected(autoComplete)) {
                    // provide user feedback
                    Toast.makeText(view.getContext(), "Input Parameters Missing", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // extract data
                    String tmp = principal_Amount.getText().toString().replace(",", "");
                    float tmp_amount = Float.parseFloat(tmp);

                    tmp = interest.getText().toString();
                    float tmp_interest = Float.parseFloat(tmp);

                    tmp = period.getText().toString();
                    float tmp_period = Float.parseFloat(tmp);

                    float tmp_frequency = 0.0f;

                    // turn payment frequency into a numerical value
                    tmp = (String) autoComplete.getText().toString();
                    switch (tmp) {
                        case "Weekly":
                            tmp_frequency = tmp_period * 52;
                            break;
                        case "Bi-Weekly":
                            tmp_frequency = tmp_period * 26;
                            break;
                        case "Monthly":
                            tmp_frequency = tmp_period * 12;
                            break;
                    }

                    // put data in a bundle
                    Bundle bundle = new Bundle();
                    bundle.putFloat("principal", tmp_amount);
                    bundle.putFloat("interest", tmp_interest / 100);
                    bundle.putFloat("period", tmp_period);
                    bundle.putFloat("frequency", tmp_frequency);

                    // move and send data to second fragment
                    Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                }
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

    // returns true if empty
    public boolean isTextEditEmpty(TextInputEditText input) {
        return input.getText().toString().isEmpty();
    }

    // returns true if option not selected
    public boolean isOptionNotSelected(AutoCompleteTextView input) {
        return input.getText().toString().isEmpty();
    }
}