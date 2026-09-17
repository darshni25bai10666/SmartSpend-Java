PLEASE FIND ALL THE CODES UNDER THE COM FOLDER.

# SmartSpend: Personal Expense Tracker and Budget Analyzer

SmartSpend is a Java-based console application for managing personal income, expenses, and category-wise monthly budgets. It allows a user to record financial transactions, monitor spending, identify high-expense categories, generate a financial summary, and export the report to a text file.

The project is developed as an academic Java application to demonstrate Object-Oriented Programming, Exception Handling, Collections Framework, Java I/O Streams, and JDBC database connectivity.

---

## Project Objective

The objective of SmartSpend is to provide a simple offline system for individuals who want to track daily income and expenses in an organized way.

The system helps users:

- Record income and expense transactions
- Categorize expenses such as Food, Travel, Rent, Education, and Shopping
- Set monthly category-wise spending budgets
- Compare actual spending with set budgets
- Receive a warning when the budget is exceeded
- Calculate total income, total expenses, and remaining balance
- Identify the category with the highest expense
- Export a financial report for future reference

---

## Problem Statement

Many students and individuals find it difficult to track daily spending and maintain a planned monthly budget. Expenses are often recorded manually in notebooks or not recorded at all. This makes it difficult to know where money is spent, identify unnecessary expenses, and calculate the remaining available balance.

SmartSpend solves this problem through a menu-driven Java application. It stores transaction and budget data in an H2 database using JDBC, performs financial calculations, and generates readable monthly reports.

---

## Features

### 1. Transaction Management Module

- Add income transactions
- Add expense transactions
- Select a predefined category for each transaction
- Add transaction description and date
- View all stored transactions
- Delete a transaction using its generated transaction ID
- Store transactions permanently in an H2 database

### 2. Budget Management Module

- Set a monthly budget for an expense category
- Update an existing category budget
- View all configured budgets
- Calculate total expense in each category
- Compare actual spending against the budget limit
- Display a warning when a category budget is exceeded

### 3. Reports and Analytics Module

- Calculate total income
- Calculate total expense
- Calculate remaining balance

\[
\text{Balance} = \text{Total Income} - \text{Total Expense}
\]

- Display category-wise expense totals
- Identify the highest-spending category
- Generate a monthly financial report
- Export the report to `reports/monthly-report.txt`

---

## Functional Requirements

| ID | Functional requirement |
|---|---|
| FR-01 | The user shall be able to add an income transaction |
| FR-02 | The user shall be able to add an expense transaction |
| FR-03 | The user shall select a transaction category from predefined categories |
| FR-04 | The user shall be able to view all stored transactions |
| FR-05 | The user shall be able to delete a transaction using its ID |
| FR-06 | The user shall be able to set a monthly spending budget for a category |
| FR-07 | The user shall be able to update an existing category budget |
| FR-08 | The system shall compare spending with budget limits |
| FR-09 | The system shall display a warning if the budget limit is exceeded |
| FR-10 | The system shall calculate total income, total expense, and balance |
| FR-11 | The system shall show category-wise expense analysis |
| FR-12 | The system shall identify the highest-spending category |
| FR-13 | The user shall be able to export the financial report to a text file |
| FR-14 | The system shall store and retrieve data using JDBC and H2 Database |

---

## Non-Functional Requirements

| Requirement | Description |
|---|---|
| Usability | The application provides a simple numbered console menu, clear input prompts, and understandable success/error messages |
| Reliability | The system handles invalid amounts, invalid numeric inputs, missing transaction IDs, invalid dates, and database errors without unexpectedly stopping |
| Maintainability | Code is separated into model, service, repository, exception, and utility packages to make future changes easier |
| Performance | The application uses JDBC queries for persistent data retrieval and Java collections for efficient category-wise calculations |
| Data Integrity | Amounts and budgets must be greater than zero; categories are restricted through a Java enum |
| Resource Efficiency | JDBC connections, statements, result sets, and file-writing resources are closed after use |
| Portability | The application uses H2 embedded database and can run locally without installing a separate database server |
| Logging/Monitoring | Console success and failure messages help the user monitor important application operations |

---

## Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| BlueJ | Development environment used to create, compile, and run the application |
| JDBC | Java Database Connectivity APIused to communicate with the database |
| H2 Database | Embedded relational database used to store transactions and budgets |
| Java Collections Framework | `ArrayList`, `List`, `HashMap`, and `Map` used for data processing and analytics |
| Java I/O Streams | `FileWriter` and `BufferedWriter` used to export the report |
| Git and GitHub | Version control and source-code hosting |
| draw.io / diagrams.net | Creation of architecture, UML, workflow, and ER diagrams |

---

## Java Concepts Demonstrated

