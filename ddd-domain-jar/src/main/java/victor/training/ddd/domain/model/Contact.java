package victor.training.ddd.domain.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;

@Entity
public class Contact {
	@Id
	@GeneratedValue
	private Integer id;
	
//	@OneToMany
//	@JoinColumn(name = "CONTACT_ID")
	@Embedded
	@ElementCollection
	List<ContactEmail> emails;
	
}