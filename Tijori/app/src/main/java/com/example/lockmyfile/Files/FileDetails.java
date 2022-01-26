package com.example.lockmyfile.Files;

public class FileDetails {

    /**
     * 1 -> image
     * 2 -> video
     * 3 ->pdf
     */
    private String filePath;
    private String fileName;

    public FileDetails(String filePath, String fileName){
        this.filePath = filePath;
        this.fileName= fileName;
    }

    public String getFileName(){
        return fileName;
    }

    public String getFilePath(){
        return filePath;
    }
}
