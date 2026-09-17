package com.smartspend.model;

public class Budget {
    private Category category;
    private double monthlyLimit;

    public Budget(Category category, double monthlyLimit) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Category getCategory() {
        return category;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public String toString() {
        return category + " budget: Rs. "
            + String.format("%.2f", monthlyLimit);
    }
}