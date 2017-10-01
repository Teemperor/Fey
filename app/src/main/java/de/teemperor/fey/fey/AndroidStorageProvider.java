package de.teemperor.fey.fey;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class AndroidStorageProvider implements StorageProvider{

    private Context context;

    public AndroidStorageProvider(Context context) {
        this.context = context;
    }

    public FileOutputStream getOutput(String fileName) {
        try {
            return context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public FileInputStream getInput(String fileName) throws FileNotFoundException {
        return context.openFileInput(fileName);
    }
}
