package com.smartspend.model;

import java.time.LocalDate;

public abstract class Transaction {
    private int id;
    private Category category;
    private double amount;
    private String description;
    private LocalDate transactionDate;

    public Transaction(int id, Category category, double amount,
                       String description, LocalDate transactionDate) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public abstract String getType();

    public abstract double getSignedAmount();

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String toString() {
        return String.format(
            "ID: %d | %-7s | %-13s | Rs. %.2f | %s | %s",
            id, getType(), category, amount, transactionDate, description
        );
    }
}