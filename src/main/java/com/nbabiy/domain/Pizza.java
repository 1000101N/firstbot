package com.nbabiy.domain;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "getAllPizza", query = "select p from  Pizza p")
})
public class Pizza extends Item{

    private String description;
    private float price;



    public Pizza() {
    }

    public Pizza(String name, String description, float price, boolean avaliable, String photo) {
        this.setName(name);
        this.description = description;
        this.setPrice(price);
        this.setAvaliable(avaliable);
        this.setPhoto(photo);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public float getPrice() {
        return price;
    }

    @Override
    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "description='" + description + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}
