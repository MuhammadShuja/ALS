package com.alllinkshare.catalog.models;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    private int id;

    @SerializedName("parent_id")
    private int parentId;

    @SerializedName("name")
    private String name;

    @SerializedName("name_korean")
    private String nameKorean;

    @SerializedName("icon")
    private String icon;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("description")
    private String description;

    @SerializedName("description_korean")
    private String descriptionKorean;

    @SerializedName("has_children")
    private boolean hasChildren;

    @SerializedName("children_count")
    private int childCount;

    @SerializedName("action")
    private Action action;

    public Category(int id, int parentId, String name, String nameKorean, String icon, String thumbnail, String description, String descriptionKorean, boolean hasChildren, int childCount, Action action) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.nameKorean = nameKorean;
        this.icon = icon;
        this.thumbnail = thumbnail;
        this.description = description;
        this.descriptionKorean = descriptionKorean;
        this.hasChildren = hasChildren;
        this.childCount = childCount;
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKorean() {
        return nameKorean;
    }

    public void setNameKorean(String nameKorean) {
        this.nameKorean = nameKorean;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionKorean() {
        return descriptionKorean;
    }

    public void setDescriptionKorean(String descriptionKorean) {
        this.descriptionKorean = descriptionKorean;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public static class Action{
        @SerializedName("type")
        private String type;

        @SerializedName("handle")
        private String handle;

        public Action(String type, String handle) {
            this.type = type;
            this.handle = handle;
        }

        public String getType() {
            return type;
        }

        public String getHandle() {
            return handle;
        }
    }
}