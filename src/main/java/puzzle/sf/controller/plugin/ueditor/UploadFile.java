package puzzle.sf.controller.plugin.ueditor;

import java.io.File;
import java.io.InputStream;

public class UploadFile {
    private File file;
    public void setFile(File file){ this.file = file;}
    public File getFile(){ return this.file; }
    private String contentType;
    public void setContentType(String contentType){ this.contentType = contentType;}
    public String getContentType(){ return this.contentType; }
    private String fileName;
    public void setFileName(String fileName){ this.fileName = fileName;}
    public String getFileName(){ return this.fileName; }
    private String savePath;
    public void setSavePath(String savePath){ this.savePath = savePath;}
    public String getSavePath(){ return this.savePath; }
    private String url;
    public void setUrl(String url){ this.url = url;}
    public String getUrl(){ return this.url; }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private InputStream inputStream;

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private long fileSize;
}
