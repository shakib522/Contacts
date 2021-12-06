package com.example.contacts;

import java.io.Serializable;

public class ContactModel implements Serializable,Comparable<ContactModel> {

    String name;
    String number;
    int id;

    public ContactModel(String name, String number, int id) {
        this.name = name;
        this.number = number;
        this.id = id;
    }

    public ContactModel(String name, String number) {
        this.name = name;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public  String getText1() {
        return String.valueOf(name.charAt(0));
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(ContactModel otherContactModel) {
        return this.name.compareTo(otherContactModel.name);
    }
}
