package victor.training.ddd.domain.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TataLui {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Embedded
	private ImmutableFullName fullName;
	
	public ImmutableFullName getFullName() {
		return fullName;
	}
	public void setFullName(ImmutableFullName fullName) {
		this.fullName = fullName;
	}
}