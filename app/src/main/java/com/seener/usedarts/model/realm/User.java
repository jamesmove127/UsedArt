package com.seener.usedarts.model.realm;


import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class User extends RealmObject {

    @PrimaryKey
    private String userId;
    @Required
    private String dispalyName;
    private String email;
    private String phoneNumber;
    private ApproximateLocation approximateLocation;
    private RealmList<CommodityGroup> commodityGroup;
    private Date signUpDate;


    public User() {
    }

}
