package victor.training.ddd.play;

import lombok.AllArgsConstructor;

public class Microtypes {
	void metodaMea(OrderId id) {
		
	}
	
	void ometodaCuMulteIduriParametru(
			OrderId orderId,
			ProductId productId,
			OfferId offerId,
			UserId actorUserId
			) {
		
	}
	
	
	void altaMetoda() {
		int orderId = 2;
		int productId = 2;
		int offerId = 2;
		int actorUserId = 2;
		
		metodaMea(new OrderId(orderId));

		// XXX try to swap two lines below:
		ometodaCuMulteIduriParametru(
				new OrderId(orderId),
				new ProductId(productId), 
				new OfferId(offerId),
				new UserId(actorUserId));
	}
}


@AllArgsConstructor
class OrderId {
	Integer id;
}
@AllArgsConstructor
class ProductId {
	Integer id;
}
@AllArgsConstructor
class OfferId {
	Integer id;
}
@AllArgsConstructor
class UserId {
	Integer id;
}



