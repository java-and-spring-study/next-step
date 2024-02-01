package org.chapter01.tdd_account;

public class Account {
	private int balance;

	protected Account() {
	}

	public Account(int i) {
		this.balance = i;
	}

	public int getBalance() {
		return this.balance;
	}

	public void deposit(int money) {
		this.balance += money;
	}

	public void withdraw(int money) {
		this.balance -= money;
	}
}
