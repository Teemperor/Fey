package de.teemperor.fey.fey;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public interface StorageProvider {
    FileOutputStream getOutput(String fileName);
    FileInputStream getInput(String fileName) throws FileNotFoundException;
}
