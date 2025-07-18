package org.dmsextension.paperless.client.templates;

import com.squareup.moshi.Json;

public class TMailAccount implements IDto {
    private Integer id;
    private String name;
    @Json(name="imap_server")
    private String imapServer;
    @Json(name="imap_port")
    private long imapPort;
    @Json(name="imap_security")
    private Integer imapSecurity;
    private String username;
    private String password;
    @Json(name="character_set")
    private String characterSet;
    @Json(name="is_token")
    private boolean isToken;
    private Integer owner;
    @Json(name="user_can_change")
    private boolean userCanChange;
    @Json(name="account_type")
    private Integer accountType;
    private String expiration;
    @Json(name="set_permissions")
    private TPermissions setPermissions;

    public TMailAccount() { }

    /**
     * TODO
     * @return
     */
    @Override
    public String toJsonString() {
        return null;
    }

    public TPermissions getSetPermissions() {
        return setPermissions;
    }

    public void setSetPermissions(TPermissions setPermissions) {
        this.setPermissions = setPermissions;
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

    public String getImapServer() {
        return imapServer;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public long getImapPort() {
        return imapPort;
    }

    public void setImapPort(long imapPort) {
        this.imapPort = imapPort;
    }

    public Integer getImapSecurity() {
        return imapSecurity;
    }

    public void setImapSecurity(Integer imapSecurity) {
        this.imapSecurity = imapSecurity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public boolean isToken() {
        return isToken;
    }

    public void setToken(boolean token) {
        isToken = token;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
