package com.smartspend.service;

import com.smartspend.model.Category;
import com.smartspend.model.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public double calculateTotalIncome(List<Transaction> transactions) {
        double total = 0.0;

        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                total = total + transaction.getAmount();
            }
        }

        return total;
    }

    public double calculateTotalExpense(List<Transaction> transactions) {
        double total = 0.0;

        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                total = total + transaction.getAmount();
            }
        }

        return total;
    }

    public Map<Category, Double> calculateCategoryExpenses(
            List<Transaction> transactions) {

        Map<Category, Double> categoryExpenses =
            new HashMap<Category, Double>();

        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                Category category = transaction.getCategory();

                double oldAmount = categoryExpenses.containsKey(category)
                    ? categoryExpenses.get(category) : 0.0;

                categoryExpenses.put(category, oldAmount + transaction.getAmount());
            }
        }

        return categoryExpenses;
    }

    public Category getHighestExpenseCategory(List<Transaction> transactions) {
        Map<Category, Double> categoryExpenses =
            calculateCategoryExpenses(transactions);

        Category highestCategory = null;
        double highestAmount = 0.0;

        for (Map.Entry<Category, Double> entry : categoryExpenses.entrySet()) {
            if (entry.getValue() > highestAmount) {
                highestAmount = entry.getValue();
                highestCategory = entry.getKey();
            }
        }

        return highestCategory;
    }

    public String generateReport(List<Transaction> transactions) {
        double totalIncome = calculateTotalIncome(transactions);
        double totalExpense = calculateTotalExpense(transactions);
        double balance = totalIncome - totalExpense;

        Map<Category, Double> categoryExpenses =
            calculateCategoryExpenses(transactions);

        Category highestCategory = getHighestExpenseCategory(transactions);

        String report = "\n========== SMARTSPEND MONTHLY REPORT ==========\n";
        report = report + String.format("Total Income : Rs. %.2f%n", totalIncome);
        report = report + String.format("Total Expense: Rs. %.2f%n", totalExpense);
        report = report + String.format("Balance      : Rs. %.2f%n", balance);
        report = report + "\nCategory-wise Expenses:\n";

        if (categoryExpenses.isEmpty()) {
            report = report + "No expense transaction is available.\n";
        } else {
            for (Map.Entry<Category, Double> entry : categoryExpenses.entrySet()) {
                report = report + String.format(
                    "- %-15s Rs. %.2f%n",
                    entry.getKey(),
                    entry.getValue()
                );
            }
        }

        if (highestCategory != null) {
            report = report + "\nHighest Expense Category: "
                + highestCategory
                + " (Rs. "
                + String.format("%.2f", categoryExpenses.get(highestCategory))
                + ")\n";
        }

        report = report + "===============================================\n";

        return report;
    }
}