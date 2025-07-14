package org.dmsextension.paperless.client.templates;

public class TDocumentUploadResponse implements IDto {

    private String guid;

    public TDocumentUploadResponse() { }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toJsonString() {
        return null;
    }
}
