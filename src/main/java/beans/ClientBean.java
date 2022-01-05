package beans;
import data.Client;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless(name="clientBean")
public class ClientBean implements ClientBeanInterface{

    @PersistenceContext(name="Client")
    EntityManager em;

    @Override
    public Client addClient(String name, int manager) {
        Client newClient = new Client(name, manager);
        em.persist(newClient);
        return newClient;
    }

    @Override
    public List<Client> listClients() {
        Query q = em.createQuery("SELECT c FROM Client c");
        List<Client> clients = q.getResultList();
        return clients;
    }

    @Override
    public float getCredit(int client_id) {
        Query q = em.createQuery("SELECT c.credit FROM Client c WHERE c.id = :p");
        q.setParameter("p", client_id);
        return (float) q.getSingleResult();
    }

    @Override
    public float getPayments(int client_id) {
        Query q = em.createQuery("SELECT c.payments FROM Client c WHERE c.id = :p");
        q.setParameter("p", client_id);
        return (float) q.getSingleResult();
    }

    @Override
    public float getBalance(int client_id) {
        Query q = em.createQuery("SELECT c.balance FROM Client c WHERE c.id = :p");
        q.setParameter("p", client_id);
        return (float) q.getSingleResult();
    }
}
