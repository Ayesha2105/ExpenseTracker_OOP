// Transaction.java
abstract class Transaction {
    protected double amount;
    protected String description;

    // Constructor with validation
    Transaction(double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0.");
        }
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() { 
        return amount;
    }
    public String getDescription() { 
        return description;
     }

    // Apply this transaction to a MonthlyRecord
    public abstract void apply(MonthlyRecord record);

    // Type for history printing
    public abstract String getType();
}

// Income class
class Income extends Transaction {
    Income(double amount, String description) {
        super(amount, description);
    }

    @Override
    public void apply(MonthlyRecord record) {
        try {
            if (record == null) {
                System.out.println(" Monthly record not found!");
                return;
            }
            if (record.isClosed()) {
                System.out.println(" Cannot add income. Month is closed!");
                return;
            }
            if (amount <= 0) {
                System.out.println(" Income amount must be greater than 0!");
                return;
            }
            record.addIncomeAmount(amount); // Add amount to balance
            record.addTransaction(this);    // Save transaction for history
        } catch (Exception e) {
            System.out.println("Error applying income: " + e.getMessage());
        }
    }

    @Override
    public String getType() {
        return "Income";
    }
}

// Expense class
class Expense extends Transaction {
    Expense(double amount, String description) {
        super(amount, description);
    }

    @Override
    public void apply(MonthlyRecord record) {
        try {
            if (record == null) {
                System.out.println(" Monthly record not found!");
                return;
            }
            if (record.isClosed()) {
                System.out.println("Cannot add expense. Month is closed!");
                return;
            }
            if (amount <= 0) {
                System.out.println(" Expense amount must be greater than 0!");
                return;
            }
            if (amount > record.getBalance()) {
                System.out.println("Insufficient balance!");
                return;
            }
            record.addExpenseAmount(amount); // Deduct from balance
            record.addTransaction(this);     // Save transaction for history
        } catch (Exception e) {
            System.out.println("Error applying expense: " + e.getMessage());
        }
    }

    @Override
    public String getType() {
        return "Expense";
    }
}
