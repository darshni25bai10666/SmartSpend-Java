package com.smartspend.repository;

import com.smartspend.model.Budget;
import com.smartspend.model.Category;
import com.smartspend.model.Expense;
import com.smartspend.model.Income;
import com.smartspend.model.Transaction;
import com.smartspend.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionRepository implements TransactionRepository {

    public void saveTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions " +
                     "(transaction_type, category, amount, description, transaction_date) " +
                     "VALUES (?, ?, ?, ?, ?)";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, transaction.getType());
        statement.setString(2, transaction.getCategory().name());
        statement.setDouble(3, transaction.getAmount());
        statement.setString(4, transaction.getDescription());
        statement.setDate(5, Date.valueOf(transaction.getTransactionDate()));

        statement.executeUpdate();

        statement.close();
        connection.close();
    }

    public List<Transaction> getAllTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<Transaction>();

        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC, id DESC";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("transaction_type");
            Category category = Category.valueOf(resultSet.getString("category"));
            double amount = resultSet.getDouble("amount");
            String description = resultSet.getString("description");

            Transaction transaction;

            if ("INCOME".equalsIgnoreCase(type)) {
                transaction = new Income(
                    id, category, amount, description,
                    resultSet.getDate("transaction_date").toLocalDate()
                );
            } else {
                transaction = new Expense(
                    id, category, amount, description,
                    resultSet.getDate("transaction_date").toLocalDate()
                );
            }

            transactions.add(transaction);
        }

        resultSet.close();
        statement.close();
        connection.close();

        return transactions;
    }

    public void deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);
        int affectedRows = statement.executeUpdate();

        statement.close();
        connection.close();

        if (affectedRows == 0) {
            throw new SQLException("No transaction found with ID " + id);
        }
    }

    public void saveOrUpdateBudget(Budget budget) throws SQLException {
        String updateSql = "UPDATE budgets SET monthly_limit = ? WHERE category = ?";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);

        updateStatement.setDouble(1, budget.getMonthlyLimit());
        updateStatement.setString(2, budget.getCategory().name());

        int affectedRows = updateStatement.executeUpdate();
        updateStatement.close();

        if (affectedRows == 0) {
            String insertSql =
                "INSERT INTO budgets (category, monthly_limit) VALUES (?, ?)";

            PreparedStatement insertStatement =
                connection.prepareStatement(insertSql);

            insertStatement.setString(1, budget.getCategory().name());
            insertStatement.setDouble(2, budget.getMonthlyLimit());
            insertStatement.executeUpdate();
            insertStatement.close();
        }

        connection.close();
    }

    public List<Budget> getAllBudgets() throws SQLException {
        List<Budget> budgets = new ArrayList<Budget>();

        String sql = "SELECT category, monthly_limit FROM budgets ORDER BY category";

        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Category category = Category.valueOf(resultSet.getString("category"));
            double limit = resultSet.getDouble("monthly_limit");
            budgets.add(new Budget(category, limit));
        }

        resultSet.close();
        statement.close();
        connection.close();

        return budgets;
    }
}