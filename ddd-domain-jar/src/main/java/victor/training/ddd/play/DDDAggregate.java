package victor.training.ddd.play;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DDDAggregate {
	
	@Autowired
	private EntityManager em;
	
	public void insertInitial() {
		OrderX order = new OrderX();
		order.addLine(new OrderLineX(13));
		order.addLine(new OrderLineX(20));
		em.persist(order);
		System.out.println("Am comis in baza datele initiale");
	}
	public OrderX tranzactiaCareAduceDinBackend() {
		return em.find(OrderX.class, 1L);		
	}
	public void tranzactiaCareSalveazaInBackend(OrderX order) {
		em.merge(order);		
	}
	
}

interface OrderXRepository extends Repository<OrderX, Long>{
	// extinzi Repository care nu are nici o metoda
	// copiezi de prin JpaRepository ce metoda aveai nevoie
	// si Spring Data iti genereza implementarea potrivit

	Stream<OrderX> findByTotalPrice(int target);

	List<OrderX> findAll();
	
}

// CERINTA: trebuie ca OrderX.totalPrice sa fie mereu suma OrderX.lines[].price
@Entity
class OrderX {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	public long version;
	private int totalPrice = 0;
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn
	List<OrderLineX> lines = new ArrayList<>();

	public List<OrderLineX> getLines() {
		return unmodifiableList(lines);
	}

	public void addLine(OrderLineX orderLine) {
		lines.add(orderLine);
		totalPrice += orderLine.getPrice();
	} // + remove

	public void setLinePrice(int lineIndex, int newPrice) {
		OrderLineX line = lines.get(lineIndex);
		totalPrice += (newPrice - line.getPrice());
		line.setPrice(newPrice);
	}
	
	public void setLineComment(int lineIndex, String newComment) {
		lines.get(lineIndex).setComment(newComment);
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
}
 
@Entity
class OrderLineX {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private long version;
	private int price;
	private String newComment;
	
	

	private OrderLineX() {
	}
	public void setComment(String newComment) {
		this.newComment = newComment;
		 // TODO
	}
	public OrderLineX(int price) {
		this.price = price;
	}
	
	void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}

}

class CodClientRau {
	public static void main(String[] args) {
		OrderX order = new OrderX();

		// problema1
		// am expus direct o lista modificabila, am facut add pe ea
		// si am rupt lista de consistenta
		// order.getLines().add(new OrderLineX(13));
		order.addLine(new OrderLineX(13));

		System.out.println(order.getTotalPrice());

//		order.getLines().get(0) = 15;
		order.setLinePrice(0, 15);
		System.out.println(order.getTotalPrice());
	}
}
