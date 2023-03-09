package fr.polytech.entities;

import fr.polytech.entities.item.Item;
import fr.polytech.entities.structure.Store;

import javax.persistence.*;
import java.util.Set;

public class Payment {
    private long id;
    private Customer customer;
    private Store store;
    private Set<Item> shoppingList;
    private boolean isSettled;

    public boolean isSettled() {
        return isSettled;
    }

    private float price;



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<Item> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Set<Item> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
