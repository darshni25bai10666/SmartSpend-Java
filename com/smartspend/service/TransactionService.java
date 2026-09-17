package com.smartspend.service;

import com.smartspend.exception.InvalidAmountException;
import com.smartspend.model.Category;
import com.smartspend.model.Expense;
import com.smartspend.model.Income;
import com.smartspend.model.Transaction;
import com.smartspend.repository.TransactionRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public void addIncome(Category category, double amount,
                          String description, LocalDate date)
            throws InvalidAmountException, SQLException {

        validateAmount(amount);
        repository.saveTransaction(new Income(0, category, amount, description, date));
    }

    public void addExpense(Category category, double amount,
                           String description, LocalDate date)
            throws InvalidAmountException, SQLException {

        validateAmount(amount);
        repository.saveTransaction(new Expense(0, category, amount, description, date));
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        return repository.getAllTransactions();
    }

    public void deleteTransaction(int id) throws SQLException {
        repository.deleteTransaction(id);
    }

    private void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException(
                "Amount must be greater than zero."
            );
        }
    }
}