import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new ExpenseTrackerGUI();
    }
}

// ------------------- GUI CLASS -------------------
class ExpenseTrackerGUI extends JFrame {

    ExpenseManager manager;
    MonthlyRecord currentMonth;

    JTextField yearField, amountField, descField;
    JComboBox<String> monthDropdown;
    JTable historyTable;
    DefaultTableModel tableModel;

    // We need references to disable these later
    JButton addIncomeBtn, addExpenseBtn, closeMonthBtn;

    public ExpenseTrackerGUI() {
        manager = new ExpenseManager();
        manager.loadFromFile();

        setTitle("Expense Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initMainMenu();// calling function of main menuuu
        
        setVisible(true);
    }

    // ------------------- MAIN MENU -------------------
    private void initMainMenu() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(219, 208, 195));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 200, 100, 200));

        Font btnFont = new Font("Segoe UI", Font.BOLD, 18);

        JButton openMonthBtn = new JButton("Enter Monthly Record");
        JButton viewHistoryBtn = new JButton("View Month History");
        JButton viewBalanceBtn = new JButton("View Account Balance");

        Dimension btnSize = new Dimension(250, 50);
        openMonthBtn.setPreferredSize(btnSize);
        viewHistoryBtn.setPreferredSize(btnSize);
        viewBalanceBtn.setPreferredSize(btnSize);

        openMonthBtn.setFont(btnFont);
        viewHistoryBtn.setFont(btnFont);
        viewBalanceBtn.setFont(btnFont);

        openMonthBtn.setBackground(new Color(62, 66, 66));
        openMonthBtn.setForeground(Color.WHITE);
        viewHistoryBtn.setBackground(new Color(62, 66, 66));
        viewHistoryBtn.setForeground(Color.WHITE);
        viewBalanceBtn.setBackground(new Color(62, 66, 66));
        viewBalanceBtn.setForeground(Color.WHITE);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(new Color(219, 208, 195));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        wrapper.add(openMonthBtn, gbc);
        gbc.gridy = 1;
        wrapper.add(viewHistoryBtn, gbc);
        gbc.gridy = 2;
        wrapper.add(viewBalanceBtn, gbc);

        JLabel footer = new JLabel("Made by Laiba and Ayesha");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setForeground(new Color(62, 66, 66));
        footer.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().setLayout(new BorderLayout());
        add(wrapper, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        openMonthBtn.addActionListener(e -> openMonthForm());
        viewHistoryBtn.addActionListener(e -> viewMonthHistory());
        viewBalanceBtn.addActionListener(e -> showGlobalBalance());

        revalidate();
        repaint();
    }

    private void showGlobalBalance() {
        JOptionPane.showMessageDialog(this, "Global Account Balance: " + manager.getCurrentBalance());
    }

    private void openMonthForm() {
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(219, 208, 195));

        // Year panel
        JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearPanel.setBackground(new Color(219, 208, 195));
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setForeground(new Color(62, 66, 66));
        yearPanel.add(yearLabel);
        yearField = new JTextField();
        yearField.setPreferredSize(new Dimension(100, 25));
        yearPanel.add(yearField);

        // Month panel
        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monthPanel.setBackground(new Color(219, 208, 195));
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setForeground(new Color(62, 66, 66));
        monthPanel.add(monthLabel);
        monthDropdown = new JComboBox<>();
        for (int i = 1; i <= 12; i++) monthDropdown.addItem(String.valueOf(i));
        monthPanel.add(monthDropdown);

        // Amount panel
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        amountPanel.setBackground(new Color(219, 208, 195));
        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(new Color(62, 66, 66));
        amountPanel.add(amountLabel);
        amountField = new JTextField();
        amountField.setPreferredSize(new Dimension(100, 25));
        amountPanel.add(amountField);

        // Description panel
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descPanel.setBackground(new Color(219, 208, 195));
        JLabel descLabel = new JLabel("Description:");
        descLabel.setForeground(new Color(62, 66, 66));
        descPanel.add(descLabel);
        descField = new JTextField();
        descField.setPreferredSize(new Dimension(200, 25));
        descPanel.add(descField);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(219, 208, 195));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        addIncomeBtn = new JButton("Add Income");
        addExpenseBtn = new JButton("Add Expense");
        closeMonthBtn = new JButton("Close Month");
        JButton backBtn = new JButton("Back to Menu");

        Dimension btnSize = new Dimension(200, 40);
        addIncomeBtn.setPreferredSize(btnSize);
        addExpenseBtn.setPreferredSize(btnSize);
        closeMonthBtn.setPreferredSize(btnSize);
        backBtn.setPreferredSize(btnSize);

        addIncomeBtn.setFont(btnFont);
        addExpenseBtn.setFont(btnFont);
        closeMonthBtn.setFont(btnFont);
        backBtn.setFont(btnFont);

        addIncomeBtn.setBackground(new Color(62, 66, 66));
        addIncomeBtn.setForeground(Color.WHITE);
        addExpenseBtn.setBackground(new Color(62, 66, 66));
        addExpenseBtn.setForeground(Color.WHITE);
        closeMonthBtn.setBackground(new Color(62, 66, 66));
        closeMonthBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(62, 66, 66));
        backBtn.setForeground(Color.WHITE);

        gbc.gridy = 0;
        buttonPanel.add(addIncomeBtn, gbc);
        gbc.gridy = 1;
        buttonPanel.add(addExpenseBtn, gbc);
        gbc.gridy = 2;
        buttonPanel.add(closeMonthBtn, gbc);
        gbc.gridy = 3;
        buttonPanel.add(backBtn, gbc);

        mainPanel.add(yearPanel);
        mainPanel.add(monthPanel);
        mainPanel.add(amountPanel);
        mainPanel.add(descPanel);
        mainPanel.add(buttonPanel);

        setLayout(new BorderLayout());

