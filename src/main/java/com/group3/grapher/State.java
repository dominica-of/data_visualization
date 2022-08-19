package com.group3.grapher;

public class State {
    private Boolean editted;
    private Boolean loadedFromDB;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private String tableName;

    public Boolean getEditted() {
        return editted;
    }

    public void setEditted(Boolean editted) {
        this.editted = editted;
    }

    public Boolean getLoadedFromDB() {
        return loadedFromDB;
    }

    public void setLoadedFromDB(Boolean loadedFromDB) {
        this.loadedFromDB = loadedFromDB;
    }
}
