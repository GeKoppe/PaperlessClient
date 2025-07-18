package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

public class TStoragePath implements IDto {

    private Integer id;
    private String slug;
    private String name;
    private String path;
    private String match;
    @Json(name="matching_algorithm")
    private Integer matchingAlgorithm;
    @Json(name="is_insensitive")
    private boolean isInsensitive;
    private Integer owner;
    @Json(name="user_can_change")
    private boolean userCanChange;

    public TStoragePath() { }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public boolean isUserCanChange() {
        return userCanChange;
    }

    public void setUserCanChange(boolean userCanChange) {
        this.userCanChange = userCanChange;
    }
}
