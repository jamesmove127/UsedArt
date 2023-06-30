package com.seener.usedarts.model.realm;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CommodityPicture extends RealmObject {

    @PrimaryKey
    private long CommodityPictureId;

    private long commodityId;

    private String url;

    public CommodityPicture() {
    }

    public CommodityPicture(long commodityPictureId, long commodityId, String url) {
        CommodityPictureId = commodityPictureId;
        this.commodityId = commodityId;
        this.url = url;
    }

    public long getCommodityPictureId() {
        return CommodityPictureId;
    }

    public void setCommodityPictureId(long commodityPictureId) {
        CommodityPictureId = commodityPictureId;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
