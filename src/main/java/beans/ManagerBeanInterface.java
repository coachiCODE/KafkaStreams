package beans;

import data.Manager;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ManagerBeanInterface {
    Manager addManager(String name, String password);
    List<Manager> listManagers();
}
