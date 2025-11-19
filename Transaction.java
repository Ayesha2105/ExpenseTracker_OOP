abstract class Transaction {
    protected double amount;
    protected String description;

    Transaction(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }

    // abstract method to apply transaction to a month
    public abstract void apply(MonthlyRecord record);

    // type for history printing
    public abstract String getType();
}

class Income extends Transaction {
    Income(double amount, String description) {
        super(amount, description);
    }

    @Override
    public void apply(MonthlyRecord record) {
        if (record.isClosed()) {
            System.out.println("Cannot add income. Month is closed!");
            return;
        }
        record.addIncomeAmount(amount); // just add amount it i a method in monthly record
        record.addTransaction(this);   // save for history
    }

    @Override
    public String getType() { return "Income"; }
}

class Expense extends Transaction {
    Expense(double amount, String description) {
        super(amount, description);
    }

    @Override
    public void apply(MonthlyRecord record) {
        if (record.isClosed()) {
            System.out.println("Cannot add expense. Month is closed!");
            return;
        }
        record.addExpenseAmount(amount); // just add amount
        record.addTransaction(this);     // save for history
    }

    @Override
    public String getType() { return "Expense"; }
}
