package com.example.egarden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.egarden.databinding.ItemMyPlantsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;

public class MyPlantAdapter extends FirebaseRecyclerAdapter<Model_data,MyPlantAdapter.Holder> {
    Context context;

    public MyPlantAdapter(FirebaseRecyclerOptions<Model_data> options, Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public MyPlantAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMyPlantsBinding binding = ItemMyPlantsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new Holder(binding);
    }



    @Override
    protected void onBindViewHolder(@NonNull Holder holder, int position, @NonNull Model_data model) {



    }


    public class Holder extends RecyclerView.ViewHolder {

        ItemMyPlantsBinding binding;
        public Holder(ItemMyPlantsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
