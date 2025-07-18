package org.dmsextension.paperless.client.templates;

import java.util.List;

public class TGroup implements IDto {
    private Integer id;
    private String name;
    private List<String> permissions;
    public TGroup() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
