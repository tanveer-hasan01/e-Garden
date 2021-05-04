package com.example.egarden;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.egarden.databinding.FragmentHomeBinding;

public class Home extends Fragment {

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.autoWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    binding.waterPump.setClickable(false);
                    binding.waterPump.setChecked(false);
                    Tools.snackInfo((Activity) getContext(),"Water pump is automatic On/OFF");

                } else {
                    binding.waterPump.setClickable(true);
                }
            }
        });






        return view;
    }
}