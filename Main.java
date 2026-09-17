package com.smartspend;

import com.smartspend.exception.InvalidAmountException;
import com.smartspend.model.Budget;
import com.smartspend.model.Category;
import com.smartspend.model.Transaction;
import com.smartspend.repository.JdbcTransactionRepository;
import com.smartspend.repository.TransactionRepository;
import com.smartspend.service.BudgetService;
import com.smartspend.service.ReportService;
import com.smartspend.service.TransactionService;
import com.smartspend.util.DatabaseConnection;
import com.smartspend.util.FileExporter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        TransactionRepository repository = new JdbcTransactionRepository();
        TransactionService transactionService = new TransactionService(repository);
        BudgetService budgetService = new BudgetService(repository);
        ReportService reportService = new ReportService();

        boolean running = true;

        System.out.println("========================================");
        System.out.println("   SMARTSPEND EXPENSE TRACKER SYSTEM");
        System.out.println("========================================");

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        addTransaction(transactionService, true);
                        break;
                    case 2:
                        addTransaction(transactionService, false);
                        break;
                    case 3:
                        showAllTransactions(transactionService);
                        break;
                    case 4:
                        deleteTransaction(transactionService);
                        break;
                    case 5:
                        setBudget(budgetService);
                        break;
                    case 6:
                        showBudgets(repository, budgetService);
                        break;
                    case 7:
                        showReport(transactionService, reportService);
                        break;
                    case 8:
                        exportReport(transactionService, reportService);
                        break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using SmartSpend.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 0 to 8.");
                }
            } catch (InvalidAmountException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("Database operation failed: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("File export failed: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. Delete Transaction");
        System.out.println("5. Set / Update Budget");
        System.out.println("6. View Budget Status");
        System.out.println("7. View Monthly Report");
        System.out.println("8. Export Monthly Report");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void addTransaction(TransactionService service, boolean isIncome)
            throws InvalidAmountException, SQLException {

        System.out.println(isIncome ? "\n--- Add Income ---" : "\n--- Add Expense ---");

        Category category = readCategory();
        double amount = readDouble("Enter amount: ");
        String description = readText("Enter description: ");
        LocalDate date = readDate();

        if (isIncome) {
            service.addIncome(category, amount, description, date);
            System.out.println("Income added successfully.");
        } else {
            service.addExpense(category, amount, description, date);
            System.out.println("Expense added successfully.");
        }
    }

    private static void showAllTransactions(TransactionService service)
            throws SQLException {

        List<Transaction> transactions = service.getAllTransactions();

        System.out.println("\n========== ALL TRANSACTIONS ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void deleteTransaction(TransactionService service)
            throws SQLException {

        int id = readInt("Enter transaction ID to delete: ");
        service.deleteTransaction(id);
        System.out.println("Transaction deleted successfully.");
    }

    private static void setBudget(BudgetService service)
            throws InvalidAmountException, SQLException {

        System.out.println("\n--- Set / Update Budget ---");

        Category category = readCategory();
        double limit = readDouble("Enter monthly budget limit: ");

        service.setBudget(category, limit);
        System.out.println("Budget saved successfully.");
    }

    private static void showBudgets(TransactionRepository repository,
                                    BudgetService service) throws SQLException {

        List<Budget> budgets = service.getAllBudgets();

        System.out.println("\n========== SAVED BUDGETS ==========");

        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
        } else {
            for (Budget budget : budgets) {
                System.out.println(budget);
            }
        }

        service.showBudgetStatus(repository.getAllTransactions());
    }

    private static void showReport(TransactionService transactionService,
                                   ReportService reportService) throws SQLException {

        List<Transaction> transactions = transactionService.getAllTransactions();
        System.out.println(reportService.generateReport(transactions));
    }

    private static void exportReport(TransactionService transactionService,
                                     ReportService reportService)
            throws SQLException, IOException {

        List<Transaction> transactions = transactionService.getAllTransactions();
        String report = reportService.generateReport(transactions);

        String filePath = "reports/monthly-report.txt";
        FileExporter.exportReport(report, filePath);

        System.out.println("Report exported successfully to: " + filePath);
    }

    private static Category readCategory() {
        Category[] categories = Category.values();

        System.out.println("\nSelect a category:");

        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }

        while (true) {
            int selected = readInt("Enter category number: ");

            if (selected >= 1 && selected <= categories.length) {
                return categories[selected - 1];
            }

            System.out.println("Invalid category number. Try again.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static LocalDate readDate() {
        while (true) {
            System.out.print("Enter date (YYYY-MM-DD), or press Enter for today: ");
            String input = scanner.nextLine().trim();

            if (input.length() == 0) {
                return LocalDate.now();
            }

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}