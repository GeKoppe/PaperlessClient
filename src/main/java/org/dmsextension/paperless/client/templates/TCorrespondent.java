package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

/**
 * Represents a paperless correspondent
 */
public class TCorrespondent implements IDto {
    /**
     * Id
     */
    private Integer id;
    /**
     * Slugged name
     */
    private String slug;
    /**
     * Correspondents name
     */
    private String name;
    /**
     * Matching
     */
    private String match;
    /**
     * Matching algorithm
     */
    @Json(name="matching_algorithm")
    private Integer matchingAlgorithm;
    /**
     * Case sensitivity
     */
    @Json(name="is_insensitive")
    private boolean isInsensitive;
    @Json(name="document_count")
    private Integer documentCount;
    @Json(name="last_correspondence")
    private String lastCorrespondence;
    private Integer owner;
    private TPermissions permissions;
    @Json(name="user_can_change")
    private boolean userCanChange;

    public TCorrespondent() { }
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Integer getMatchingAlgorithm() {
        return matchingAlgorithm;
    }

    public void setMatchingAlgorithm(Integer matchingAlgorithm) {
        this.matchingAlgorithm = matchingAlgorithm;
    }

    public boolean isInsensitive() {
        return isInsensitive;
    }

    public void setInsensitive(boolean insensitive) {
        isInsensitive = insensitive;
    }

    public Integer getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(Integer documentCount) {
        this.documentCount = documentCount;
    }

    public String getLastCorrespondence() {
        return lastCorrespondence;
    }

    public void setLastCorrespondence(String lastCorrespondence) {
        this.lastCorrespondence = lastCorrespondence;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public TPermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(TPermissions permissions) {
        this.permissions = permissions;
    }

    public boolean isUserCanChange() {
        return userCanChange;
    }

    public void setUserCanChange(boolean userCanChange) {
        this.userCanChange = userCanChange;
    }
}
