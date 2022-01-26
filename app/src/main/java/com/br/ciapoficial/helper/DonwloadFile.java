package com.br.ciapoficial.helper;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DonwloadFile extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        Log.v(TAG, "download() Method HAVE PERMISSIONS ");

        String fileUrl= strings[0];
        String fileName = strings[1];
        String extStorageDirectory= Environment.getExternalStorageDirectory().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File pdfFile = new File(folder, fileName);
        Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
        Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

        try {
            pdfFile.createNewFile();
            Log.v(TAG, "doInBackground() file created " + pdfFile);

        } catch (IOException e) {
            e.printStackTrace();
            Log.v(TAG, "doInBackground() error " + e.getMessage());
            Log.v(TAG, "doInBackground() error " + e.getStackTrace());

        }
        FileDownloader.downloadFile(fileUrl, pdfFile);
        Log.v(TAG, "doInBackground() file download completed");

        return null;
    }
}
