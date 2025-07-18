package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.List;

public class TSavedView implements IDto {

    private Integer id;
    private String name;
    @Json(name="show_on_dashboard")
    private boolean showOnDashboard;
    @Json(name="show_in_sidebar")
    private boolean showInSidebar;
    @Json(name="sort_field")
    private String sortField;
    @Json(name="sort_reverse")
    private boolean sortReverse;
    @Json(name="filter_rules")
    private List<TFilterRule> filterRules;
    @Json(name="page_size")
    private long pageSize;
    @Json(name="display_mode")
    private String displayMode;
    @Json(name="display_fields")
    private String displayFields;
    private Integer owner;
    @Json(name="user_can_change")
    private boolean userCanChange;


    public TSavedView() {
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

    public boolean isShowOnDashboard() {
        return showOnDashboard;
    }

    public void setShowOnDashboard(boolean showOnDashboard) {
        this.showOnDashboard = showOnDashboard;
    }

    public boolean isShowInSidebar() {
        return showInSidebar;
    }

    public void setShowInSidebar(boolean showInSidebar) {
        this.showInSidebar = showInSidebar;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isSortReverse() {
        return sortReverse;
    }

    public void setSortReverse(boolean sortReverse) {
        this.sortReverse = sortReverse;
    }

    public List<TFilterRule> getFilterRules() {
        return filterRules;
    }

    public void setFilterRules(List<TFilterRule> filterRules) {
        this.filterRules = filterRules;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getDisplayFields() {
        return displayFields;
    }

    public void setDisplayFields(String displayFields) {
        this.displayFields = displayFields;
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

    public static class TFilterRule implements IDto {
        @Json(name="rule_type")
        private Integer ruleType;
        private String value;

        public TFilterRule() { }

        /**
         * {@inheritDoc}
         * TODO
         * @return
         */
        @Override
        public String toJsonString() {
            return null;
        }

        public Integer getRuleType() {
            return ruleType;
        }

        public void setRuleType(Integer ruleType) {
            this.ruleType = ruleType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
