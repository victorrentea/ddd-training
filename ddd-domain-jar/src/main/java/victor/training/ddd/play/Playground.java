package victor.training.ddd.play;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import victor.training.ddd.domain.model.ImmutableFullName;
import victor.training.ddd.domain.model.TataLui;

@Service
@Transactional
@Validated
public class Playground {

	@Autowired
	private EntityManager em;

	@Autowired
	private OrderXRepository orderXRepository;
	
	public void firstTransaction() {
		TataLui tataLui = new TataLui();
		tataLui.setFullName(new ImmutableFullName("Jane", "Doe"));
		em.persist(tataLui);
//		Order22 o = new Order22();
//		o.getLines().add(new OrderLine("a"));
//		o.getLines().add(new OrderLine("b"));
//		em.persist(o);
		
		System.out.println(orderXRepository.findAll());
		orderXRepository.findByTotalPrice(33).forEach(System.out::println);
		
	}
	
	@Autowired
	private Validator validator;
	
	public void secondTransaction(Customer customer) {
		
		TataLui tataLui = em.find(TataLui.class, 1);
		System.out.println("numele lui tataLui: " + tataLui.getFullName().getFirstName());
		
		customer.setId(new CustomerId(ElementType.PRODUCT));
		
		System.out.println("Acum incerc sa-l persist");
//		customer.setFirstName("1234567890123");
		em.persist(customer);
		
		Set<ConstraintViolation<Customer>> errors = validator
				.validate(customer, PtUseCaseulDeCreate.class);
		if (!errors.isEmpty()) {
			throw new IllegalArgumentException("Customer invalid" + errors ); 
		}
		
		
		
//		customer.setFirstName(null);
		System.out.println("are oare id-ul setat ?" + customer.getId());
		
		System.out.println("Aici trimit un SMS cu first name-ul "
				+ "customerului: " + customer.getFirstName());
		
		
	}  // Hibernate valideaza automat orice din javax.validation la comiterea tranzactiei
	
	
	
}
enum ElementType {
	PRODUCT("P");
	public final String dbCode;
	private ElementType(String dbCode) {
		this.dbCode = dbCode;
	}
	public static ElementType fromCode(String code) {
		return Stream.of(values()).filter(e -> e.dbCode.equals(code)).findFirst().get();
	}
}


interface PtUseCaseulDeCreate {}

@Entity
class Customer {
	@EmbeddedId
	private CustomerId id;
	
	@NotNull(groups = PtUseCaseulDeCreate.class)
	@Column(length = 10)
	@Size(max = 10)
	private String firstName;
	
	private Customer() {
	}
	public Customer(String firstName) {
		setFirstName(firstName);
	}
	
	@AssertTrue
	public boolean isValidAddress() {
//		if (status!=DRAFT) {
//			return address != null;
//		}
		return firstName != null;
	}
	
		
	public void setFirstName(String firstName) {
		if (StringUtils.isBlank(firstName)) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public CustomerId getId() {
		return id;
	}
	
	public void setId(CustomerId id) {
		this.id = id;
	}
}


@Entity
class Order22 {
	@Id
	@GeneratedValue
	Integer id;
	
	String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("order")
	List<OrderLine> lines = new ArrayList<>();
	public List<OrderLine> getLines() {
		return lines;
	}
}

@Entity
class OrderLine {
	@Embeddable
	static class OrderLineId implements Serializable {
		Integer parentId;
		Integer order;
	}
	@EmbeddedId
	OrderLineId id;
	
	private OrderLine() {
	}
	
	public OrderLine(String name) {
		this.name = name;
	}

	private String name;
	
	
}






//



@Embeddable
class CustomerId implements Serializable{
	private String id;

	private CustomerId() {
	}
	public CustomerId(String id) {
		this.id = id;
	}
	
	public CustomerId(ElementType elementType) {
		this.id = "APM-"+elementType.dbCode +
				"-"+
				LocalDate.now().format(DateTimeFormatter.ISO_DATE)
				+"-"+((new Random()).nextInt(999999)+100000);
	}
	public ElementType getElementType() {
		return ElementType.valueOf(id.split("-")[1]);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}