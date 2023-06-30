package com.seener.usedarts.model.realm;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CommodityGroup extends RealmObject {

    @PrimaryKey
    private long commodityGroupId;

    @Required
    private String useId;

    private RealmList<Long> commodityIdList;

    public CommodityGroup() {
    }

    public CommodityGroup(long commodityGroupId, String useId, RealmList<Long> commodityIdList) {
        this.commodityGroupId = commodityGroupId;
        this.useId = useId;
        this.commodityIdList = commodityIdList;
    }

    public long getCommodityGroupId() {
        return commodityGroupId;
    }

    public void setCommodityGroupId(long commodityGroupId) {
        this.commodityGroupId = commodityGroupId;
    }

    public String getUseId() {
        return useId;
    }

    public void setUseId(String useId) {
        this.useId = useId;
    }

    public RealmList<Long> getCommodityIdList() {
        return commodityIdList;
    }

    public void setCommodityIdList(RealmList<Long> commodityIdList) {
        this.commodityIdList = commodityIdList;
    }
}
