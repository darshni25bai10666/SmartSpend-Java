package com.smartspend.repository;

import com.smartspend.model.Budget;
import com.smartspend.model.Transaction;
import java.sql.SQLException;
import java.util.List;

public interface TransactionRepository {
    void saveTransaction(Transaction transaction) throws SQLException;

    List<Transaction> getAllTransactions() throws SQLException;

    void deleteTransaction(int id) throws SQLException;

    void saveOrUpdateBudget(Budget budget) throws SQLException;

    List<Budget> getAllBudgets() throws SQLException;
}