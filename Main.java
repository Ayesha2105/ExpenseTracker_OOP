import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        while (true) {
            System.out.println("\n===== EXPENSE TRACKER =====");
            System.out.println("1. Open Month");
            System.out.println("2. View All Months");
            System.out.println("3. View Current Balance");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            if (choice == 4) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    System.out.print("Enter month (1-12): ");
                    int month = sc.nextInt();

                    MonthlyRecord monthRecord = manager.getMonthlyRecord(year, month);

                    boolean closeMonth = false;
                    while (!closeMonth) {
                        System.out.println("\n===== " + month + "/" + year + " =====");
                        System.out.println("1. Add Income");
                        System.out.println("2. Add Expense");
                        System.out.println("3. View Month History");
                        System.out.println("4. Close Month");
                        System.out.print("Enter choice: ");
                        int mChoice = sc.nextInt();
                        sc.nextLine(); // consume newline

                        switch (mChoice) {
                            case 1:
                                if (monthRecord.isClosed()) {
                                    System.out.println("Month is closed. Cannot add income.");
                                    break;
                                }
                                System.out.print("Enter income amount: ");
                                double inAmount = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Enter description: ");
                                String inDesc = sc.nextLine();
                                manager.addIncome(year, month, inAmount, inDesc);
                                System.out.println("Income added!");
                                break;

                            case 2:
                                if (monthRecord.isClosed()) {
                                    System.out.println("Month is closed. Cannot add expense.");
                                    break;
                                }
                                System.out.print("Enter expense amount: ");
                                double exAmount = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Enter description: ");
                                String exDesc = sc.nextLine();
                                if (exAmount > manager.getCurrentBalance()) {
                                    System.out.println("Insufficient balance!");
                                } else {
                                    manager.addExpense(year, month, exAmount, exDesc);
                                    System.out.println("Expense added!");
                                }
                                break;

                            case 3:
                                monthRecord.printHistory();
                                break;

                            case 4:
                                monthRecord.closeMonth();
                                System.out.println("Month " + month + "/" + year + " is now closed!");
                                closeMonth = true;
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                    break;

                case 2:
                    manager.viewAllMonths();
                    break;

                case 3:
                    System.out.println("Current balance: " + manager.getCurrentBalance());
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
