package org.dmsextension.paperless.client.templates;

import java.io.File;

public class TDocumentDownload implements IDto {
    /**
     * Downloaded file
     */
    private File file;

    /**
     * Default constructor
     */
    public TDocumentDownload() {

    }

    /**
     * {@inheritDoc}
     * Returns downloaded file as string in JSON format
     * @return
     */
    @Override
    public String toJsonString() {
        return "{ \"file\": \"" + this.file.toString() + "\" }";
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
