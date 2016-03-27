package com.noqoush.adfalcon.adgallery.model;

public class ItemModel implements Comparable {

    private String key;
    private String title;
    private String description;
    private int resourceId;

    public ItemModel(String key, String title, String description, int resourceId) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItemModel) {
            ItemModel other = (ItemModel) o;
            if (other.getTitle().equalsIgnoreCase(getTitle()))
                return true;
        }

        return false;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ItemModel) {
            ItemModel other = (ItemModel) o;
            return other.getTitle().toLowerCase().compareTo(getTitle().toLowerCase());
        }

        return 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
