package com.nbabiy.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Item {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private float price;
    private boolean avaliable;
    private String photo;

    public Item() {
    }

    public Item(String name, float price, boolean avaliable, String photo) {
        this.name = name;
        this.price = price;
        this.avaliable = avaliable;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", avaliable=" + avaliable +
                ", photo='" + photo + '\'' +
                '}';
    }
}
