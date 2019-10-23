package user.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.repository.UserEntity;
import user.repository.UserRepository;
import user.service.dto.UserDto;

import java.util.HashSet;
import java.util.Set;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void addUser(UserDto user) {
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.save(userEntity);
    }

    public Set<UserDto> findAll() {
        Set<UserDto> result = new HashSet<>();
        userRepository.findAll().forEach(userEntity -> result.add(modelMapper.map(userEntity, UserDto.class)));
        return result;
    }

    public void removeUser(Long id) {
        if (userRepository.count() > 1) {
            userRepository.delete(id);
        }
    }
}
