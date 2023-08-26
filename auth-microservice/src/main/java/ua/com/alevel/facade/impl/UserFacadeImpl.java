package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.service.UserService;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.response.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) throws EntityExistException {
        User user = new User();
        setMainUserInformation(userRequestDto, user);
        return new UserResponseDto(userService.create(user));
    }

    @Override
    public UserResponseDto update(UserRequestDto userRequestDto) throws EntityExistException, AccessException {
        User user = userService.findByEmail(userRequestDto.getEmail());
        setMainUserInformation(userRequestDto, user);
        return new UserResponseDto(userService.update(user, userRequestDto.getActualAuthToken()));
    }

    @Override
    public void delete(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException {
        userService.delete(id, actualAuthToken);
    }

    @Override
    public UserResponseDto findById(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException {
        return new UserResponseDto(userService.findById(id, actualAuthToken));
    }

    @Override
    public UserResponseDto findByEmail(String email) throws AccessException {
        return new UserResponseDto(userService.findByEmail(email));
    }

    @Override
    public UserResponseDto findByToken(String actualAuthToken) {
        return new UserResponseDto(userService.findByToken(actualAuthToken));
    }

    @Override
    public User apiFindUserByToken(String actualAuthToken) {
        return userService.findByToken(actualAuthToken);
    }

    @Override
    public List<UserResponseDto> findAll(String actualAuthToken) throws AccessException {
        List<User> users = userService.findAll(actualAuthToken);
        List<UserResponseDto> userResponses = new ArrayList<>();
        for (User user : users) {
            userResponses.add(new UserResponseDto(user));
        }
        return userResponses;
    }

    private void setMainUserInformation(UserRequestDto userRequestDto, User user){
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPassword(userRequestDto.getPassword());
    }
}
