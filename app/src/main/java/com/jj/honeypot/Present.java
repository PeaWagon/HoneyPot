package com.jj.honeypot;

import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class Present implements Serializable {

    private Date created;
    private Date updated;

    public String name;
    public double price;
    public String store;
    public String image;

    public Present(String name, double price, String store, String image) {
        this.created = new Date();
        this.updated = new Date();

        this.name = name;
        this.price = price;
        this.store = store;
        this.image = image;
    }

}
