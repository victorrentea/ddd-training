package victor.training.ddd.spring.repo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class EntityRepositoryImpl<T extends Entity<ID>, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements EntityRepository<T, ID> {

    protected EntityManager entityManager;
    
    public EntityRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager ;
    }

    @Override
    public T getReference(ID id) {
        return entityManager.getReference(getDomainClass(), id);
    }
    

	@Override
	public T getExactlyOne(ID id) {
		T entity = findOne(id);
		if (entity == null) {
			throw new EntityNotFoundException("No " + getDomainClass().getSimpleName() + " with id " + id);
		}
		return entity;
	}
	
}
