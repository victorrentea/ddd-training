package victor.training.ddd.repo;

import java.util.List;

import victor.training.ddd.domain.model.User;
import victor.training.ddd.domain.model.UserSearchCriteria;

public interface UserRepositoryCustom {
	List<User> search(UserSearchCriteria criteria);
	User getOneByUsername(String username);
}
