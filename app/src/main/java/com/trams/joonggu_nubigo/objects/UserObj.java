package com.trams.joonggu_nubigo.objects;

import java.util.Date;

/**
 * Created by ADMIN on 11/5/2015.
 */
public class UserObj {
    private long id;
    /**
     * Not-null value.
     */
    private String username;
    /**
     * Not-null value.
     */
    private String password;
    private String nickname;
    private String fullname;
    private int role;
    private String sex;
    private String phone;
    private String email;
    private Integer age;
    /**
     * Not-null value.
     */
    private Date createDate;
    /**
     * Not-null value.
     */
    private Date updateDate;

    public UserObj() {

    }

    public UserObj(long id, String username, String password, String nickname, String fullname, int role, String sex, String phone, String email, Integer age, Date createDate, Date updateDate) {
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
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    @Override
    public String toString() {
        return "UserObj{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role=" + role +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


}
