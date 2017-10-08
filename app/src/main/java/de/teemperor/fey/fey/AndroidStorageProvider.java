package de.teemperor.fey.fey;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class AndroidStorageProvider implements StorageProvider{

    private Context context;

    public AndroidStorageProvider(Context context) {
        this.context = context;
    }

    public OutputStream getOutput(String fileName) {
        try {
            return context.openFileOutput(fileName, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream getInput(String fileName) throws FileNotFoundException {
        return context.openFileInput(fileName);
    }
}
