package victor.training.ddd.facade;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import victor.training.ddd.MyRequestContext;
import victor.training.ddd.ReglissException;
import victor.training.ddd.ReglissException.ErrorCode;
import victor.training.ddd.client.refog.RefogClient;
import victor.training.ddd.domain.model.Language;
import victor.training.ddd.domain.model.User;
import victor.training.ddd.domain.model.User.Status;
import victor.training.ddd.domain.model.UserSearchCriteria;
import victor.training.ddd.facade.dto.LoggedInUserDto;
import victor.training.ddd.facade.dto.UserDto;
import victor.training.ddd.facade.mapper.UserMapper;
import victor.training.ddd.repo.UserRepository;
import victor.training.ddd.spring.Facade;

@Facade
public class UserFacade {
	
	@Autowired
	private MyRequestContext requestContext;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper userMapper;
	

	@Autowired
	private RefogClient refogClient;

	@Autowired
	private UserRepository userRepo;

	private static final String SYS_USERNAME = "sys";
	
    public LoggedInUserDto getLoggedInUser() {
    	User user = userRepo.findByUsername(requestContext.getUsername());
        LoggedInUserDto dto = new LoggedInUserDto();
        dto.id = user.getId();
		dto.firstName = user.getFirstName();
        dto.lastName = user.getLastName();
        dto.username = user.getUsername();
        dto.email = "<NOT IMPLEMENTED>";
        dto.language = user.getLanguage().name();
        dto.fullName = user.getFullName();
        dto.profile = user.getProfile();
        return dto;
    }
    
    public List<UserDto> search(UserSearchCriteria criteria) {
    	return repository.search(criteria).stream()
    			.filter(user -> !SYS_USERNAME.equals(user.getUsername()))
    			.map(userMapper::toDto)
    			.collect(toList());
    }

    @CacheEvict(value = "reference", allEntries = true)
	public void create(UserDto dto) {
		if(repository.countByUsername(dto.username, -1L) > 0){
			throw new ReglissException(ErrorCode.CREATE_USER_UID_ALREADY_USED);
		}
		User user = userMapper.fromDtoForCreate(dto);
		
		repository.save(user);
	}

    @CacheEvict(value = "reference", allEntries = true)
	public void update(UserDto dto) {
		User userInDb = repository.getExactlyOne(dto.id);
		User oldUser = new User(userInDb);
		User newUser = userMapper.toEntity(userInDb, dto);
		repository.save(newUser);
	}
	
	public UserDto searchRefogForCreate(String username) {
		if (repository.countByUsername(username, -1L) != 0) {
			throw new ReglissException(ErrorCode.CREATE_USER_UID_ALREADY_USED);
		}
		User user = refogClient.getExactlyOne(username);
		user.setStatus(Status.ACTIVE);
		return userMapper.toDto(user);
	}

	@CacheEvict(value = "reference", allEntries = true)
	public void saveLanguage(Language language) {
		User user = repository.findByUsername(requestContext.getUsername());
		user.setLanguage(language);
	}


	public UserDto getForUpdate(Long id) {
		User user = repository.getExactlyOne(id);
        //checkIsEditableByLoggedUser(user);
		return userMapper.toDto(user);
	}
	

}
