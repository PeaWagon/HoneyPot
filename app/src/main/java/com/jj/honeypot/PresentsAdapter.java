package com.jj.honeypot;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

//import static java.security.AccessController.getContext;


public class PresentsAdapter extends RecyclerView.Adapter<PresentsAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        String appFilesDir;
        public TextView name;
        public TextView price;
        public TextView store;
        public ImageView image;
        public FloatingActionButton deletePresent;
        public FloatingActionButton editPresent;

        public ViewHolder(View view, String appFilesDirPresentsAdapter) {
            super(view);
            appFilesDir = appFilesDirPresentsAdapter;
            name = (TextView) view.findViewById(R.id.textViewPresentName);
            price = (TextView) view.findViewById(R.id.textViewPresentPrice);
            store = (TextView) view.findViewById(R.id.textViewPresentStore);
            image = (ImageView) view.findViewById(R.id.imageViewPresentImage);
            deletePresent = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_deletePresent);
            editPresent = (FloatingActionButton) view.findViewById(R.id.floatingActionButton_editPresent);
        }
    }

    private ArrayList<Present> presents;
    private String appFilesDir;

    public PresentsAdapter(ArrayList<Present> inputPresents, String appFilesDirAdapter) {
        presents = inputPresents;
        appFilesDir = appFilesDirAdapter;
    }

    @NonNull
    @Override
    public PresentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View presentView = inflater.inflate(R.layout.present, parent, false);
        ViewHolder viewHolder = new ViewHolder(presentView, appFilesDir);
        return viewHolder;
    }

    public void deletePresentDialog(final int presentIndex, Context context,final String appFilesDir) {
        // what happens when you click the delete fab next to
        // a given present
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.dialog_delete_present_title);
        dialog.setMessage(R.string.dialog_delete_present_message);
        dialog.setPositiveButton(
                R.string.dialog_delete_present_positive_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean deleteSuccess = Presents.deletePresent(appFilesDir, presentIndex);
                        if (deleteSuccess) {
                            presents.remove(presentIndex);
                            notifyItemRemoved(presentIndex);
                            //notifyItemRangeChanged is necessary to cover the instance when the
                            //top item in the list is being removed.
                            notifyItemRangeChanged(presentIndex,getItemCount());
                        }
                    }
                }
        );
        dialog.setNegativeButton(
                R.string.dialog_delete_present_negative_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }
        );
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onBindViewHolder(final @NonNull PresentsAdapter.ViewHolder holder, final int position) {
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

        holder.deletePresent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePresentDialog(position,holder.name.getContext(),appFilesDir);
            }
        });
    }

    @Override
    public int getItemCount() {
        return presents.size();
    }
}
