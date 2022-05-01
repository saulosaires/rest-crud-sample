package com.example.restcrudsample.service;

import com.example.restcrudsample.exception.BusinessException;
import com.example.restcrudsample.exception.user.UserNotFoundException;
import com.example.restcrudsample.model.User;
import com.example.restcrudsample.model.dto.UserDTO;
import com.example.restcrudsample.model.dto.UserPatchNameDTO;
import com.example.restcrudsample.model.mapper.UserMapper;
import com.example.restcrudsample.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void given_id_should_retreive_user() {

        given(userRepository.findById(any())).willReturn(Optional.of(new User()));

        User user = userService.retreive(1L);

        assertThat(user).isNotNull();

        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void given_invalid_id_should_throw_exception() {

        BusinessException thrown = Assertions.assertThrows(BusinessException.class, () -> userService.retreive(0L));

        Assertions.assertEquals("Id can not be less or equals to Zero", thrown.getMessage());

        verify(userRepository, times(0)).findById(any());

    }

    @Test
    public void given_UserDTO_should_create_user() {

        UserDTO userDTO = userDTOBuilder();
        User user = UserMapper.INSTANCE.dtoToUser(userDTO);

        given(userRepository.save(any(User.class))).willReturn(user);

        User userCreated = userService.create(userDTO);

        assertThat(userCreated).isNotNull();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void given_null_UserDTO_should_throw_exception() {

        BusinessException thrown = Assertions.assertThrows(BusinessException.class, () -> userService.create(null));

        Assertions.assertEquals("User can not be Empty", thrown.getMessage());

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void given_UserDTO_with_no_name_or_birth_date_should_throw_exception() {

        BusinessException thrownName = Assertions.assertThrows(BusinessException.class, () -> {
            UserDTO userDTO = userDTOBuilder();
            userDTO.setName(null);
            userService.create(userDTO);
        });

        Assertions.assertEquals("User Name can not be Empty", thrownName.getMessage());

        BusinessException thrownNoBirth = Assertions.assertThrows(BusinessException.class, () -> {
            UserDTO userDTO = userDTOBuilder();
            userDTO.setBirth(null);
            userService.create(userDTO);
        });

        Assertions.assertEquals("User Birth Date can not be Empty", thrownNoBirth.getMessage());


        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    public void given_id_and_UserDTO_should_update_user() {

        UserDTO userDTO = userDTOBuilder();
        User user = UserMapper.INSTANCE.dtoToUser(userDTO);
        Long id = 1L;

        given(userRepository.save(any(User.class))).willReturn(user);

        User userUpdated = userService.update(id, userDTO);

        assertThat(userUpdated).isNotNull();

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void given_invalid_id_should_not_update_user_and_throw_exception() {

        BusinessException thrownNullId = Assertions.assertThrows(BusinessException.class, () -> {
            UserDTO userDTO = userDTOBuilder();
            userService.update(null, userDTO);
        });
        Assertions.assertEquals("Id can not be Empty", thrownNullId.getMessage());

        BusinessException thrownZeroId = Assertions.assertThrows(BusinessException.class, () -> {
            UserDTO userDTO = userDTOBuilder();
            userService.update(0L, userDTO);
        });
        Assertions.assertEquals("Id can not be less or equals to Zero", thrownZeroId.getMessage());

        verify(userRepository, times(0)).save(any(User.class));

    }

    @Test
    public void given_id_and_UserPatchNameDTO_should_patch_user() {

        UserPatchNameDTO userPatchNameDTO = new UserPatchNameDTO();
        userPatchNameDTO.setName(UUID.randomUUID().toString());

        User user = new User();
        user.setName(UUID.randomUUID().toString());
        user.setBirth(LocalDate.now());
        user.setId(1L);

        given(userRepository.findById(any())).willReturn(Optional.of(user));
        given(userRepository.save(any(User.class))).willReturn(user);

        userService.patch(user.getId(), userPatchNameDTO);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void given_id_and_UserPatchNameDTO_with_no_name_should_throw_exception() {


        BusinessException thrownNullId = Assertions.assertThrows(BusinessException.class, () -> userService.patch(1L, new UserPatchNameDTO()));

        Assertions.assertEquals("User Name can not be Empty", thrownNullId.getMessage());

        verify(userRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any(User.class));

    }

    @Test
    public void given_id_should_delete_user() {

        given(userRepository.findById(any())).willReturn(Optional.of(new User()));

        userService.delete(1L);

        verify(userRepository, times(1)).findById(any());
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    public void given_invalid_id_delete_and_throw_exception() {

        given(userRepository.findById(any())).willReturn(Optional.empty());

        UserNotFoundException thrownNullId = Assertions.assertThrows(UserNotFoundException.class, () -> userService.delete(null));

        Assertions.assertEquals("Could not find user with id:null", thrownNullId.getMessage());

        verify(userRepository, times(0)).delete(any());
    }


    private UserDTO userDTOBuilder() {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(UUID.randomUUID().toString());
        userDTO.setBirth(LocalDate.now());
        return userDTO;
    }

}
