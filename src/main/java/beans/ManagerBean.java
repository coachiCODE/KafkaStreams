package beans;
import data.Manager;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless(name="managerBean")
public class ManagerBean implements ManagerBeanInterface{

    @PersistenceContext(name="Manager")
    EntityManager em;

    @Override
    public Manager addManager(String name, String password) {
        Manager newManager = new Manager(name, password);
        em.persist(newManager);
        return newManager;
    }

    @Override
    public List<Manager> listManagers() {
        Query q = em.createQuery("SELECT m FROM Manager m");
        List<Manager> managers = q.getResultList();
        return managers;
    }
}
