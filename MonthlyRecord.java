import java.io.*;
import java.util.ArrayList;

public class MonthlyRecord {

    private int month, year;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double balance;
    private boolean closed = false;

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public MonthlyRecord(int month, int year, double carryOverBalance) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1-12");
        }
        this.month = month;
        this.year = year;
        this.balance = carryOverBalance;
    }

    //short helprer methods getter and setters
    public int getMonth() {
        return month;
    }
    public int getYear() { 
        return year; 
    }
    public boolean isClosed() { 
        return closed; 
    }

    public ArrayList<Transaction> getTransactions() { 
        return transactions; 
    }

    public void setBalance(double balance) { 
        this.balance = balance;
    }

    // Close month
    public void closeMonth() { 
        closed = true;
    }


    // Add income safely
    public void addIncomeAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Income must be greater than 0");
            return;
        }
        try {
            totalIncome += amount;
            balance += amount;
        } catch (Exception e) {
            System.out.println("Error adding income: " + e.getMessage());
        }
    }

    // Add expense safely
    public void addExpenseAmount(double amount) {
        if (amount <= 0) {
            System.out.println("Expense must be greater than 0");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }
        try {
            totalExpense += amount;
            balance -= amount;
        } catch (Exception e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    // Save transaction for monthly history
    public void addTransaction(Transaction t) {
        if (t == null) {
            System.out.println("Cannot add null transaction");
            return;
        }
        try {
            transactions.add(t);
        } catch (Exception e) {
            System.out.println("Error adding transaction: " + e.getMessage());
        }
    }

    public double getBalance() { 
        return balance; 
    }

    // Print history
    public void printHistory() {
        System.out.println("---- " + month + "/" + year + " ----");
        System.out.println("Balance: " + balance);
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        System.out.println("Incomes & Expenses:");
        for (Transaction t : transactions) {
            try {
                System.out.println(t.getType() + " | " + t.getAmount() + " | " + t.getDescription());
            } catch (Exception e) {
                System.out.println("Error printing transaction: " + e.getMessage());
            }
        }
    }

    // Save this month to file
    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            // Month header
            bw.write("Month:" + month + ",Year:" + year + ",Balance:" + balance + ",Closed:" + closed);
            bw.newLine();
            // Transactions
            for (Transaction t : transactions) {
                bw.write(t.getType() + "," + t.getAmount() + "," + t.getDescription());
                bw.newLine();
            }
            bw.write("END"); // end of month
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving month: " + e.getMessage());
        }
    }

    // Load month from file lines
    public static MonthlyRecord loadFromLines(String[] lines) {
        MonthlyRecord monthRecord = null;
        try {
            if (lines.length < 1) 
                return null;

            String[] header = lines[0].split(",");
            int month = Integer.parseInt(header[0].split(":")[1]);
            int year = Integer.parseInt(header[1].split(":")[1]);
            double balance = Double.parseDouble(header[2].split(":")[1]);
            boolean closed = Boolean.parseBoolean(header[3].split(":")[1]);

            monthRecord = new MonthlyRecord(month, year, 0);
            monthRecord.setBalance(balance);
            if (closed) monthRecord.closeMonth();

            for (int i = 1; i < lines.length; i++) {
                if (lines[i].equals("END"))
                    break;
                
                String[] tParts = lines[i].split(",", 3);
                if (tParts.length < 3) continue; // skip invalid lines
                String type = tParts[0];
                double amount = Double.parseDouble(tParts[1]);
                String desc = tParts[2];

                if (type.equalsIgnoreCase("Income")) {
                    Income income = new Income(amount, desc);
                    monthRecord.addTransaction(income);
                    monthRecord.addIncomeAmount(amount);
                } else if (type.equalsIgnoreCase("Expense")) {
                    Expense expense = new Expense(amount, desc);
                    monthRecord.addTransaction(expense);
                    monthRecord.addExpenseAmount(amount);
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading month: " + e.getMessage());
        }
        return monthRecord;
    }
}
