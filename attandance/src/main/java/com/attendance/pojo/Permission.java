package com.attendance.pojo;

import java.io.Serializable;

public class Permission implements Serializable {
    private static final long serialVersionUID = 7273261439163054906L;
    private Integer pid;
    private String pgroup;
    private String rights;

    public Permission() {
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPgroup() {
        return pgroup;
    }

    public void setPgroup(String pgroup) {
        this.pgroup = pgroup;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }
}
