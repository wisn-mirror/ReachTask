package com.wisn.mainmodule.entity.bean;

/**
 * @author Wisn
 * @time 2018/2/5 12:07
 */


public class FileItem {
    public int filePic;
    public String fileName;
    public String filePath;
    public String fileModifiedTime;

    public FileItem() {
    }

    public FileItem(int filePic, String fileName, String filePath, String fileModifiedTime) {
        this.filePic = filePic;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileModifiedTime = fileModifiedTime;
    }

    public int getFilePic() {
        return filePic;
    }

    public void setFilePic(int filePic) {
        this.filePic = filePic;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileModifiedTime() {
        return fileModifiedTime;
    }

    public void setFileModifiedTime(String fileModifiedTime) {
        this.fileModifiedTime = fileModifiedTime;
    }
}
