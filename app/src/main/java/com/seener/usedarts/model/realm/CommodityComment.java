package com.seener.usedarts.model.realm;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CommodityComment extends RealmObject {

    @PrimaryKey
    private long commentId;
    private long commodityId;
    private long userId;
    private String displayName;
    private String comment;
    private Date addDate;
    private Date editDate;

    public CommodityComment() {
    }

    public CommodityComment(long commentId, long commodityId, long userId, String displayName, String comment, Date addDate, Date editDate) {
        this.commentId = commentId;
        this.commodityId = commodityId;
        this.userId = userId;
        this.displayName = displayName;
        this.comment = comment;
        this.addDate = addDate;
        this.editDate = editDate;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }
}
