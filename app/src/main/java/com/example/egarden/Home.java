package com.example.egarden;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class Home extends Fragment {

    FragmentHomeBinding binding;
    MyPlantAdapter adapter;
    SharedPreference sharedPreference;
    DatabaseReference databaseRef;
    DatabaseReference switchRef,lastSend_reference;
    Snackbar snackbar;
    FirebaseDatabase firebaseDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sharedPreference = SharedPreference.getPreferences(getContext());
        firebaseDatabase=FirebaseDatabase.getInstance();

        databaseRef = FirebaseDatabase.getInstance().getReference("plants");
        switchRef = FirebaseDatabase.getInstance().getReference("devices").child("device1");
        lastSend_reference=firebaseDatabase.getReference("devices/device1/last_watering");



        lastSend_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

              binding.lastWatering.setText("  Last watering : "+snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        if (sharedPreference.getPump().equals("off")) {
            binding.waterPump.setChecked(false);
        } else if (sharedPreference.getPump().equals("on")) {
            binding.waterPump.setChecked(true);
        }


        if (sharedPreference.getAutoWatering().equals("off")) {
            binding.autoWatering.setChecked(false);
        } else if (sharedPreference.getAutoWatering().equals("on")) {
            binding.autoWatering.setChecked(true);
        }


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

                    switchRef.child("automatic_watering").setValue("on");
                    sharedPreference.setAutoWatering("on");

                    Tools.snackInfo((Activity) getContext(), "Water pump is automatically On/OFF");
                    binding.waterPump.setChecked(false);

                } else {

                    switchRef.child("automatic_watering").setValue("off");
                    sharedPreference.setAutoWatering("off");
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


                    if (isChecked) {

                        switchRef.child("pump").setValue("on");

                        DateFormat dateFormat = new SimpleDateFormat("h:mm a");
                        Date date = new Date();
                        switchRef.child("last_watering").setValue(""+dateFormat.format(date));


                        sharedPreference.setPump("on");
                        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "Water pump is running !",
                                Snackbar.LENGTH_INDEFINITE)
                                .setActionTextColor(Color.WHITE);

                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                        snackbar.show();
                    }else {

                        switchRef.child("pump").setValue("off");
                        sharedPreference.setPump("off");
                        snackbar.dismiss();

                    }


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