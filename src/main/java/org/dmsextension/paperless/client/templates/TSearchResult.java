package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.List;

public class TSearchResult implements IDto {
    private Integer total;
    private List<TDocument> documents;
    @Json(name="custom_fields")
    private List<TCustomFieldTemplate> customFields;
    private List<TTag> tags;
    private List<TCorrespondent> correspondents;
    @Json(name="document_types")
    private List<TDocumentType> documentTypes;
    @Json(name="storage_paths")
    private List<TStoragePath> storagePaths;
    private List<TUser> users;
    private List<TWorkflow> workflows;
    @Json(name="saved_views")
    private List<TSavedView> savedViews;


    public TSearchResult() {

    }
    @Override
    public String toJsonString() {
        return null;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<TDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(List<TDocument> documents) {
        this.documents = documents;
    }

    public List<TCustomFieldTemplate> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<TCustomFieldTemplate> customFields) {
        this.customFields = customFields;
    }

    public List<TTag> getTags() {
        return tags;
    }

    public void setTags(List<TTag> tags) {
        this.tags = tags;
    }

    public List<TCorrespondent> getCorrespondents() {
        return correspondents;
    }

    public void setCorrespondents(List<TCorrespondent> correspondents) {
        this.correspondents = correspondents;
    }

    public List<TDocumentType> getDocumentTypes() {
        return documentTypes;
    }

    public void setDocumentTypes(List<TDocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

    public List<TStoragePath> getStoragePaths() {
        return storagePaths;
    }

    public void setStoragePaths(List<TStoragePath> storagePaths) {
        this.storagePaths = storagePaths;
    }

    public List<TUser> getUsers() {
        return users;
    }

    public void setUsers(List<TUser> users) {
        this.users = users;
    }

    public List<TWorkflow> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<TWorkflow> workflows) {
        this.workflows = workflows;
    }

    public List<TSavedView> getSavedViews() {
        return savedViews;
    }

    public void setSavedViews(List<TSavedView> savedViews) {
        this.savedViews = savedViews;
    }
}
