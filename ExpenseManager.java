import java.io.*;
import java.util.ArrayList;

public class ExpenseManager {

    ArrayList<MonthlyRecord> months = new ArrayList<>();
    private double lastBalance = 0; // carry-over from previous month
    private static final String FILE_NAME = "expense_data.txt";

    // Find month, return null if not exists
    private MonthlyRecord findMonth(int year, int month) {
        try {
            for (MonthlyRecord m : months) {
                if (m.getYear() == year && m.getMonth() == month) {
                    return m;
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding month: " + e.getMessage());
        }
        return null;
    }

    // Get month, create if doesn't exist
    public MonthlyRecord getMonthlyRecord(int year, int month) {
        try {
            MonthlyRecord m = findMonth(year, month);
            if (m == null) {
                m = new MonthlyRecord(month, year, lastBalance);
                months.add(m);
            }
            return m;
        } catch (Exception e) {
            System.out.println("Error getting month: " + e.getMessage());
            return null;
        }
    }

    public void addIncome(int year, int month, double amount, String description) {
        try {
            MonthlyRecord m = getMonthlyRecord(year, month);
            if (m.isClosed()) {
                System.out.println("Cannot add income. This month is closed.");
                return;
            }
            Income income = new Income(amount, description);
            income.apply(m);
            lastBalance = m.getBalance();
        } catch (Exception e) {
            System.out.println("Error adding income: " + e.getMessage());
        }
    }

    public void addExpense(int year, int month, double amount, String description) {
        try {
            MonthlyRecord m = getMonthlyRecord(year, month);
            if (m.isClosed()) {
                System.out.println("Cannot add expense. This month is closed.");
                return;
            }
            Expense expense = new Expense(amount, description);
            expense.apply(m);
            lastBalance = m.getBalance();
        } catch (Exception e) {
            System.out.println("Error adding expense: " + e.getMessage());
        }
    }

    // View one month history
    public void viewMonth(int year, int month) {
        try {
            MonthlyRecord m = findMonth(year, month);
            if (m == null) {
                System.out.println("No records for " + month + "/" + year);
                return;
            }
            m.printHistory();
        } catch (Exception e) {
            System.out.println("Error viewing month: " + e.getMessage());
        }
    }

    // View all months
    public void viewAllMonths() {
        try {
            if (months.isEmpty()) {
                System.out.println("No records yet.");
                return;
            }
            for (MonthlyRecord m : months) {
                System.out.println();
                m.printHistory();
            }
        } catch (Exception e) {
            System.out.println("Error viewing all months: " + e.getMessage());
        }
    }

    // To get current balance 
    public double getCurrentBalance() {
        try {
            return lastBalance;
        } catch (Exception e) {
            System.out.println("Error getting current balance: " + e.getMessage());
            return 0;
        }
    }

    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (MonthlyRecord m : months) {
                pw.println(m.getMonth() + "," + m.getYear() + "," + m.getBalance() + "," + m.isClosed());
                for (Transaction t : m.getTransactions()) {
                    pw.println(t.getType() + "," + t.getAmount() + "," + t.getDescription());
                }
                pw.println("END_MONTH");
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        months.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            MonthlyRecord currentMonth = null;
            while ((line = br.readLine()) != null) {
                if (line.equals("END_MONTH")) {
                    currentMonth = null;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]);
                    double balance = Double.parseDouble(parts[2]);
                    boolean closed = Boolean.parseBoolean(parts[3]);
                    currentMonth = new MonthlyRecord(month, year, balance);
                    if (closed) currentMonth.closeMonth();
                    months.add(currentMonth);
                    lastBalance = balance;
                } else if (parts.length == 3 && currentMonth != null) {
                    String type = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    String desc = parts[2];
                    if (type.equals("Income")) currentMonth.addTransaction(new Income(amount, desc));
                    else currentMonth.addTransaction(new Expense(amount, desc));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Starting fresh.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
