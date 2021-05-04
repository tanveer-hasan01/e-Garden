package com.example.egarden;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
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



        if (binding.autoWatering.isChecked())
        {
            binding.waterPump.setClickable(false);
            binding.waterPump.setChecked(false);
        }else {

            binding.waterPump.setClickable(true);
        }


        binding.autoWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    Tools.snackInfo((Activity) getContext(),"Water pump is automatically On/OFF");
                    binding.waterPump.setChecked(false);

                } else {


                }
            }
        });



        binding.waterPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.autoWatering.isChecked())
                {

                    binding.waterPump.setChecked(false);
                    Tools.snackErrInfo((Activity) getContext(), "Turn off Automatic watering !", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }else {

                }
            }
        });







        return view;
    }
}