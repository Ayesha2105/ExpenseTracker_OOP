import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        // Load previous data
        manager.loadFromFile();

        while (true) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Open Month");
            System.out.println("2. View All Months");
            System.out.println("3. View Current Balance");
            System.out.println("4. Exit");

            int choice;
            try {
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(" Invalid number. Try again.");
                sc.nextLine();
                continue;
            }

            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: // Open Month
                    int year, month;
                    try {
                        System.out.print("Enter year: ");
                        year = sc.nextInt();
                        System.out.print("Enter month (1-12): ");
                        month = sc.nextInt();
                        if (year <= 0 || month < 1 || month > 12) {
                            System.out.println("Invalid year or month.");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input!");
                        sc.nextLine();
                        continue;
                    }
                    sc.nextLine(); // consume newline

                    MonthlyRecord monthRecord = manager.getMonthlyRecord(year, month);
                    boolean exitMonth = false;

                    while (!exitMonth) {
                        System.out.println("\n--- " + month + "/" + year + " ---");
                        System.out.println("1. Add Income");
                        System.out.println("2. Add Expense");
                        System.out.println("3. View Month History");
                        System.out.println("4. Close Month");
                        System.out.println("5. Exit Month without Closing");

                        int mChoice;
                        try {
                            System.out.print("Enter choice: ");
                            mChoice = sc.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid number. Try again.");
                            sc.nextLine();
                            continue;
                        }
                        sc.nextLine(); // consume newline

                        switch (mChoice) {
                            case 1: // Add Income
                                if (monthRecord.isClosed()) {
                                    System.out.println(" Month is closed.");
                                    break;
                                }
                                double inAmt;
                                try {
                                    System.out.print("Enter income amount: ");
                                    inAmt = sc.nextDouble();
                                    if (inAmt <= 0) {
                                        System.out.println("Income must be > 0.");
                                        break;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println(" Invalid amount.");
                                    sc.nextLine();
                                    break;
                                }
                                sc.nextLine();
                                System.out.print("Enter description: ");
                                String inDesc = sc.nextLine();
                                manager.addIncome(year, month, inAmt, inDesc);
                                System.out.println(" Income added!");
                                break;

                            case 2: // Add Expense
                                if (monthRecord.isClosed()) {
                                    System.out.println("Month is closed.");
                                    break;
                                }
                                double exAmt;
                                try {
                                    System.out.print("Enter expense amount: ");
                                    exAmt = sc.nextDouble();
                                    if (exAmt <= 0) {
                                        System.out.println(" Expense must be > 0.");
                                        break;
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid amount.");
                                    sc.nextLine();
                                    break;
                                }
                                sc.nextLine();
                                System.out.print("Enter description: ");
                                String exDesc = sc.nextLine();
                                manager.addExpense(year, month, exAmt, exDesc);
                                System.out.println("Expense added!");
                                break;

                            case 3:
                                monthRecord.printHistory();
                                break;

                            case 4:
                                monthRecord.closeMonth();
                                System.out.println("Month closed! New expenses cannot be added to this month");
                                exitMonth = true;
                                break;

                            case 5:
                                System.out.println("Exiting month without closing...");
                                exitMonth = true;
                                break;

                            default:
                                System.out.println(" Invalid choice!");
                        }
                    }
                    break;

                case 2:
                    manager.viewAllMonths();
                    break;

                case 3:
                    System.out.println("Current balance: " + manager.getCurrentBalance());
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    manager.saveToFile();
                    sc.close();
                    return;

                default:
                    System.out.println(" Invalid choice!");
            }
        }
    }
}
