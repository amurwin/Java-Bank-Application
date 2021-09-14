import java.util.*;
import java.lang.*;

public class Account {
    private String name;
    private int accountNumber;
    private double balance;
    private LoginInfo login;
    private static int numberOfAccounts = 1;

    public Account(String name, double balance, LoginInfo login) {
        this.name = name;
        this.balance = balance;
        this.login = login;
        this.accountNumber = numberOfAccounts;
        numberOfAccounts++;
    }
    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public void transfer(Account account, double amount) {
        this.balance -= amount;
        account.deposit(amount);
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public LoginInfo getLogin() {
        return login;
    }

    public String toString() {
        return "Name: " + name + "\nAccount Number: " + accountNumber + "\nBalance: " + balance;
    }
}
