package org.dmsextension.paperless.client.templates;

import java.util.List;

public class TPermissions implements IDto {
    public static class Perm {
        private List<Integer> users;
        private List<Integer> groups;

        public Perm() { }

        public List<Integer> getUsers() {
            return users;
        }

        public void setUsers(List<Integer> users) {
            this.users = users;
        }

        public List<Integer> getGroups() {
            return groups;
        }

        public void setGroups(List<Integer> groups) {
            this.groups = groups;
        }
    }

    private List<Perm> view;
    private List<Perm> change;

    public TPermissions() {

    }

    @Override
    public String toJsonString() {
        return null;
    }

    public List<Perm> getView() {
        return view;
    }

    public void setView(List<Perm> view) {
        this.view = view;
    }

    public List<Perm> getChange() {
        return change;
    }

    public void setChange(List<Perm> change) {
        this.change = change;
    }
}
