package victor.training.ddd.spring.repo;

import java.io.Serializable;


public interface Entity<ID extends Serializable> {
	ID getId();

}