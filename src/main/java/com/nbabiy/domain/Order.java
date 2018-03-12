package com.nbabiy.domain;

import java.util.List;

public class Order {

    private int id;
    private int number;
    private List<Item> items;
    private int chat_ID;

    public Order() {
    }

    public Order(int number, List<Item> items, int chat_ID) {
        this.number = number;
        this.items = items;
        this.chat_ID = chat_ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getChat_ID() {
        return chat_ID;
    }

    public void setChat_ID(int chat_ID) {
        this.chat_ID = chat_ID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", number=" + number +
                ", items=" + items +
                ", chat_ID=" + chat_ID +
                '}';
    }
}
