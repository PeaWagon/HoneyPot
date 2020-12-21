package com.jj.honeypot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PresentsAdapter extends RecyclerView.Adapter<PresentsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView price;
        public TextView store;
        public ImageView image;
        public FloatingActionButton deletePresent;
        public FloatingActionButton editPresent;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textViewPresentName);
            price = (TextView) view.findViewById(R.id.textViewPresentPrice);
            store = (TextView) view.findViewById(R.id.textViewPresentStore);
            image = (ImageView) view.findViewById(R.id.imageViewPresentImage);
            deletePresent = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_deletePresent);
            editPresent = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_editPresent);
        }
    }

    private ArrayList<Present> presents;

    public PresentsAdapter(ArrayList<Present> inputPresents) {
        presents = inputPresents;
    }

    @NonNull
    @Override
    public PresentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View presentView = inflater.inflate(R.layout.present, parent, false);
        ViewHolder viewHolder = new ViewHolder(presentView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PresentsAdapter.ViewHolder holder, int position) {
        Present present = presents.get(position);
        // avoid null present error when attempting
        // to get properties
        if (present == null) {
            Log.d("Jen",
                "Warning. Ignored a null present at position: " + String.valueOf(position)
            );
            return;
        }
        TextView textViewName = holder.name;
        textViewName.setText(present.name);
        TextView textViewPrice = holder.price;
        textViewPrice.setText(String.valueOf(present.price));
        TextView textViewStore = holder.store;
        textViewStore.setText(present.store);
        ImageView imageViewImage = holder.image;
        imageViewImage.setImageResource(R.drawable.honeypot_logo);
    }

    @Override
    public int getItemCount() {
        return presents.size();
    }
}