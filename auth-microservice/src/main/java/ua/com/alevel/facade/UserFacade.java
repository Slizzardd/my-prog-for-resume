package ua.com.alevel.facade;

import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.response.UserResponseDto;

import java.util.List;

public interface UserFacade extends BaseFacade<UserRequestDto, UserResponseDto> {

    UserResponseDto create(UserRequestDto userRequestDto) throws EntityExistException;

    UserResponseDto update(UserRequestDto userRequestDto) throws EntityExistException, AccessException;

    void delete(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException;

    UserResponseDto findById(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException;

    UserResponseDto findByEmail(String email) throws AccessException;

    UserResponseDto findByToken(String actualAuthToken);

    User apiFindUserByToken(String actualAuthToken);

    List<UserResponseDto> findAll(String actualAuthToken) throws AccessException;
}
