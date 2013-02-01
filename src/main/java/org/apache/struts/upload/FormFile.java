package org.apache.struts.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormFile {

    private File file;
    private String inputName;
    private String contentType;
    private int fileSize;
    private String fileName;
    private byte[] fileData;
    private InputStream inputStream;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getFileSize() {
        fileSize = (int) file.length();
        return fileSize;
    }

    public String getFileName() {
        fileName = file.getName();
        return fileName;
    }

    public byte[] getFileData() {
        this.fileData = new byte[this.fileSize];
        try {
            if (inputStream == null) {
                inputStream = new FileInputStream(file);
            }
            this.inputStream.read(fileData, 0, this.fileSize);
        } catch (IOException ex) {
            Logger.getLogger(FormFile.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(FormFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return fileData;
    }

    public void destroy() {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FormFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the inputName
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        try {
            inputStream = new FileInputStream(this.file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inputStream;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @param inputName the inputName to set
     */
    public void setInputName(String inputName) {
        this.inputName = inputName;
    }
}