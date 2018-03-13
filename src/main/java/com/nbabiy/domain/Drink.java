package com.nbabiy.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "geAllDrink", query = "SELECT d from Drink d")
})
public class Drink extends Item {

    private float volume;

    public Drink() {
    }

    public Drink(String name, float price, boolean avaliable, String photo, float volume) {
        super(name, price, avaliable, photo);
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "volume=" + volume +
                "} " + super.toString();
    }
}
