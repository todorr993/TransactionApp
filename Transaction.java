package com.company;

public class Transaction {
    String receiver;
    double amount;
    double saldo;

    public Transaction (String receiver, double amount, double saldo){
        this.receiver=receiver;
        this.amount=amount;
        this.saldo=saldo;
    }//end contructor

    public double getAmount() {
        return amount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "receiver='" + receiver + '\'' +
                ", amount=" + amount +
                ", saldo=" + saldo +
                '}';
    }
}
