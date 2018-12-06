package victor.training.ddd.play;

public class ExempluValueObjectBani {
	public static void main(String[] args) {
		new Credit(new Bani(10, Moneda.EUR), new Bani(10_000, Moneda.EUR));
		new Credit(null, new Bani(10000, Moneda.EUR));
		
		
		new Credit(new Bani(10, null), new Bani(10_000, Moneda.EUR));
	}
}

class Bani {
	private final Integer suma;
	private final Moneda moneda;
	
	public Bani(Integer suma, Moneda moneda) {
		if (suma == null || moneda  ==null ) {
			throw new IllegalArgumentException();
		}
		this.suma = suma;
		this.moneda = moneda;
	}
	public Integer getSuma() {
		return suma;
	}
	public Moneda getMoneda() {
		return moneda;
	}
}


class Credit {
	
	Bani avans;
	Bani credit;
	
	public Credit(Bani avans, Bani credit) {
		if (credit == null) {
			throw new IllegalArgumentException();
		}
		this.avans = avans;
		this.credit = credit;
	}
	
	
	
	
//	User createadBy;
//	LocalDate createdWhen;
//	User lastModifiedBy;
//	LocalDate lastModifiedWhen;
}

enum Moneda {
	RON, EUR, USD
}