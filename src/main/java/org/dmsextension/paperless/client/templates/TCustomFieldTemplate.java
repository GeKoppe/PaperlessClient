package org.dmsextension.paperless.client.templates;


import com.squareup.moshi.Json;

public class TCustomFieldTemplate implements IDto {
    private Integer id;
    private String name;
    @Json(name="data_type")
    private String dataType;
    @Json(name="extra_data")
    private TExtraData extraData;
    @Json(name="document_count")
    private Integer documentCount;

    public TCustomFieldTemplate() { }

    @Override
    public String toJsonString() {
        return String.format("""
                {
                "id"=%s,
                "name"="%s",
                "data_type"="%s",
                "extra_data"="%s",
                "document_count"="%s"
                }
                """,
                this.id,
                this.name,
                this.dataType,
                this.extraData,
                this.documentCount
        );
    }
    @Override
    public String toString() {
        return "TCustomFieldTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dataType='" + dataType + '\'' +
                ", extraData='" + extraData.toString() + '\'' +
                ", documentCount=" + documentCount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public TExtraData getExtraData() {
        return extraData;
    }

    public void setExtraData(TExtraData extraData) {
        this.extraData = extraData;
    }

    public Integer getDocumentCount() {
        return documentCount;
    }

    public void setDocumentCount(Integer documentCount) {
        this.documentCount = documentCount;
    }
}