| Java concept | Implementation in SmartSpend |
|---|---|
| Variables and data types | Transaction ID, amount, description, date, category, and budget values |
| Input/output | `Scanner` accepts user input; console output displays menus and reports |
| Conditions | `if/else` validates amounts, input options, and budget limits |
| Loops | `while` keeps the menu running; `for` loops display categories, transactions, and reports |
| Switch statement | Main menu routes user choices to the correct operation |
| Classes and objects | `Transaction`, `Income`, `Expense`, `Budget`, and service/repository objects |
| Constructors | Objects are initialized with transaction, budget, and category information |
| Encapsulation | Model data is kept private and accessed through getter methods |
| Abstract class | `Transaction` is an abstract base class |
| Inheritance | `Income` and `Expense` extend the `Transaction` class |
| Polymorphism | `Income` and `Expense` override `getType()` and `getSignedAmount()` |
| Interface | `TransactionRepository` defines database operations |
| Enum | `Category` defines valid expense categories |
| Exception handling | `InvalidAmountException`, `try/catch`, `throw`, and `throws` are used |
| Collections | Lists store transactions; maps calculate category-wise expenses |
| File I/O | Reports are saved as `.txt` files |
| JDBC | `Connection`, `PreparedStatement`, `ResultSet`, and SQL queries are used |
| Database CRUD | Insert, retrieve, update budget, and delete transaction operations are performed |

---

## Project Architecture

```text
User
  |
  v
Main.java
Console User Interface
  |
  +--------------------+-------------------+------------------+
  |                    |                   |
  v                    v                   v
TransactionService   BudgetService      ReportService
  |                    |                   |
  +--------------------+-------------------+
                       |
                       v
          JdbcTransactionRepository
                       |
                       v
                JDBC + H2 Database
             /                     \
     transactions table        budgets table

ReportService
  |
  v
FileExporter
  |
  v
reports/monthly-report.txt
```

---

## Project Structure

```text
SmartSpend-Java/
├── com/
│   └── smartspend/
│       ├── Main.java
│       ├── model/
│       │   ├── Transaction.java
│       │   ├── Income.java
│       │   ├── Expense.java
│       │   ├── Budget.java
│       │   └── Category.java
│       ├── exception/
│       │   └── InvalidAmountException.java
│       ├── repository/
│       │   ├── TransactionRepository.java
│       │   └── JdbcTransactionRepository.java
│       ├── service/
│       │   ├── TransactionService.java
│       │   ├── BudgetService.java
│       │   └── ReportService.java
│       └── util/
│           ├── DatabaseConnection.java
│           └── FileExporter.java
├── database/
│   └── schema.sql
├── docs/
│   └── diagrams/
│       ├── architecture.png
│       ├── workflow.png
│       ├── use-case.png
│       ├── class-diagram.png
│       ├── sequence-diagram.png
│       └── er-diagram.png
├── screenshots/
│   ├── menu.png
│   ├── transactions-jdbc.png
│   ├── budget-warning.png
│   ├── monthly-report.png
│   ├── exported-report.png
│   └── exception-handling.png
├── reports/
│   └── monthly-report.txt
├── README.md
├── statement.md
└── .gitignore
```

---

## Database Design

SmartSpend uses two H2 database tables.

### `transactions` table

| Column | Data type | Description |
|---|---|---|
| `id` | INT | Auto-generated unique transaction ID |
| `transaction_type` | VARCHAR(20) | Stores `INCOME` or `EXPENSE` |
| `category` | VARCHAR(50) | Stores the transaction category |
| `amount` | DECIMAL(12,2) | Stores a positive transaction amount |
| `description` | VARCHAR(255) | Stores the user-provided transaction details |
| `transaction_date` | DATE | Stores the date of the transaction |

### `budgets` table

| Column | Data type | Description |
|---|---|---|
| `id` | INT | Auto-generated unique budget ID |
| `category` | VARCHAR(50) | Unique category name |
| `monthly_limit` | DECIMAL(12,2) | Maximum monthly budget allowed for the category |

---

## Setup Instructions

### Prerequisites

Install the following before running the project:

- Java Development Kit (JDK)
- BlueJ IDE
- H2 Database JAR file

### Add H2 JAR in BlueJ

1. Download the H2 Database JAR file from the official H2 website.
2. Open BlueJ.
3. Select `Tools → Preferences`.
4. Open the `Libraries` tab.
5. Click `Add` and select the H2 `.jar` file.
6. Click OK.
7. Close BlueJ completely.
8. Reopen BlueJ and open the SmartSpend project.

### Open and run project

1. Open BlueJ.
2. Select `Project → Open Project`.
3. Select the `SmartSpend-Java` folder.
4. Compile all classes.
5. Right-click `Main`.
6. Select:

