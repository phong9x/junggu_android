package com.trams.joonggu_nubigo.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "VERSION".
 */
public class Version {

    private long id;
    /** Not-null value. */
    private String name;
    /** Not-null value. */
    private String status;
    private String etc;
    /** Not-null value. */
    private java.util.Date createDate;
    /** Not-null value. */
    private java.util.Date updateDate;

    public Version() {
    }

    public Version(long id) {
        this.id = id;
    }

    public Version(long id, String name, String status, String etc, java.util.Date createDate, java.util.Date updateDate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.etc = etc;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(String name) {
        this.name = name;
    }

    /** Not-null value. */
    public String getStatus() {
        return status;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    /** Not-null value. */
    public java.util.Date getCreateDate() {
        return createDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreateDate(java.util.Date createDate) {
        this.createDate = createDate;
    }

    /** Not-null value. */
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

}