package com.jj.honeypot;

import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Presents {

    private static String getPresentsFile(String appFilesDir) {
        final String presentsFileName = "presents.dat";
        String presentsFileFull = appFilesDir + File.pathSeparator + presentsFileName;
        return presentsFileFull;
    }

    public static boolean deletePresent(String appFilesDir, int presentIndex) {
        // delete a present from the specified index
        // (starting from index 0) and rewrite presents to file
        // returns true if deletion was successful
        try {
            ArrayList<Present> presents = getPresentsFromFile(appFilesDir);
            presents.remove(presentIndex);
            writePresentsToFile(appFilesDir, presents);
        } catch (java.lang.ClassNotFoundException | java.io.IOException exception) {
            Log.d("Exception", exception.toString());
            return false;
        } catch (IndexOutOfBoundsException exception) {
            Log.d("Exception", exception.toString());
            return false;
        }
        return true;
    }

    public static void deletePresentsFile(String appFilesDir) {
        String presentsFileName = getPresentsFile(appFilesDir);
        File presentsFile = new File(presentsFileName);
        boolean deleteResult = presentsFile.delete();
        if (deleteResult) {
            Log.d("Presents", "Presents file was deleted.");
        } else {
            Log.d("Presents", "Presents file was not deleted.");
        }
    }

    public static ArrayList<Present> getPresentsFromFile(String appFilesDir)
        throws SecurityException, ClassNotFoundException, java.io.IOException {
        // open the presents file and put the presents in an ArrayList
        // if the presents file cannot be open or is corrupted (the presents
        // object has fundamentally changed) then an exception will be raised
        String presentsFileName = getPresentsFile(appFilesDir);
        File presentsFile = new File(presentsFileName);
        ArrayList<Present> presents = new ArrayList<>();

        if (!presentsFile.exists()) {
            return presents;
        }

        try {
            FileInputStream presentsFileStream = new FileInputStream(presentsFile);
            ObjectInputStream presentsObjectStream = new ObjectInputStream(presentsFileStream);
            presents = (ArrayList<Present>) presentsObjectStream.readObject();
            presentsObjectStream.close();
            presentsFileStream.close();
        } catch (ClassNotFoundException exception) {
            Log.d("Exception", "ClassNotFoundException: " + exception.getLocalizedMessage());
            throw exception;
        } catch (java.io.IOException exception) {
            Log.d("Exception", "java.io.IOException: " + exception.getLocalizedMessage());
            throw exception;
        } catch (SecurityException exception) {
            Log.d("Exception", "SecurityException: " + exception.getLocalizedMessage());
            throw exception;
        }
        return presents;
    }

    public static void writePresentsToFile(String appFilesDir, ArrayList<Present> presents)
        throws java.io.IOException, SecurityException {
        // objects cannot easily be passed between activities
        // also, when writing objects to an existing serialized file,
        // you need to re-write the contents and then add the
        // new object => instead, we chose to serialize a single
        // ArrayList object to the file

        String presentsFileName = getPresentsFile(appFilesDir);
        File presentsFile = new File(presentsFileName);

        try {
            FileOutputStream presentsFileStream = new FileOutputStream(presentsFile);;
            ObjectOutputStream presentsObjectStream = new ObjectOutputStream(presentsFileStream);
            presentsObjectStream.writeObject(presents);
            presentsObjectStream.close();
            presentsFileStream.close();
        } catch (java.io.IOException exception) {
            Log.d("Exception", exception.getLocalizedMessage());
            throw exception;
        } catch (SecurityException exception) {
            Log.d("Exception", exception.getLocalizedMessage());
            throw exception;
        }
    }
}
