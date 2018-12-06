package victor.training.ddd.client.refog;

import lombok.Getter;
import lombok.Setter;

public class RefogUser {
	
	@Getter @Setter
    private String username;
	
	@Getter @Setter
    private String firstName;
	
	@Getter @Setter
    private String lastName;

	@Getter @Setter
    private String email;

	@Getter @Setter
    private String countryName;

	@Getter @Setter
    private String pole;

	@Getter @Setter
    private String entity;

	@Getter @Setter
    private String businessLine;

}
