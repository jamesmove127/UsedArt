package com.seener.usedarts.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CommodityTag extends RealmObject {


    @PrimaryKey
    private long CommodityTagId;

    @Required
    private String tagName;

    public CommodityTag() {
    }

    public CommodityTag(long commodityTagId, String tagName) {
        CommodityTagId = commodityTagId;
        this.tagName = tagName;
    }

    public long getCommodityTagId() {
        return CommodityTagId;
    }

    public void setCommodityTagId(long commodityTagId) {
        CommodityTagId = commodityTagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
