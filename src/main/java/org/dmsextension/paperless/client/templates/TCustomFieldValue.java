package org.dmsextension.paperless.client.templates;

public class TCustomFieldValue implements IDto{
    private String value;
    private Integer field;

    public TCustomFieldValue () { }

    @Override
    public String toJsonString() {
        return null;
    }

    @Override
    public String toString() {
        return "TCustomFieldValue{" +
                "value='" + value + '\'' +
                ", field=" + field +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getField() {
        return field;
    }

    public void setField(Integer field) {
        this.field = field;
    }
}
