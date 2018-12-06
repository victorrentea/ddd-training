package victor.training.ddd.facade.dto;

import victor.training.ddd.domain.model.Profile;

public class LoggedInUserDto  {

    public long id;
    public String firstName;
    public String lastName;
    public String username;
    public String email;
    public String language;
    public String fullName;
    public Profile profile;
}
