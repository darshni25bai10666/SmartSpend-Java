package com.smartspend.service;

import com.smartspend.exception.InvalidAmountException;
import com.smartspend.model.Budget;
import com.smartspend.model.Category;
import com.smartspend.model.Transaction;
import com.smartspend.repository.TransactionRepository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetService {
    private TransactionRepository repository;

    public BudgetService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void setBudget(Category category, double limit)
            throws InvalidAmountException, SQLException {

        if (limit <= 0) {
            throw new InvalidAmountException(
                "Budget limit must be greater than zero."
            );
        }

        repository.saveOrUpdateBudget(new Budget(category, limit));
    }

    public List<Budget> getAllBudgets() throws SQLException {
        return repository.getAllBudgets();
    }

    public Map<Category, Double> calculateExpenseByCategory(
            List<Transaction> transactions) {

        Map<Category, Double> expenses = new HashMap<Category, Double>();

        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                Category category = transaction.getCategory();
                double oldAmount = expenses.containsKey(category)
                    ? expenses.get(category) : 0.0;

                expenses.put(category, oldAmount + transaction.getAmount());
            }
        }

        return expenses;
    }

    public void showBudgetStatus(List<Transaction> transactions)
            throws SQLException {

        List<Budget> budgets = getAllBudgets();
        Map<Category, Double> expenses = calculateExpenseByCategory(transactions);

        if (budgets.isEmpty()) {
            System.out.println("No budgets are set yet.");
            return;
        }

        System.out.println("\n========== BUDGET STATUS ==========");

        for (Budget budget : budgets) {
            double spent = expenses.containsKey(budget.getCategory())
                ? expenses.get(budget.getCategory()) : 0.0;

            double remaining = budget.getMonthlyLimit() - spent;

            System.out.printf(
                "%-15s | Budget: Rs. %-10.2f | Spent: Rs. %-10.2f | Remaining: Rs. %.2f%n",
                budget.getCategory(),
                budget.getMonthlyLimit(),
                spent,
                remaining
            );

            if (spent > budget.getMonthlyLimit()) {
                System.out.println(
                    "WARNING: Budget exceeded for " + budget.getCategory() + "!"
                );
            }
        }
    }
}