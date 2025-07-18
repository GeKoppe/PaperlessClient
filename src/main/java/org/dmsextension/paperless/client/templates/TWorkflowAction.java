package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TWorkflowAction implements IDto {

    private Integer type;
    private Integer id;
    @Json(name="assign_title")
    private String assignTitle;
    @Json(name="assign_tags")
    private List<Integer> assignTags;
    @Json(name="assign_correspondent")
    private Integer assignCorrespondent;
    @Json(name="assign_document_type")
    private Integer assignDocumentType;
    @Json(name="assign_storage_path")
    private Integer assignStoragePath;
    @Json(name="assign_owner")
    private Integer assignOwner;
    @Json(name="assign_view_users")
    private List<Integer> assignViewUsers;
    @Json(name="assign_change_users")
    private List<Integer> assignChangeUsers;
    @Json(name="assign_view_groups")
    private List<Integer> assignViewGroups;
    @Json(name="assign_change_groups")
    private List<Integer> assignChangeGroups;
    @Json(name="assign_custom_fields_values")
    private Map<String, String> assignCustomFieldsValues;
    @Json(name="remove_all_tags")
    private boolean removeAllTags;
    @Json(name="assign_custom_fields")
    private List<Integer> assignCustomFields;
    @Json(name="remove_tags")
    private List<Integer> removeTags;
    @Json(name="remove_all_correspondents")
    private boolean removeAllCorrespondents;
    @Json(name="remove_correspondents")
    private List<Integer> removeCorrespondents;
    @Json(name="remove_all_document_types")
    private boolean removeAllDocumentTypes;
    @Json(name="remove_document_types")
    private List<Integer> removeDocumentTypes;
    @Json(name="remove_all_storage_paths")
    private boolean removeAllStoragePaths;
    @Json(name="remove_storage_paths")
    private List<Integer> removeStoragePaths;
    @Json(name="remove_all_custom_fields")
    private boolean removeAllCustomFields;
    @Json(name="remove_custom_fields")
    private List<Integer> removeCustomFields;
    @Json(name="remove_all_owners")
    private boolean removeAllOwners;
    @Json(name="remove_owners")
    private List<Integer> removeOwners;
    @Json(name="remove_all_permissions")
    private boolean removeAllPermissions;
    @Json(name="remove_view_users")
    private List<Integer> removeViewUsers;
    @Json(name="remove_view_groups")
    private List<Integer> removeViewGroups;
    @Json(name="remove_change_users")
    private List<Integer> removeChangeUsers;
    @Json(name="remove_change_groups")
    private List<Integer> removeChangeGroups;
    private TEmail email;
    private TWebhook webhook;

    public TWorkflowAction() {
    }

    @Override
    public String toJsonString() {
        return null;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssignTitle() {
        return assignTitle;
    }

    public void setAssignTitle(String assignTitle) {
        this.assignTitle = assignTitle;
    }

    public List<Integer> getAssignTags() {
        return assignTags;
    }

    public void setAssignTags(List<Integer> assignTags) {
        this.assignTags = assignTags;
    }

    public Integer getAssignCorrespondent() {
        return assignCorrespondent;
    }

    public void setAssignCorrespondent(Integer assignCorrespondent) {
        this.assignCorrespondent = assignCorrespondent;
    }

    public Integer getAssignDocumentType() {
        return assignDocumentType;
    }

    public void setAssignDocumentType(Integer assignDocumentType) {
        this.assignDocumentType = assignDocumentType;
    }

    public Integer getAssignStoragePath() {
        return assignStoragePath;
    }

    public void setAssignStoragePath(Integer assignStoragePath) {
        this.assignStoragePath = assignStoragePath;
    }

    public Integer getAssignOwner() {
        return assignOwner;
    }

    public void setAssignOwner(Integer assignOwner) {
        this.assignOwner = assignOwner;
    }

    public List<Integer> getAssignViewUsers() {
        return assignViewUsers;
    }

    public void setAssignViewUsers(List<Integer> assignViewUsers) {
        this.assignViewUsers = assignViewUsers;
    }

    public List<Integer> getAssignChangeUsers() {
        return assignChangeUsers;
    }

    public void setAssignChangeUsers(List<Integer> assignChangeUsers) {
        this.assignChangeUsers = assignChangeUsers;
    }

    public List<Integer> getAssignViewGroups() {
        return assignViewGroups;
    }

    public void setAssignViewGroups(List<Integer> assignViewGroups) {
        this.assignViewGroups = assignViewGroups;
    }

    public List<Integer> getAssignChangeGroups() {
        return assignChangeGroups;
    }

    public void setAssignChangeGroups(List<Integer> assignChangeGroups) {
        this.assignChangeGroups = assignChangeGroups;
    }

    public Map<String, String> getAssignCustomFieldsValues() {
        return assignCustomFieldsValues;
    }

    public void setAssignCustomFieldsValues(HashMap<String, String> assignCustomFieldsValues) {
        this.assignCustomFieldsValues = assignCustomFieldsValues;
    }

    public boolean isRemoveAllTags() {
        return removeAllTags;
    }

    public void setRemoveAllTags(boolean removeAllTags) {
        this.removeAllTags = removeAllTags;
    }

    public List<Integer> getAssignCustomFields() {
        return assignCustomFields;
    }

    public void setAssignCustomFields(List<Integer> assignCustomFields) {
        this.assignCustomFields = assignCustomFields;
    }

    public List<Integer> getRemoveTags() {
        return removeTags;
    }

    public void setRemoveTags(List<Integer> removeTags) {
        this.removeTags = removeTags;
    }

    public boolean isRemoveAllCorrespondents() {
        return removeAllCorrespondents;
    }

    public void setRemoveAllCorrespondents(boolean removeAllCorrespondents) {
        this.removeAllCorrespondents = removeAllCorrespondents;
    }

    public List<Integer> getRemoveCorrespondents() {
        return removeCorrespondents;
    }

    public void setRemoveCorrespondents(List<Integer> removeCorrespondents) {
        this.removeCorrespondents = removeCorrespondents;
    }

    public boolean isRemoveAllDocumentTypes() {
        return removeAllDocumentTypes;
    }

    public void setRemoveAllDocumentTypes(boolean removeAllDocumentTypes) {
        this.removeAllDocumentTypes = removeAllDocumentTypes;
    }

    public List<Integer> getRemoveDocumentTypes() {
        return removeDocumentTypes;
    }

    public void setRemoveDocumentTypes(List<Integer> removeDocumentTypes) {
        this.removeDocumentTypes = removeDocumentTypes;
    }

    public boolean isRemoveAllStoragePaths() {
        return removeAllStoragePaths;
    }

    public void setRemoveAllStoragePaths(boolean removeAllStoragePaths) {
        this.removeAllStoragePaths = removeAllStoragePaths;
    }

    public List<Integer> getRemoveStoragePaths() {
        return removeStoragePaths;
    }

    public void setRemoveStoragePaths(List<Integer> removeStoragePaths) {
        this.removeStoragePaths = removeStoragePaths;
    }

    public boolean isRemoveAllCustomFields() {
        return removeAllCustomFields;
    }

    public void setRemoveAllCustomFields(boolean removeAllCustomFields) {
        this.removeAllCustomFields = removeAllCustomFields;
    }

    public List<Integer> getRemoveCustomFields() {
        return removeCustomFields;
    }

    public void setRemoveCustomFields(List<Integer> removeCustomFields) {
        this.removeCustomFields = removeCustomFields;
    }

    public boolean isRemoveAllOwners() {
        return removeAllOwners;
    }

    public void setRemoveAllOwners(boolean removeAllOwners) {
        this.removeAllOwners = removeAllOwners;
    }

    public List<Integer> getRemoveOwners() {
        return removeOwners;
    }

    public void setRemoveOwners(List<Integer> removeOwners) {
        this.removeOwners = removeOwners;
    }

    public boolean isRemoveAllPermissions() {
        return removeAllPermissions;
    }

    public void setRemoveAllPermissions(boolean removeAllPermissions) {
        this.removeAllPermissions = removeAllPermissions;
    }

    public List<Integer> getRemoveViewUsers() {
        return removeViewUsers;
    }

    public void setRemoveViewUsers(List<Integer> removeViewUsers) {
        this.removeViewUsers = removeViewUsers;
    }

    public List<Integer> getRemoveViewGroups() {
        return removeViewGroups;
    }

    public void setRemoveViewGroups(List<Integer> removeViewGroups) {
        this.removeViewGroups = removeViewGroups;
    }

    public List<Integer> getRemoveChangeUsers() {
        return removeChangeUsers;
    }

    public void setRemoveChangeUsers(List<Integer> removeChangeUsers) {
        this.removeChangeUsers = removeChangeUsers;
    }

    public List<Integer> getRemoveChangeGroups() {
        return removeChangeGroups;
    }

    public void setRemoveChangeGroups(List<Integer> removeChangeGroups) {
        this.removeChangeGroups = removeChangeGroups;
    }

    public TEmail getEmail() {
        return email;
    }

    public void setEmail(TEmail email) {
        this.email = email;
    }

    public TWebhook getWebhook() {
        return webhook;
    }

    public void setWebhook(TWebhook webhook) {
        this.webhook = webhook;
    }

    public static class TEmail implements IDto {
        private Integer id;
        private String subject;
        private String body;
        private String to;
        @Json(name="include_document")
        private boolean includeDocument;

        public TEmail() {

        }

        @Override
        public String toJsonString() {
            return null;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public boolean isIncludeDocument() {
            return includeDocument;
        }

        public void setIncludeDocument(boolean includeDocument) {
            this.includeDocument = includeDocument;
        }
    }

    public static class TWebhook implements IDto {
        private Integer id;
        private String url;
        @Json(name="use_params")
        private boolean useParams;
        @Json(name="as_json")
        private boolean asJson;
        private String params;
        private String body;
        private String header;
        @Json(name="include_document")
        private String includeDocument;

        public TWebhook() { }
        @Override
        public String toJsonString() {
            return null;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUseParams() {
            return useParams;
        }

        public void setUseParams(boolean useParams) {
            this.useParams = useParams;
        }

        public boolean isAsJson() {
            return asJson;
        }

        public void setAsJson(boolean asJson) {
            this.asJson = asJson;
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getIncludeDocument() {
            return includeDocument;
        }

        public void setIncludeDocument(String includeDocument) {
            this.includeDocument = includeDocument;
        }
    }
}
