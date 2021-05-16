package com.example.egarden;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.example.egarden.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends Fragment {

    FragmentHomeBinding binding;
    MyPlantAdapter adapter;
    SharedPreference sharedPreference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreference = SharedPreference.getPreferences(getContext());

        if (binding.autoWatering.isChecked()) {
            binding.waterPump.setClickable(false);
            binding.waterPump.setChecked(false);
        } else {

            binding.waterPump.setClickable(true);
        }


        binding.autoWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    Tools.snackInfo((Activity) getContext(), "Water pump is automatically On/OFF");
                    binding.waterPump.setChecked(false);

                } else {


                }
            }
        });


        binding.waterPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.autoWatering.isChecked()) {

                    binding.waterPump.setChecked(false);
                    Tools.snackErrInfo((Activity) getContext(), "Turn off Automatic watering !", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {

                }
            }
        });


        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Model_data> options1 =
                new FirebaseRecyclerOptions.Builder<Model_data>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("plants").
                                startAt(sharedPreference.getData()).orderByChild(sharedPreference.getData()).
                                endAt(sharedPreference.getData() + "\uf8ff"), Model_data.class)
                        .build();


        adapter = new MyPlantAdapter(options1, getContext());
        binding.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}