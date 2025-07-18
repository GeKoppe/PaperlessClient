package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.List;

public class TWorkflowTrigger implements IDto {

    private Integer id;
    private List<Integer> sources;
    private Integer type;
    @Json(name="filter_path")
    private String filterPath;
    @Json(name="filter_filename")
    private String filterFilename;
    @Json(name="filter_mail_rule")
    private Integer filterMailRule;
    @Json(name="matching_algorithm")
    private Integer matchingAlgorithm;
    private String match;
    @Json(name="is_insensitive")
    private boolean isInsensitive;
    @Json(name="filter_has_tags")
    private List<Integer> filterHasTags;
    @Json(name="filter_has_correspondents")
    private Integer filterHasCorrespondents;
    @Json(name="filter_has_document_type")
    private Integer filterHasDocumentType;
    @Json(name="schedule_offset_days")
    private long scheduleOffsetDays;
    @Json(name="schedule_is_recurring")
    private boolean scheduleIsRecurring;
    @Json(name="schedule_recurring_interval_days")
    private long scheduleRecurringIntervalDays;
    @Json(name="schedule_date_field")
    private String scheduleDateField;
    @Json(name="schedule_date_custom_field")
    private Integer scheduleDateCustomField;

    public TWorkflowTrigger() { }

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

    public List<Integer> getSources() {
        return sources;
    }

    public void setSources(List<Integer> sources) {
        this.sources = sources;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilterPath() {
        return filterPath;
    }

    public void setFilterPath(String filterPath) {
        this.filterPath = filterPath;
    }

    public String getFilterFilename() {
        return filterFilename;
    }

    public void setFilterFilename(String filterFilename) {
        this.filterFilename = filterFilename;
    }

    public Integer getFilterMailRule() {
        return filterMailRule;
    }

    public void setFilterMailRule(Integer filterMailRule) {
        this.filterMailRule = filterMailRule;
    }

    public Integer getMatchingAlgorithm() {
        return matchingAlgorithm;
    }

    public void setMatchingAlgorithm(Integer matchingAlgorithm) {
        this.matchingAlgorithm = matchingAlgorithm;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public boolean isInsensitive() {
        return isInsensitive;
    }

    public void setInsensitive(boolean insensitive) {
        isInsensitive = insensitive;
    }

    public List<Integer> getFilterHasTags() {
        return filterHasTags;
    }

    public void setFilterHasTags(List<Integer> filterHasTags) {
        this.filterHasTags = filterHasTags;
    }

    public Integer getFilterHasCorrespondents() {
        return filterHasCorrespondents;
    }

    public void setFilterHasCorrespondents(Integer filterHasCorrespondents) {
        this.filterHasCorrespondents = filterHasCorrespondents;
    }

    public Integer getFilterHasDocumentType() {
        return filterHasDocumentType;
    }

    public void setFilterHasDocumentType(Integer filterHasDocumentType) {
        this.filterHasDocumentType = filterHasDocumentType;
    }

    public long getScheduleOffsetDays() {
        return scheduleOffsetDays;
    }

    public void setScheduleOffsetDays(long scheduleOffsetDays) {
        this.scheduleOffsetDays = scheduleOffsetDays;
    }

    public boolean isScheduleIsRecurring() {
        return scheduleIsRecurring;
    }

    public void setScheduleIsRecurring(boolean scheduleIsRecurring) {
        this.scheduleIsRecurring = scheduleIsRecurring;
    }

    public long getScheduleRecurringIntervalDays() {
        return scheduleRecurringIntervalDays;
    }

    public void setScheduleRecurringIntervalDays(long scheduleRecurringIntervalDays) {
        this.scheduleRecurringIntervalDays = scheduleRecurringIntervalDays;
    }

    public String getScheduleDateField() {
        return scheduleDateField;
    }

    public void setScheduleDateField(String scheduleDateField) {
        this.scheduleDateField = scheduleDateField;
    }

    public Integer getScheduleDateCustomField() {
        return scheduleDateCustomField;
    }

    public void setScheduleDateCustomField(Integer scheduleDateCustomField) {
        this.scheduleDateCustomField = scheduleDateCustomField;
    }
}
