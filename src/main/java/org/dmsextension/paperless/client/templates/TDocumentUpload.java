package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;

public class TDocumentUpload implements IDto {
    private String created;
    private File document;
    private String title;
    private Integer correspondent;
    @Json(name="document_type")
    private Integer documentType;
    @Json(name="storage_path")
    private Integer storagePath;
    private List<Integer> tags;
    @Json(name="archive_serial_number")
    private Integer archiveSerialNumber;
    @Json(name="custom_fields")
    private List<Integer> customFields;
    @Json(name="from_webui")
    private Boolean fromWebUi;

    public TDocumentUpload() { }

    public MultipartBody getRequestBody() throws IllegalStateException {
        if (this.document == null) {
            throw new IllegalStateException("No document given for upload");
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart(
                        "document",
                        this.document.getName(),
                        RequestBody.create(
                                this.document,
                                MediaType.parse("application/pdf")
                        )
                )
                .addFormDataPart("title", this.title != null && !this.title.isEmpty() ? this.title : this.document.getName());

        if (this.documentType != null) builder.addFormDataPart("document_type", "" + this.documentType);
        if (this.correspondent != null) builder.addFormDataPart("correspondent", "" + this.correspondent);
        if (this.fromWebUi != null) builder.addFormDataPart("from_webui", "" + this.fromWebUi);
        if (this.archiveSerialNumber != null) builder.addFormDataPart("archive_serial_number", "" + this.archiveSerialNumber);
        if (this.created != null) builder.addFormDataPart("created", this.created);
        if (this.customFields != null && !this.customFields.isEmpty()) builder.addFormDataPart("custom_fields", this.customFields.toString());
        if (this.storagePath != null) builder.addFormDataPart("storage_path", "" + this.storagePath);
        if (this.tags != null && !this.tags.isEmpty()) builder.addFormDataPart("tags", this.tags.toString());

        return builder.build();
    }

    @Override
    public String toString() {
        return "TDocumentUpload{" +
                "created='" + created + '\'' +
                ", document='" + document + '\'' +
                ", title='" + title + '\'' +
                ", correspondent=" + correspondent +
                ", documentType=" + documentType +
                ", storagePath=" + storagePath +
                ", tags=" + tags +
                ", archiveSerialNumber=" + archiveSerialNumber +
                ", customFields=" + customFields +
                ", fromWebUi=" + fromWebUi +
                '}';
    }

    @Override
    public String toJsonString() {
        return null;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public File getDocument() {
        return document;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCorrespondent() {
        return correspondent;
    }

    public void setCorrespondent(Integer correspondent) {
        this.correspondent = correspondent;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public Integer getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(Integer storagePath) {
        this.storagePath = storagePath;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public Integer getArchiveSerialNumber() {
        return archiveSerialNumber;
    }

    public void setArchiveSerialNumber(Integer archiveSerialNumber) {
        this.archiveSerialNumber = archiveSerialNumber;
    }

    public List<Integer> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<Integer> customFields) {
        this.customFields = customFields;
    }

    public boolean isFromWebUi() {
        return fromWebUi;
    }

    public void setFromWebUi(boolean fromWebUi) {
        this.fromWebUi = fromWebUi;
    }
}
