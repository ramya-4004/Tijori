package com.example.lockmyfile.Files;

public class FileDetails {

    /**
     * 1 -> image
     * 2 -> video
     * 3 ->pdf
     */
    private String fileName;
    private int fileType;

    public FileDetails(String fileName, int imageType){
        this.fileName = fileName;
        this.fileType = imageType;
    }

    public String getFileName(){
        return fileName;
    }

    public int getFileType(){
        return fileType;
    }
}
