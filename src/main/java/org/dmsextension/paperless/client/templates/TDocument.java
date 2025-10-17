package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.List;
import java.util.Objects;

public class TDocument implements IDto {

    private Integer analyzeTries = 0;
    private Integer id;
    @Json(name="document_type")
    private Integer documentType;
    @Json(name="storage_path")
    private Integer storagePath;
    private String title;
    private String content;
    private List<Integer> tags;
    private String created;
    private String modified;
    private String added;
    @Json(name="deleted_at")
    private String deletedAt;
    @Json(name="mime_type")
    private String mimeType;
    @Json(name="custom_fields")
    private List<TCustomFieldValue> customFields;
    private Integer owner;
    @Json(name="user_can_change")
    private Boolean userCanChange;
    @Json(name="archive_serial_number")
    private Integer archiveSerialNumber;
    @Json(name="created_date")
    private String createdDate;
    @Json(name="original_file_name")
    private String originalFileName;
    @Json(name="archived_file_name")
    private String archivedFileName;
    @Json(name="is_shared_by_requester")
    private Boolean isSharedByRequester;

    public TDocument() { }

    @Override
    public String toJsonString() {
        return String.format(
                """
                {
                "id"=%s,
                "document_type"="%s",
                "title"="%s",
                "custom_fields"="%s"
                }        
                """,
                this.id,
                this.documentType,
                this.title,
                this.customFields
        );
    }

    @Override
    public String toString() {
        return "TDocument{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", storagePath=" + storagePath +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", added='" + added + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", customFields=" + customFields +
                ", owner=" + owner +
                ", userCanChange=" + userCanChange +
                ", archiveSerialNumber=" + archiveSerialNumber +
                ", createdDate='" + createdDate + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", archivedFileName='" + archivedFileName + '\'' +
                ", isSharedByRequester=" + isSharedByRequester +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TDocument tDocument)) return false;
        return getId() == tDocument.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }


    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public List<TCustomFieldValue> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<TCustomFieldValue> customFields) {
        this.customFields = customFields;
    }

    public Integer getAnalyzeTries() {
        return analyzeTries;
    }

    public void setAnalyzeTries(Integer analyzeTries) {
        this.analyzeTries = analyzeTries;
    }

    public void addAnalyzeTry() {
        this.analyzeTries++;
    }

    public Boolean getUserCanChange() {
        return userCanChange;
    }

    public void setUserCanChange(Boolean userCanChange) {
        this.userCanChange = userCanChange;
    }

    public Integer getArchiveSerialNumber() {
        return archiveSerialNumber;
    }

    public void setArchiveSerialNumber(Integer archiveSerialNumber) {
        this.archiveSerialNumber = archiveSerialNumber;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getArchivedFileName() {
        return archivedFileName;
    }

    public void setArchivedFileName(String archivedFileName) {
        this.archivedFileName = archivedFileName;
    }

    public Boolean getSharedByRequester() {
        return isSharedByRequester;
    }

    public void setSharedByRequester(Boolean sharedByRequester) {
        isSharedByRequester = sharedByRequester;
    }
}
