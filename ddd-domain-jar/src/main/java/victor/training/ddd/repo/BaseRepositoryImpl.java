package victor.training.ddd.repo;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import victor.training.ddd.spring.ReflectionUtils;

public abstract class BaseRepositoryImpl<T> {
    @Autowired
    protected EntityManager entityManager;
    
    protected final Class<?> entityClass = ReflectionUtils.getTypeArguments(BaseRepositoryImpl.class, getClass()).get(0);

    @SuppressWarnings("unchecked")
    protected List<T> queryForList(String jpql, Map<String, Object> params) {
        TypedQuery<?> query = entityManager.createQuery(jpql, entityClass);
        for (String param : params.keySet()) {
            query.setParameter(param, params.get(param));
        }
        return (List<T>) query.getResultList();
    }
    
    protected <U> U queryForObject(String jpql, Map<String, Object> params, Class<U> targetClass) {
        TypedQuery<U> query = entityManager.createQuery(jpql, targetClass);
        for (String param : params.keySet()) {
            query.setParameter(param, params.get(param));
        }
        return query.getSingleResult();
    }
}
