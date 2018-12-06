package victor.training.ddd.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
public class User implements victor.training.ddd.spring.repo.Entity<Long>{
	
	public enum Status {
		ACTIVE, INACTIVE
	}
	
	public enum LastConnectionType{
		FREE, SECURED
	}
	
	@Id
	@Getter
	@GeneratedValue
	private Long id;
	
	
	@Getter @Setter
	@Column(name = "USERNAME", length=6)
    private String username;
	 
	@Getter @Setter
    @Column(name = "FIRST_NAME")
    private String firstName;

	@Getter @Setter
    @Column(name = "LAST_NAME")
    private String lastName;

	@Getter @Setter
	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@Getter @Setter
	@Enumerated(EnumType.STRING)
    @Column(name = "USER_LANGUAGE")
    private Language language = Language.EN;

	@Getter @Setter
    @Column(name = "BUSINESS_LINE")
    private String businessLine;
	
	@Getter @Setter
	private Long countryId;
	
	@Getter @Setter
    @Column(name = "EMAIL")
    private String email;
	
	@Getter @Setter
	@Enumerated(EnumType.STRING)
    @Column(name = "PROFILE")
    private Profile profile;
	
	public User() {
	}
    
	public User(String username) {
		this.username = username;
	}

	public User(User other) {
		this.id = other.id;
		this.firstName = other.firstName;
		this.language = other.language;
		this.lastName = other.lastName;
		this.status = other.status;
		this.username = other.username;
		this.businessLine = other.businessLine;
		this.email = other.email;
		this.profile = other.profile;
	}

	public boolean isActive() {
    	return status == Status.ACTIVE;
    }
  
	public String getFullName() {
		return (firstName!=null?firstName:"") + " " + (lastName!=null?lastName:"");
	}
	
}