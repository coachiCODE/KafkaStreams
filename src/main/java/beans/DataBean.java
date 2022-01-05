package beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name="dataBean")
public class DataBean implements DataBeanInterface{

    @PersistenceContext(name="Client")
    EntityManager em;

    @Override
    public float getAllCredits() {
        Query q = em.createQuery("SELECT d.allCredits FROM Data d");
        return (float) q.getSingleResult();
    }

    @Override
    public float getAllPayments() {
        Query q = em.createQuery("SELECT d.allPayments FROM Data d");
        return (float) q.getSingleResult();
    }

    @Override
    public float getAllBalance() {
        Query q = em.createQuery("SELECT d.allBalance FROM Data d");
        return (float) q.getSingleResult();
    }
}
