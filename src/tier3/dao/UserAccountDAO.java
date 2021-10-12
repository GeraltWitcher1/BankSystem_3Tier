package tier3.dao;


import model.User;

public interface UserAccountDAO {
    boolean create(User user);
    User read(String cpr);
    boolean update(User user);
    boolean delete(String cpr);
}
