package user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.repository.UserEntity;
import user.repository.UserRepository;
import user.service.dto.UserDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(final UserRepository userRepository, final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void saveOrUpdate(UserDto user) {
        validate(user);

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        if (userEntity.getRegistrationDate() == null) {
            userEntity.setRegistrationDate(LocalDate.now());
        }
        userRepository.save(userEntity);
    }

    public Set<UserDto> findAll() {
        Set<UserDto> result = new HashSet<>();
        userRepository.findAll().forEach(userEntity -> result.add(modelMapper.map(userEntity, UserDto.class)));
        return result;
    }

    public void removeUser(String id) {
        if (userRepository.count() > 1) {
            userRepository.delete(Long.parseLong(id));
        }
    }

    private void validate(UserDto user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDto>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            throw new IllegalArgumentException("User was not validated properly");
        }
    }
}
