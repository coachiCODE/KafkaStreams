package beans;

import javax.ejb.Local;

@Local
public interface DataBeanInterface {
    float getAllCredits();
    float getAllPayments();
    float getAllBalance();
}
