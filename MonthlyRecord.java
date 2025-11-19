import java.util.ArrayList;

public class MonthlyRecord {

    private int month, year;
    private double totalIncome = 0;
    private double totalExpense = 0;
    private double balance;
    private boolean closed = false;

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public MonthlyRecord(int month, int year, double carryOverBalance) {
        this.month = month;
        this.year = year;
        this.balance = carryOverBalance;
    }

    // Getters
    public int getMonth() { return month; }
    public int getYear() { return year; }
    public boolean isClosed() { return closed; }

    // Close month
    public void closeMonth() { 
        closed = true; 
    }

    // Only add amount to totals/balance
    public void addIncomeAmount(double amount) {
        totalIncome += amount;
        balance += amount;
    }

    public void addExpenseAmount(double amount) {
        totalExpense += amount;
        balance -= amount;
    }

    // Save transaction for history
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public double getBalance() { return balance; }

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
            System.out.println(t.getType() + " | " + t.getAmount() + " | " + t.getDescription());
        }
    }
}
