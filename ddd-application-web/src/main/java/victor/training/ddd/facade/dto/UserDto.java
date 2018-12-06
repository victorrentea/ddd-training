package victor.training.ddd.facade.dto;

import victor.training.ddd.domain.model.Language;
import victor.training.ddd.domain.model.Profile;

public class UserDto {
	
	public Long id;
    public String username; // UID
    public String firstName;
    public String lastName; 
    public boolean active;
    public Language language;
    public Long country;
	public String pole;
	public String entity;
	public String businessLine;
	public String email;
	public Profile profile; 

	
	
	
}
