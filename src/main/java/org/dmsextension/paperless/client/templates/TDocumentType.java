package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

public class TDocumentType {
    private Integer id;
    private String slug;
    private String name;
    private String match;
    @Json(name="document_count")
    private Integer documentCount;

    public TDocumentType() { }

    @Override
    public String toString() {
        return "TDocumentType{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", match='" + match + '\'' +
                ", documentCount=" + documentCount +
                '}';
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

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public Integer getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(Integer documentCount) {
        this.documentCount = documentCount;
    }
}
