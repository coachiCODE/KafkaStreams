package beans;

import data.Currency;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CurrencyBeanInterface {
    void addCurrency(String name, float exchange);
    List<Currency> listCurrencies();
}
