package com.jj.honeypot;

import java.io.Serializable;
import java.util.Date;

public class Present implements Serializable {

    private Date created;
    private Date updated;

    public String name;
    public double price;
    public String store;
    public String picture;

    public Present(String name, double price, String store, String picture) {
        this.created = new Date();
        this.updated = new Date();

        this.name = name;
        this.price = price;
        this.store = store;
        this.picture = picture;
    }

    /*private void writeObject(java.io.ObjectOutputStream out) {
        throws IOException;
    }

    private void readObject(java.io.ObjectInputStream in) {
        throws IOException, ClassNotFoundException;
    }

    private void readObjectNoData() {
        throws ObjectStreamException;
    }*/
}
