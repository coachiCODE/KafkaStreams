package data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="data")
public class Data {
    @Id
    @GeneratedValue
    private int id;
    private float allCredits;
    private float allPayments;
    private float allBalance;

    public Data() {
    }

    public Data(float allCredits, float allPayments, float allBalance) {
        this.allCredits = allCredits;
        this.allPayments = allPayments;
        this.allBalance = allBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAllCredits() {
        return allCredits;
    }

    public void setAllCredits(float allCredits) {
        this.allCredits = allCredits;
    }

    public float getAllPayments() {
        return allPayments;
    }

    public void setAllPayments(float allPayments) {
        this.allPayments = allPayments;
    }

    public float getAllBalance() {
        return allBalance;
    }

    public void setAllBalance(float allBalance) {
        this.allBalance = allBalance;
    }
}
