package org.dmsextension.paperless.client.templates;

import java.util.List;

public class TWorkflow implements IDto {
    private Integer id;
    private String name;
    private Integer order;
    private boolean enabled;
    private List<TWorkflowTrigger> triggers;
    private List<TWorkflowAction> actions;

    public TWorkflow() {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<TWorkflowTrigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<TWorkflowTrigger> triggers) {
        this.triggers = triggers;
    }

    public List<TWorkflowAction> getActions() {
        return actions;
    }

    public void setActions(List<TWorkflowAction> actions) {
        this.actions = actions;
    }
}
