package victor.training.ddd.spring.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T extends Entity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
    
    T getReference(ID id);

    T getExactlyOne(ID id);
}
