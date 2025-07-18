package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

public class TTag implements IDto {

    private Integer id;
    private String slug;
    private String name;
    private String color;
    @Json(name="text_color")
    private String textColor;
    private String match;
    @Json(name="document_count")
    private Integer documentCount;

    public TTag() { }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public String toString() {
        return "TTag{" +
                "id=" + id +
                ", slug='" + slug + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", textColor='" + textColor + '\'' +
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
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
