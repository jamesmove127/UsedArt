package com.seener.usedarts.model.realm;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Commodity extends RealmObject {

    @PrimaryKey
    private long commodityId;

    @Required
    private String dispalyName;

    private String subDisplayName;
    private RealmList<CommodityTag> commodityTagList;
    private RealmList<CommodityPicture> commodityPictureList;
    private boolean official;
    private boolean officialVerified;
    private ApproximateLocation approximateLocation;
    private CommodityPrice commodityPrice;
    private String ownerUserId;
    private RealmList<CommodityComment> commodityCommentList;
    private String infos;
    private boolean scheduled;
    private boolean paid;
    private Date addTime;

    public Commodity() {
    }

    public Commodity(long commodityId, String dispalyName, String subDisplayName, RealmList<CommodityTag> commodityTagList, RealmList<CommodityPicture> commodityPictureList, boolean official, boolean officialVerified, ApproximateLocation approximateLocation, CommodityPrice commodityPrice, String ownerUserId, RealmList<CommodityComment> commodityCommentList, String infos, boolean scheduled, boolean paid, Date addTime) {
        this.commodityId = commodityId;
        this.dispalyName = dispalyName;
        this.subDisplayName = subDisplayName;
        this.commodityTagList = commodityTagList;
        this.commodityPictureList = commodityPictureList;
        this.official = official;
        this.officialVerified = officialVerified;
        this.approximateLocation = approximateLocation;
        this.commodityPrice = commodityPrice;
        this.ownerUserId = ownerUserId;
        this.commodityCommentList = commodityCommentList;
        this.infos = infos;
        this.scheduled = scheduled;
        this.paid = paid;
        this.addTime = addTime;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public String getDispalyName() {
        return dispalyName;
    }

    public void setDispalyName(String dispalyName) {
        this.dispalyName = dispalyName;
    }

    public String getSubDisplayName() {
        return subDisplayName;
    }

    public void setSubDisplayName(String subDisplayName) {
        this.subDisplayName = subDisplayName;
    }

    public RealmList<CommodityTag> getCommodityTagList() {
        return commodityTagList;
    }

    public void setCommodityTagList(RealmList<CommodityTag> commodityTagList) {
        this.commodityTagList = commodityTagList;
    }

    public RealmList<CommodityPicture> getCommodityPictureList() {
        return commodityPictureList;
    }

    public void setCommodityPictureList(RealmList<CommodityPicture> commodityPictureList) {
        this.commodityPictureList = commodityPictureList;
    }

    public boolean isOfficial() {
        return official;
    }

    public void setOfficial(boolean official) {
        this.official = official;
    }

    public boolean isOfficialVerified() {
        return officialVerified;
    }

    public void setOfficialVerified(boolean officialVerified) {
        this.officialVerified = officialVerified;
    }

    public ApproximateLocation getApproximateLocation() {
        return approximateLocation;
    }

    public void setApproximateLocation(ApproximateLocation approximateLocation) {
        this.approximateLocation = approximateLocation;
    }

    public CommodityPrice getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(CommodityPrice commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public RealmList<CommodityComment> getCommodityCommentList() {
        return commodityCommentList;
    }

    public void setCommodityCommentList(RealmList<CommodityComment> commodityCommentList) {
        this.commodityCommentList = commodityCommentList;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
