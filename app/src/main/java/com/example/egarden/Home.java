package com.example.egarden;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;


import com.example.egarden.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static android.content.ContentValues.TAG;

public class Home extends Fragment {

    FragmentHomeBinding binding;
    MyPlantAdapter adapter;
    SharedPreference sharedPreference;
    DatabaseReference databaseRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreference = SharedPreference.getPreferences(getContext());
        databaseRef = FirebaseDatabase.getInstance().getReference("plants");
        binding.feb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scan();
            }
        });


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


        binding.waterPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


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
                       //.setQuery(FirebaseDatabase.getInstance().getReference().child("plants").orderByChild("email").startAt(sharedPreference.getEmail()).endAt(sharedPreference.getEmail() + "\uf8ff"), Model_data.class)

                        .setQuery(FirebaseDatabase.getInstance().getReference().child("plants"), Model_data.class)
                        .build();


        adapter = new MyPlantAdapter(options1, getContext());
        binding.recycler.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();

        return view;
    }

    public void scan() {
        IntentIntegrator intentIntegrator = new IntentIntegrator((Activity) getContext());
        intentIntegrator.setPrompt("Scan Device QR Code");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );

        if (intentResult.getContents() != null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ;
            builder.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    scan();
                }
            });
            builder.show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }

}