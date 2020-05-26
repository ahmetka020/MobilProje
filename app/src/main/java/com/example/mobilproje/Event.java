package com.example.mobilproje;

import android.text.Editable;

public class Event {
    private Editable name;
    private Editable detail;
    private Editable startDate;
    private Editable endDate;
    private Editable address;

    public Event(Editable name, Editable detail, Editable startDate, Editable endDate, Editable address) {
        this.name = name;
        this.detail = detail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
    }

    public Event() {
    }

    public Editable getName() {
        return name;
    }

    public void setName(Editable name) {
        this.name = name;
    }

    public Editable getDetail() {
        return detail;
    }

    public void setDetail(Editable detail) {
        this.detail = detail;
    }

    public Editable getStartDate() {
        return startDate;
    }

    public void setStartDate(Editable startDate) {
        this.startDate = startDate;
    }

    public Editable getEndDate() {
        return endDate;
    }

    public void setEndDate(Editable endDate) {
        this.endDate = endDate;
    }

    public Editable getAddress() {
        return address;
    }

    public void setAddress(Editable address) {
        this.address = address;
    }
}
