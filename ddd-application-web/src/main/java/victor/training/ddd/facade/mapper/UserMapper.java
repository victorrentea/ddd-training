package victor.training.ddd.facade.mapper;

import org.springframework.stereotype.Service;

import victor.training.ddd.domain.model.User;
import victor.training.ddd.domain.model.User.Status;
import victor.training.ddd.facade.dto.UserDto;

@Service
public class UserMapper {
	
	public UserDto toDto(User user) {
		UserDto dto = new UserDto();
		dto.id = user.getId();
		dto.username = user.getUsername();
		dto.firstName = user.getFirstName();
		dto.lastName = user.getLastName();
		dto.active = User.Status.ACTIVE.equals(user.getStatus())? true : false;
		dto.language = user.getLanguage();
		dto.country = user.getCountryId();
		dto.businessLine = user.getBusinessLine();
		dto.email = user.getEmail();
		dto.profile = user.getProfile(); 
		return dto;
	}

	public User fromDtoForCreate(UserDto dto) {
		User user = new User();
		user.setUsername(dto.username);
		user.setFirstName(dto.firstName);
		user.setLastName(dto.lastName);
		user.setBusinessLine(dto.businessLine);
		user.setLanguage(dto.language);
		
		user.setCountryId(dto.country);
		
		user.setEmail(dto.email);
		user.setProfile(dto.profile);
		user.setBusinessLine(dto.businessLine);
		user.setEmail(dto.email);
		user.setLanguage(dto.language);
		user.setStatus(dto.active ? Status.ACTIVE : Status.INACTIVE);
		return user;
	}
	
	public User toEntity(User user, UserDto dto) {
		user.setLanguage(dto.language);
		user.setProfile(dto.profile);
		user.setStatus(dto.active ? Status.ACTIVE : Status.INACTIVE);
		return user;
	}


}
