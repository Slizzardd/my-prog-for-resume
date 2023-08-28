package ua.com.alevel.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.User;
import ua.com.alevel.persistence.type.Role;
import ua.com.alevel.service.UserService;
import ua.com.alevel.persistence.repository.UserRepository;
import ua.com.alevel.util.JwtUtil;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User user) throws EntityExistException {

        checkIfUserExistsByEmail(user.getEmail());
        checkIfUserExistsByPhoneNumber(user.getPhoneNumber());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(User user, String actualAuthToken) throws EntityExistException, AccessException, EntityExistException {
        User actualUser = findByToken(actualAuthToken);

        checkExist(user.getId());
        if (isAdmin(actualUser.getRole()) ||
                Objects.equals(actualUser.getId(), user.getId())) {
            return userRepository.save(user);
        } else {
            throw new AccessException("you not have permission for this operation");
        }
    }

    @Override
    public void delete(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException {
        User actualUser = findByToken(actualAuthToken);

        checkExist(id);

        if (isAdmin(actualUser.getRole()) ||
                Objects.equals(actualUser.getId(), id)) {
            userRepository.deleteById(id);
        } else {
            throw new AccessException("you not have permission for this operation");
        }
    }

    @Override
    public User findById(Long id, String actualAuthToken) throws EntityNotFoundException, AccessException {
        User actualUser = findByToken(actualAuthToken);

        checkExist(id);

        if (isAdmin(actualUser.getRole()) ||
                Objects.equals(actualUser.getId(), id)) {
            return userRepository.findById(id).orElse(null);
        } else {
            throw new AccessException("you not have permission for this data");
        }
    }


    @Override
    public User findByEmail(String email) throws AccessException {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findByToken(String actualAuthToken) {
        String email = JwtUtil.extractUsername(actualAuthToken);
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAll(String actualAuthToken) throws AccessException {
        User actualUser = findByEmail(JwtUtil.extractUsername(actualAuthToken));

        if (isAdmin(actualUser.getRole())) {
            return userRepository.findAll();
        } else {
            throw new AccessException("you not have permission for this data");
        }
    }

    @Override
    public Long getNumbersOfUsers() {
        return userRepository.count();
    }

    private void checkIfUserExistsByEmail(String email) throws EntityExistException {
        if (userRepository.existsByEmail(email)) {
            throw new EntityExistException("a user with this email already exists");
        }
    }

    private void checkIfUserExistsByPhoneNumber(String phoneNumber) throws EntityExistException {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new EntityExistException("a user with this phone number already exists");
        }
    }

    private Boolean isAdmin(Role role){
        return role == Role.ADMIN;
    }

    private void checkExist(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("this entity is not found");
        }
    }
}
