package com.jj.honeypot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class NewPresentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_present);
    }

    private String getAppFilesDir() {
        // getFilesDir can only be called within an app activity
        // since it needs the android 'context' from the activity
        // getFilesDir returns a file object, so we call getAbsolutePath
        // method to get the string
        return getFilesDir().getAbsolutePath();
    }

    private boolean addPresentToFile(Present present) {
        // read presents from the presents file into an
        // arraylist, add a new present object to the
        // arraylist, and write the new arraylist to the
        // presents file
        ArrayList<Present> presents;
        try {
             presents = Presents.getPresentsFromFile(getAppFilesDir());
        } catch (java.io.IOException | ClassNotFoundException exception) {
            newPresentDialog(
                "Could not read existing presents from file: "
                + exception.getLocalizedMessage()
            );
            return false;
        }
        presents.add(present);
        try {
            Presents.writePresentsToFile(getAppFilesDir(), presents);
        } catch (java.io.IOException exception) {
            newPresentDialog(
                "Could not write present to file: "
                + exception.getLocalizedMessage()
            );
            return false;
        }
        return true;
    }

    private Present validatePresentInputs(
        String name, String price, String store, String image
    ) {
        Double presentPrice;
        try {
             presentPrice = Double.parseDouble(price);
        } catch (NumberFormatException exception) {
            Log.d("Exception","NumberFormatException: " + exception.getLocalizedMessage());
            newPresentDialog("Invalid price. Price cannot be empty.");
            return null;
        }
        // should not occur since price cannot be negative in the form
        if (presentPrice < 0.0) {
            newPresentDialog("Price cannot be negative.");
            return null;
        }
        return new Present(name, presentPrice, store, image);
    }

    private void newPresentDialog(String message) {
        // message indicates why new present could not be added
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        // the dialog is not dismissed if user clicks outside
        // alert dialog or clicks back button
        dialog.setCancelable(false);
        dialog.setMessage(
                getResources().getString(R.string.dialog_new_present_message) + " " + message
        );
        dialog.setTitle(R.string.dialog_new_present_title);
        // allow the user to try and correct the present
        // by clicking OK
        dialog.setPositiveButton(
                R.string.dialog_new_present_positive_button,
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

    public void onClickCreateNewPresentButton(View view) {
        // gets the contents of the NewPresentActivity form
        // and calls addPresentToFile
        EditText name = findViewById(R.id.editTextNewPresentName);
        String presentName = name.getText().toString();

        EditText price = findViewById(R.id.editTextNewPresentPrice);
        String presentPrice = price.getText().toString();

        EditText store = findViewById(R.id.editTextNewPresentStore);
        String presentStore = store.getText().toString();

        EditText image = findViewById(R.id.editTextNewPresentImage);
        String presentImage = image.getText().toString();

        Present present = validatePresentInputs(
            presentName, presentPrice, presentStore, presentImage
        );

        // input validation failed and dialog box will appear
        if (present == null) {
            return;
        }

        boolean addPresentResult = addPresentToFile(present);
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