package beans;

import data.Client;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ClientBeanInterface {
    Client addClient(String name, int manager);
    List<Client> listClients();
    float getCredit(int client_id);
    float getPayments(int client_id);
    float getBalance(int client_id);
}
