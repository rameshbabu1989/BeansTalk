package com.lkrb.beanstalk;

import java.util.HashMap;

/**
 * Created by ramesh on 15/04/18.
 */

public class Customer {
    private String name;
    private String phone;
    private String email;
    private HashMap<String,Object> address;
    private HashMap<String,Object> card;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HashMap<String, Object> getAddress() {
        return address;
    }

    public void setAddress(HashMap<String, Object> address) {
        this.address = address;
    }

    public HashMap<String, Object> getCard() {
        return card;
    }

    public void setCard(HashMap<String, Object> card) {
        this.card = card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                ", card=" + card +
                '}';
    }
}
