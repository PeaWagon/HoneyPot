package com.jj.honeypot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PresentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presents);

        ArrayList<Present> presents = new ArrayList<>();
        try {
             presents = Presents.getPresentsFromFile(getAppFilesDir());
        } catch (ClassNotFoundException | java.io.IOException exception) {
            deletePresentsFileDialog();
        }

        Log.d("Justin",""+presents.size());
        // do stuff with presents
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_presents);
        PresentsAdapter presentsAdapter = new PresentsAdapter(presents,getAppFilesDir());
        recyclerView.setAdapter(presentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getAppFilesDir() {
        // getFilesDir can only be called within an app activity
        // since it needs the android 'context' from the activity
        // getFilesDir returns a file object, so we call getAbsolutePath
        // method to get the string
        return getFilesDir().getAbsolutePath();
    }

    public void onClickPresentsFab(View view) {
        Intent newPresentActivityIntent = new Intent(this, NewPresentActivity.class);
        startActivityForResult(newPresentActivityIntent,1);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Justin","onActivityResult");
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                finish();
                startActivity(getIntent());
            } else if (resultCode == RESULT_CANCELED) {
                // Do nothing
            }
        }
    }

    private void deletePresentsFileDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // the dialog is not dismissed if user clicks outside
        // alert dialog or clicks back button
        dialog.setCancelable(false);
        dialog.setMessage(R.string.dialog_delete_presents_file_message);
        dialog.setTitle(R.string.dialog_delete_presents_file_title);
        // delete the presents file if user clicks OK
        dialog.setPositiveButton(
            R.string.dialog_delete_presents_file_positive_button,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Presents.deletePresentsFile(getAppFilesDir());
                }
            }
        );
        // return to main activity if user clicks Cancel
        dialog.setNegativeButton(
            R.string.dialog_delete_presents_file_negative_button,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }
        );
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void deletePresentDialog(final int presentIndex) {
        // what happens when you click the delete fab next to
        // a given present
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.dialog_delete_present_title);
        dialog.setMessage(R.string.dialog_delete_present_message);
        dialog.setPositiveButton(
            R.string.dialog_delete_present_positive_button,
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    boolean deleteSuccess = Presents.deletePresent(getAppFilesDir(), presentIndex);
                    if (deleteSuccess) {
                        finish();
                        startActivity(getIntent());
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
    }

}
