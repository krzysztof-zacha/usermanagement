package user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import user.common.RoleEnum;
import user.repository.UserEntity;
import user.repository.UserRepository;
import user.service.dto.UserDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void shouldSaveEntity() {
        // given
        UserEntity userEntity = createUserEntity(1L);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        UserService userService = new UserService(userRepository, new ModelMapper());

        UserDto userDto = new UserDto();
        userDto.setName("name");
        userDto.setUsername("username");
        // when

        userService.addUser(userDto);

        // then
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }


    @Test
    public void shouldDeleteEntity() {
        // given
        when(userRepository.count()).thenReturn(2L);
        doNothing().when(userRepository).delete(anyLong());

        UserService userService = new UserService(userRepository, new ModelMapper());
        // when
        userService.removeUser("1");

        // then
        verify(userRepository, times(1)).delete(1L);
    }

    @Test
    public void shouldNotDeleteEntity() {
        // given
        when(userRepository.count()).thenReturn(1L);
        doNothing().when(userRepository).delete(anyLong());
        UserService userService = new UserService(userRepository, new ModelMapper());
        // when

        userService.removeUser("1");

        // then
        verify(userRepository, times(0)).delete(1L);
    }

    @Test
    public void shouldFindAllUsers() {
        // given
        when(userRepository.findAll()).thenReturn(new HashSet<>(Arrays.asList(createUserEntity(1L), createUserEntity(2L))));
        UserService userService = new UserService(userRepository, new ModelMapper());

        // when
        Set<UserDto> founded = userService.findAll();

        // then
        UserDto userDto = founded.stream().findFirst().get();
        assertEquals(founded.size(), 2);
        assertNotNull(userDto.getId());
        assertEquals(userDto.getEmail(), "abc@gmail.com");
        assertEquals(userDto.getUsername(), "username");


    }

    private UserEntity createUserEntity(Long id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setEmail("abc@gmail.com");
        userEntity.setEnabled(true);
        userEntity.setUsername("username");
        userEntity.setRole(RoleEnum.ADMIN);
        userEntity.setSurname("surname");
        return userEntity;
    }

}