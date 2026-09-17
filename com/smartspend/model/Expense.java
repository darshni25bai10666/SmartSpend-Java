package com.smartspend.model;

import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(int id, Category category, double amount,
                   String description, LocalDate transactionDate) {
        super(id, category, amount, description, transactionDate);
    }

    public String getType() {
        return "EXPENSE";
    }

    public double getSignedAmount() {
        return -getAmount();
    }
}