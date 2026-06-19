package com.example.demo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
public class OrderSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int tableNo;
    private boolean isSplitBilling;
    private String currentFoodStatus; // RECEIVED, PREPARING, READY, SERVED

    public OrderSession() {}

    public OrderSession(int tableNo) {
        this.tableNo = tableNo;
        this.isSplitBilling = false;
        this.currentFoodStatus = "RECEIVED";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getTableNo() { return tableNo; }
    public void setTableNo(int tableNo) { this.tableNo = tableNo; }

    public boolean isSplitBilling() { return isSplitBilling; }
    public void setSplitBilling(boolean splitBilling) { this.isSplitBilling = splitBilling; }

    public String getCurrentFoodStatus() { return currentFoodStatus; }
    public void setCurrentFoodStatus(String currentFoodStatus) { this.currentFoodStatus = currentFoodStatus; }
}