```text
void main(String[] args)
```

7. Click `OK`.
8. The SmartSpend menu opens in the BlueJ terminal.

The application creates the H2 database and required tables automatically when it starts.

---

## Application Menu

```text
1. Add Income
2. Add Expense
3. View All Transactions
4. Delete Transaction
5. Set / Update Budget
6. View Budget Status
7. View Monthly Report
8. Export Monthly Report
0. Exit
```

---

## Sample Input and Output

### Sample income entry

```text
Enter your choice: 1
Select a category: OTHER
Enter amount: 25000
Enter description: Monthly allowance
Enter date: 2026-09-17

Income added successfully.
```

### Sample expense entry

```text
Enter your choice: 2
Select a category: FOOD
Enter amount: 700
Enter description: Restaurant expense
Enter date: 2026-09-17

Expense added successfully.
```

### Sample budget warning

```text
FOOD | Budget: Rs. 1000.00 | Spent: Rs. 1150.00 | Remaining: Rs. -150.00
WARNING: Budget exceeded for FOOD!
```

### Sample report

```text
========== SMARTSPEND MONTHLY REPORT ==========
Total Income : Rs. 25000.00
Total Expense: Rs. 2269.00
Balance      : Rs. 22731.00

Category-wise Expenses:
- FOOD            Rs. 1150.00
- TRAVEL          Rs. 120.00
- EDUCATION       Rs. 999.00

Highest Expense Category: FOOD (Rs. 1150.00)
===============================================
```

---

## Testing Instructions

Perform the following manual tests after running the application.

| Test ID | Test case | Test input/action | Expected result |
|---|---|---|---|
| T01 | Add income | Add income of Rs. 25,000 | Income transaction is saved successfully |
| T02 | Add expense | Add Food expense of Rs. 450 | Expense transaction is saved successfully |
| T03 | View transactions | Choose `View All Transactions` | All stored transactions appear with IDs |
| T04 | Invalid amount | Enter `0` or `-200` as amount | Error message: amount must be greater than zero |
| T05 | Invalid numeric input | Enter text instead of a number | System requests a valid numeric value |
| T06 | Set budget | Set Food budget to Rs. 1,000 | Budget is saved successfully |
| T07 | Budget exceeded | Add Food expenses of Rs. 1,150 | Warning is displayed for Food category |
| T08 | Generate report | Choose `View Monthly Report` | Correct income, expense, balance, and category details are shown |
| T09 | Export report | Choose `Export Monthly Report` | `reports/monthly-report.txt` is created |
| T10 | Delete transaction | Delete an existing transaction ID | The transaction is removed |
| T11 | Missing ID | Delete an unavailable ID | Error is shown without application crash |
| T12 | Database persistence | Restart program after adding records | Previously saved records remain available |

---

## Screenshots

Add screenshots after running the application.

| Screenshot | Description |
|---|---|
| `screenshots/menu.png` | SmartSpend main menu |
| `screenshots/transactions-jdbc.png` | Transaction list retrieved through JDBC |
| `screenshots/budget-warning.png` | Budget status and exceeded-budget warning |
| `screenshots/monthly-report.png` | Monthly report output |
| `screenshots/exported-report.png` | Exported report file opened in a text editor |
| `screenshots/exception-handling.png` | Invalid amount exception handling |

Example Markdown image links after you add files:

```markdown







```

---

## Design Diagrams

The following diagrams are included in `docs/diagrams/`:

- System Architecture Diagram
- Workflow Diagram
- Use Case Diagram
- Class Diagram
- Sequence Diagram
- Entity Relationship Diagram

---

## Limitations

- The system is designed for one user only.
- It is a console-based application and does not contain a graphical user interface.
- Budget calculations currently use all saved transactions and can be enhanced with month-wise date filtering.
- The system does not include user login, authentication, or cloud synchronization.
- Notifications are displayed in the console only; real email/SMS notifications are not implemented.

---

## Future Enhancements

- Add a JavaFX or Swing graphical user interface.
- Add user authentication and password protection.
- Add date-range and month-specific financial reports.
- Add transaction editing functionality.
- Add charts for category-wise spending analysis.
- Add receipt upload functionality.
- Send automated budget alerts using email or notifications.
- Add multithreaded periodic reminder functionality.
- Replace or extend JDBC implementation with JPA/Hibernate.
- Add cloud database support using MySQL/PostgreSQL.

---

## Author

**Name:** DARSHNI SAHU 
**Registration no.** 25BAI10666
**Course:** Java Programming  
**Institution:** VITyarthi  
**Academic Year:** 2026  

Replace `Your Name` with your actual name before submitting.

---

## License

This project is created for academic and learning purposes.
