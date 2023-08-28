package ua.com.alevel.service;

import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.User;

import java.util.List;

public interface UserService extends BaseService<User> {

    User create(User user) throws EntityExistException;

    User update(User user, String actualAuthToken) throws EntityExistException, AccessException;

    void delete(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException;

    User findById(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException;

    User findByEmail(String email) throws AccessException;

    User findByToken(String actualAuthToken);

    List<User> findAll(String actualAuthToken) throws AccessException;

    Long getNumbersOfUsers();

}
