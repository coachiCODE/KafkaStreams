package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="clients")
public class Client {

    @Id
    @GeneratedValue
    //TODO: tirar o auto generate da BD
    private int id;
    private String name;
    private float credit;
    private float payments;
    private float balance;
    private float lastMonthCredit;
    private float lastMonthsPayments;
    private int manager_id;

    public Client() {
    }

    public Client(String name, int manager_id) {
        this.name = name;
        this.credit = 0;
        this.payments = 0;
        this.balance = 0;
        this.lastMonthCredit = 0;
        this.lastMonthsPayments = 0;
        this.manager_id = manager_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public float getPayments() {
        return payments;
    }

    public void setPayments(float payments) {
        this.payments = payments;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getLastMonthCredit() {
        return lastMonthCredit;
    }

    public void setLastMonthCredit(float lastMonthCredit) {
        this.lastMonthCredit = lastMonthCredit;
    }

    public float getLastMonthsPayments() {
        return lastMonthsPayments;
    }

    public void setLastMonthsPayments(float lastMonthsPayments) {
        this.lastMonthsPayments = lastMonthsPayments;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", credit=" + credit +
                ", payments=" + payments +
                ", manager_id=" + manager_id +
                '}';
    }
}
