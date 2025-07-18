package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

import java.util.List;

public class TUser implements IDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    @Json(name="first_name")
    private String firstName;
    @Json(name="last_name")
    private String lastName;
    @Json(name="date_joined")
    private String dateJoined;
    @Json(name="is_staff")
    private boolean isStaff;
    @Json(name="is_active")
    private boolean isActice;
    @Json(name="is_supervisor")
    private boolean isSupervisor;
    private List<Integer> groups;
    @Json(name="user_permissions")
    private List<String> userPermissions;
    @Json(name="inherited_permissions")
    private List<String> inheritedPermissions;
    @Json(name="is_mfa_enabled")
    private boolean isMfaEnabled;

    public TUser() { }
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public boolean isActice() {
        return isActice;
    }

    public void setActice(boolean actice) {
        isActice = actice;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }

    public void setSupervisor(boolean supervisor) {
        isSupervisor = supervisor;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public List<String> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(List<String> userPermissions) {
        this.userPermissions = userPermissions;
    }

    public List<String> getInheritedPermissions() {
        return inheritedPermissions;
    }

    public void setInheritedPermissions(List<String> inheritedPermissions) {
        this.inheritedPermissions = inheritedPermissions;
    }

    public boolean isMfaEnabled() {
        return isMfaEnabled;
    }

    public void setMfaEnabled(boolean mfaEnabled) {
        isMfaEnabled = mfaEnabled;
    }
}
