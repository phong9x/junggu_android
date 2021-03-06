package com.trams.joonggu_nubigo.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USER".
 */
public class User {

    private long id;
    /** Not-null value. */
    private String username;
    /** Not-null value. */
    private String password;
    private String nickname;
    private String fullname;
    private int role;
    private String sex;
    private String phone;
    private String email;
    private Integer age;
    private String scrap;
    /** Not-null value. */
    private java.util.Date createDate;
    /** Not-null value. */
    private java.util.Date updateDate;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String username, String password, String nickname, String fullname, int role, String sex, String phone, String email, Integer age, String scrap, java.util.Date createDate, java.util.Date updateDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.fullname = fullname;
        this.role = role;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.scrap = scrap;
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
    public String getUsername() {
        return username;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Not-null value. */
    public String getPassword() {
        return password;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getScrap() {
        return scrap;
    }

    public void setScrap(String scrap) {
        this.scrap = scrap;
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
