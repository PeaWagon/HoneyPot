package com.jj.honeypot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class NewPresentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_present);
    }

    private String getPresentsFile() {
        String appFilesDir = getFilesDir().getAbsolutePath();
        final String presentsFileName = "presents.dat";
        String presentsFileFull = appFilesDir + File.pathSeparator + presentsFileName;
        return presentsFileFull;
    }

    private boolean addPresentToFile(
            String presentNameValue, double presentPriceValue,
            String presentStoreValue, String presentPictureValue
    ) {
        // create a new present using the input variables
        // and write the contents to the presents file
        // returns true if the addition was successful
        // and false otherwise
        File presentsFile = new File(getPresentsFile());

        try {
            boolean createFileResult = presentsFile.createNewFile();
            FileOutputStream presentsFileStream;
            if (createFileResult) {
                presentsFileStream = new FileOutputStream(presentsFile);
            } else {
                presentsFileStream = new FileOutputStream(presentsFile, true);
            }
            Present present = new Present(
                    presentNameValue, presentPriceValue,
                    presentStoreValue, presentPictureValue
            );
            ObjectOutputStream presentsObjectStream = new ObjectOutputStream(presentsFileStream);
            presentsObjectStream.writeObject(present);
            presentsObjectStream.close();
            presentsFileStream.close();
            return true;
        } catch (java.io.IOException exception) {
            Log.d("Exception", exception.getLocalizedMessage());
            return false;
        } catch (SecurityException exception) {
            Log.d("Exception", exception.getLocalizedMessage());
            return false;
        }
    }

    public void onClickCreateNewPresentButton(View view) {
        // gets the contents of the NewPresentActivity form
        // and calls addPresentToFile
        EditText presentName = findViewById(R.id.editTextNewPresentName);
        String presentNameValue = presentName.getText().toString();

        EditText presentPrice = findViewById(R.id.editTextNewPresentPrice);
        double presentPriceValue = Double.parseDouble(presentPrice.getText().toString());

        EditText presentStore = findViewById(R.id.editTextNewPresentStore);
        String presentStoreValue = presentStore.getText().toString();

        EditText presentPicture = findViewById(R.id.editTextNewPresentPicture);
        String presentPictureValue = presentPicture.getText().toString();

        boolean addPresentResult = addPresentToFile(
                presentNameValue, presentPriceValue, presentStoreValue, presentPictureValue
        );
        if (addPresentResult) {
            Log.d("Present", "add present success");
            finish();
        } else {
            Log.d("Present", "add present failure");
        }
    }

    public void onClickCancelNewPresentButton(View view) {
        finish();
    }
}