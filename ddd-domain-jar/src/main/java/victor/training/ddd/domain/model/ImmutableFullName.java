package victor.training.ddd.domain.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import victor.training.ddd.play.Validated;

@interface DDD {
	@interface ValueObject {
		
	}
}

@DDD.ValueObject
public class ImmutableFullName implements Validated {
	private String firstName;
	private String lastName;
	
	private ImmutableFullName() {
	}
	public ImmutableFullName(String firstName, String lastName) {
		Assert.hasText(firstName);
		Assert.hasText(lastName);
		this.firstName = firstName;
		this.lastName = lastName;
		isValid();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public ImmutableFullName withLastName(String newLastName) {
		return new ImmutableFullName(firstName, newLastName);
	}
	public int hashCode() {
		return new HashCodeBuilder()
			.append(firstName)
			.append(lastName)
			.toHashCode();
	}
	public boolean equals(Object obj) {
		if (!(obj instanceof ImmutableFullName)) 
			return false;
		ImmutableFullName other = (ImmutableFullName) obj;
		return new EqualsBuilder()
				.append(firstName, other.firstName)
				.append(lastName, other.lastName)
				.isEquals();
	}
	
}

class ImmutableBuilder {
	private String firstName;
	private String lastName;
	public ImmutableBuilder() {
	}
	
	public ImmutableBuilder(ImmutableFullName old) {
		firstName = old.getFirstName();
		lastName = old.getLastName();
	}
	
	public ImmutableBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public ImmutableFullName build() {
		return new ImmutableFullName(firstName, lastName);
	}
	
}

class CodClient {
	
	public static void main(String[] args) {
		ImmutableFullName immutable = new ImmutableFullName("Jane", "Doe");
		ImmutableFullName maritata = immutable.withLastName("Constantinescu");
		maritata = new ImmutableFullName(immutable.getFirstName(), "Constantinescu");
		maritata = new ImmutableBuilder(immutable).withLastName("Constantinescu").build();
	}
}



