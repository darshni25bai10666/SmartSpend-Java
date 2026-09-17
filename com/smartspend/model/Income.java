package com.smartspend.model;

import java.time.LocalDate;

public class Income extends Transaction {
    public Income(int id, Category category, double amount,
                  String description, LocalDate transactionDate) {
        super(id, category, amount, description, transactionDate);
    }

    public String getType() {
        return "INCOME";
    }

    public double getSignedAmount() {
        return getAmount();
    }
}