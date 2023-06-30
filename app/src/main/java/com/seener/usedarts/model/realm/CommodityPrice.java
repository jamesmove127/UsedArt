package com.seener.usedarts.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CommodityPrice extends RealmObject {

    private String currency;
    private double amount;

    public CommodityPrice(String currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public CommodityPrice() {
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
