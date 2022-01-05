package beans;

import data.Currency;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless(name="currencyBean")
public class CurrencyBean implements CurrencyBeanInterface{

    @PersistenceContext(name="Currency")
    EntityManager em;

    @Override
    public void addCurrency(String name, float exchange) {
        Currency newCurrency = new Currency(name, exchange);
        em.persist(newCurrency);
    }

    @Override
    public List<Currency> listCurrencies() {
        Query q = em.createQuery("SELECT c FROM Currency c");
        List<Currency> currencies = q.getResultList();
        return currencies;
    }
}
