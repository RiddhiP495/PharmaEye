package com.example.pharmaeye.models;

import java.util.Date;

public class Prescription {

    private String id;
    private String drugName;
    private String quantity;
    private Date dueBy;

    public Prescription(String drugName, String quantity, Date dueBy) {
        this.drugName = drugName;
        this.quantity = quantity;
        this.dueBy = dueBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDueBy(Date dueBy) {
        this.dueBy = dueBy;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getQuantity() {
        return quantity;
    }

    public Date getDueBy() {
        return dueBy;
    }
}
