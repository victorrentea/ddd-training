package victor.training.ddd.repo;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import victor.training.ddd.domain.model.User;
import victor.training.ddd.spring.repo.EntityRepository;

@Repository
public interface UserRepository extends EntityRepository<User, Long>, UserRepositoryCustom {
    
    void deleteByUsername(String username);

    User findByUsername(String username);
    
    @Query("SELECT u FROM User u WHERE u.username = ?1 AND u.status='ACTIVE'")
    User findActiveByUsername(String username);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.username = ?1 AND u.id != ?2")
    int countByUsername(String username, Long exceptId);
    
}