// HEADER
    JLabel header = new JLabel("Monthly Transactions", SwingConstants.CENTER);
    header.setFont(new Font("Segoe UI", Font.BOLD, 24));
    header.setForeground(new Color(62, 66, 66));

    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(new Color(219, 208, 195));
    headerPanel.add(header, BorderLayout.CENTER);

    add(headerPanel, BorderLayout.NORTH);
    add(mainPanel, BorderLayout.CENTER);

        revalidate();
        repaint();

        addIncomeBtn.addActionListener(e -> addIncome());
        addExpenseBtn.addActionListener(e -> addExpense());
        closeMonthBtn.addActionListener(e -> closeCurrentMonth());
        backBtn.addActionListener(e -> initMainMenu());
    }

    private void lockIfClosed() {
        if (currentMonth != null && currentMonth.isClosed()) {
            addIncomeBtn.setEnabled(false);
            addExpenseBtn.setEnabled(false);
            closeMonthBtn.setEnabled(false);
        }
    }

    private void addIncome() {
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int month = Integer.parseInt(monthDropdown.getSelectedItem().toString());
            double amount = Double.parseDouble(amountField.getText().trim());
            String desc = descField.getText().trim();

            currentMonth = manager.getMonthlyRecord(year, month);

            if (currentMonth.isClosed()) {
                JOptionPane.showMessageDialog(this, "This month is closed. You cannot add income.");
                return;
            }

            if (year <= 0 || amount <= 0 || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter valid year, amount (>0), and description!");
                return;
            }

            manager.addIncome(year, month, amount, desc);
            JOptionPane.showMessageDialog(this, "Income added!");

            amountField.setText("");
            descField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
        }
    }

    private void addExpense() {
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int month = Integer.parseInt(monthDropdown.getSelectedItem().toString());
            double amount = Double.parseDouble(amountField.getText().trim());
            String desc = descField.getText().trim();

            currentMonth = manager.getMonthlyRecord(year, month);

            if (currentMonth.isClosed()) {
                JOptionPane.showMessageDialog(this, "This month is closed. You cannot add expense.");
                return;
            }

            if (amount > currentMonth.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance!");
                return;
            }

            manager.addExpense(year, month, amount, desc);
            JOptionPane.showMessageDialog(this, "Expense added!");

            amountField.setText("");
            descField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
        }
    }

    private void closeCurrentMonth() {
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int month = Integer.parseInt(monthDropdown.getSelectedItem().toString());

            currentMonth = manager.getMonthlyRecord(year, month);

            if (currentMonth.isClosed()) {
                JOptionPane.showMessageDialog(this, "This month is already closed.");
                return;
            }

            currentMonth.closeMonth();
            manager.saveToFile();

            JOptionPane.showMessageDialog(this, "Month closed and saved!");

            addIncomeBtn.setEnabled(false);
            addExpenseBtn.setEnabled(false);
            closeMonthBtn.setEnabled(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid year & month to close.");
        }
    }

    private void viewMonthHistory() {
        String yearStr = JOptionPane.showInputDialog(this, "Enter year:");
        if (yearStr == null) return;
        String monthStr = JOptionPane.showInputDialog(this, "Enter month (1-12):");
        if (monthStr == null) return;

        try {
            int year = Integer.parseInt(yearStr.trim());
            int month = Integer.parseInt(monthStr.trim());

            currentMonth = manager.getMonthlyRecord(year, month);

            if (currentMonth.getTransactions().isEmpty()) {
                JOptionPane.showMessageDialog(this, "No transactions for this month!");
                return;
            }

            showHistory();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers!");
        }
    }

    private void showHistory() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(219, 208, 195));

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Type");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Description");

        for (Transaction t : currentMonth.getTransactions()) {
            tableModel.addRow(new Object[]{t.getType(), t.getAmount(), t.getDescription()});
        }

        historyTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historyTable);

        JButton backBtn = new JButton("Back");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(62, 66, 66));
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.addActionListener(e -> initMainMenu());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        add(panel);
        revalidate();
        repaint();
    }
}
