package victor.training.ddd.play;

import java.util.HashSet;
import java.util.Set;

public class CumPotiSaPierziUnElementIntruSet {

	
	public static void main(String[] args) {
		Set<Element> hashSet = new HashSet<>();
		Element e = new Element(14);
		hashSet.add(e);
		
		e.setValoare(15);
		
		System.out.println("Oare e pe care abia l-am pus"
				+ " mai e in set ? " + 
				hashSet.contains(e));
		
	}
}

class Element {
	private int valoare;

	public Element(int valoare) {
		this.valoare = valoare;
	}

	public int getValoare() {
		return valoare;
	}

	public void setValoare(int valoare) {
		this.valoare = valoare;
	}

	public int hashCode() {
		return valoare;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (valoare != other.valoare)
			return false;
		return true;
	}
	
	

	
}