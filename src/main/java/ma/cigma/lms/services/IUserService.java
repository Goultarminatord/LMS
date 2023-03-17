package ma.cigma.lms.services;

import ma.cigma.lms.models.User;

import java.util.List;

public interface IUserService {
    public User getOne(String email);
    List<User> getAllUsers();
    User saveUser(User user);
    void updateUser(Long id,User user);
    void deleteUser(Long id);
}
