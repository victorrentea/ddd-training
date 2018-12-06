package victor.training.ddd.play;

public interface Validated {
	
	default void isValid() {
//		ValidatorFactory.instance().getValidator().validate(this);
	}
 // WEIRD!!
}

//class ValidatorFactory {
//	
//}
