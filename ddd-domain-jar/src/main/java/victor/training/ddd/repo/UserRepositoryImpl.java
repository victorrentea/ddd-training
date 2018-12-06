package victor.training.ddd.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import victor.training.ddd.domain.model.User;
import victor.training.ddd.domain.model.UserSearchCriteria;
import victor.training.ddd.spring.ReflectionUtils;

public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepositoryCustom{

	@Autowired
	private UserRepository repository;
	
	@Override
	public List<User> search(UserSearchCriteria criteria) {
		ReflectionUtils.trimAllStringFields(criteria);
		String jpql = "SELECT distinct u FROM User u WHERE 1=1 ";
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(criteria.username)) {
			jpql += " AND u.username = :username ";
			params.put("username", criteria.username);
		}
		
		if (StringUtils.isNotBlank(criteria.firstName)) {
			jpql += " AND u.firstName = :firstName ";
			params.put("firstName", criteria.firstName);
		}
		
		if (StringUtils.isNotBlank(criteria.lastName)) {
			jpql += " AND u.lastName = :lastName ";
			params.put("lastName", criteria.lastName);
		}
		
		if (criteria.countryId != null) {
            jpql += " AND u.country.id = :countryId ";
            params.put("countryId", criteria.countryId);
        }
		
		jpql += " ORDER BY u.status, u.lastName, u.firstName ";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		for (String paramName : params.keySet()) {
			query.setParameter(paramName, params.get(paramName));
		}
		return query.getResultList();
	}

	@Override
	public User getOneByUsername(String username) {
		User user = repository.findActiveByUsername(username);
		if (user == null) {
			throw new IllegalArgumentException("Username not found in DB: " + username);
		}
		return user;
	}
}
