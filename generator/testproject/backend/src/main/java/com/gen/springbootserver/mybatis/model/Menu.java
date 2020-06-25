package com.gen.springbootserver.mybatis.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Menu implements Serializable {
    private Long id;

    private Integer sort;

    private String name;

    private String title;

    private String icon;

    private String link;

    private Boolean isOuterLink;

    private String permission;

    private Boolean hidden;

    private LocalDateTime createtime;

    private String type;

    private Boolean isDefault;

    private String parentname;

    private Boolean isHome;

    private Boolean isGroup;

    private Boolean isDelete;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsOuterLink() {
        return isOuterLink;
    }

    public void setIsOuterLink(Boolean isOuterLink) {
        this.isOuterLink = isOuterLink;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }

    public Boolean getIsHome() {
        return isHome;
    }

    public void setIsHome(Boolean isHome) {
        this.isHome = isHome;
    }

    public Boolean getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sort=").append(sort);
        sb.append(", name=").append(name);
        sb.append(", title=").append(title);
        sb.append(", icon=").append(icon);
        sb.append(", link=").append(link);
        sb.append(", isOuterLink=").append(isOuterLink);
        sb.append(", permission=").append(permission);
        sb.append(", hidden=").append(hidden);
        sb.append(", createtime=").append(createtime);
        sb.append(", type=").append(type);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", parentname=").append(parentname);
        sb.append(", isHome=").append(isHome);
        sb.append(", isGroup=").append(isGroup);
        sb.append(", isDelete=").append(isDelete);
        sb.append("]");
        return sb.toString();
    }
}