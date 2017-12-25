package com.trams.joonggu_nubigo.objects;

/**
 * Created by zin9x on 11/13/2015.
 */
public class CommentObj {

    private int id;
    private String details;
    private int userId;
    private int grade;
    private int isDelete;
    private long createDate;
    private long updateDate;
    private String store;
    private String name;

    @Override
    public String toString() {
        return "CommentObj{" +
                "id=" + id +
                ", details='" + details + '\'' +
                ", userId=" + userId +
                ", grade=" + grade +
                ", isDelete=" + isDelete +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", store='" + store + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }


}
