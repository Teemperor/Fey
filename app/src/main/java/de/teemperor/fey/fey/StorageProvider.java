package de.teemperor.fey.fey;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StorageProvider {
    OutputStream getOutput(String fileName);
    InputStream getInput(String fileName) throws FileNotFoundException;
}
