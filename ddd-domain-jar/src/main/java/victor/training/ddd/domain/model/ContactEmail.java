package victor.training.ddd.domain.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import victor.training.ddd.domain.model.ContactEmail.Type;

//@Entity
@Embeddable
public class ContactEmail {
	enum Type {
		WORK, HOME
	}
//	@Id
//	private Integer id;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Type type;
	
}