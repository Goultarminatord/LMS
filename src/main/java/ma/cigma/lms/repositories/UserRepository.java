package ma.cigma.lms.repositories;

import ma.cigma.lms.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    public User findByEmailIgnoreCase(String email);
}
