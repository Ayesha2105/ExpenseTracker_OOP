import java.util.ArrayList;

public class ExpenseManager {

    private ArrayList<MonthlyRecord> months = new ArrayList<>();
    private double lastBalance = 0; // carry-over from previous month

    // Find month, return null if not exists
    private MonthlyRecord findMonth(int year, int month) {
        for (MonthlyRecord m : months) {
            if (m.getYear() == year && m.getMonth() == month) {
                return m;
            }
        }
        return null;
    }

    // Get month, create if doesn't exist
    public MonthlyRecord getMonthlyRecord(int year, int month) {
        MonthlyRecord m = findMonth(year, month);
        if (m == null) {
            m = new MonthlyRecord(month, year, lastBalance);
            months.add(m);
        }
        return m;
    }

public void addIncome(int year, int month, double amount, String description) {
    MonthlyRecord m = getMonthlyRecord(year, month);
    if (m.isClosed()) {
        System.out.println("Cannot add income. This month is closed.");
        return;
    }
    Income income = new Income(amount, description);
    income.apply(m);
    lastBalance = m.getBalance();
}

public void addExpense(int year, int month, double amount, String description) {
    MonthlyRecord m = getMonthlyRecord(year, month);
    if (m.isClosed()) {
        System.out.println("Cannot add expense. This month is closed.");
        return;
    }
    Expense expense = new Expense(amount, description);
    expense.apply(m);
    lastBalance = m.getBalance();
}

    // View one month history
    public void viewMonth(int year, int month) {
        MonthlyRecord m = findMonth(year, month);
        if (m == null) {
            System.out.println("No records for " + month + "/" + year);
            return;
        }
        m.printHistory();
    }

    // View all months
    public void viewAllMonths() {
        if (months.isEmpty()) {
            System.out.println("No records yet.");
            return;
        }
        for (MonthlyRecord m : months) {
            System.out.println();
            m.printHistory();
        }
    }

    // Get current balance (latest month)
    public double getCurrentBalance() {
        return lastBalance;
    }
}

