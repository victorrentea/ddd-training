package victor.training.ddd.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import victor.training.ddd.facade.UserFacade;
import victor.training.ddd.facade.dto.LoggedInUserDto;
import victor.training.ddd.facade.dto.UserDto;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserFacade userFacade;

	@PostMapping
	public void createUser(@RequestBody UserDto form) {
		userFacade.create(form);
	}

	@GetMapping("current")
	public LoggedInUserDto getCurrentUser() {
		return userFacade.getLoggedInUser();
	}

}
