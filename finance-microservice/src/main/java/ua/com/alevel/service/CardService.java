package ua.com.alevel.service;

import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.Card;
import ua.com.alevel.web.dto.request.UserRequestDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface CardService extends BaseService<Card> {

    Card create(Card card) throws IOException, URISyntaxException, EntityExistException;

    Card update(Card card, String actualAuthToken) throws IOException, URISyntaxException;

    void delete(Long id, String actualAuthToken) throws EntityNotFoundException;

    List<Card> findCardsByUserId(UserRequestDto actualUser, Long userId) throws IOException, URISyntaxException;

    List<Card> findAll(String actualAuthToken) throws AccessException;

    Card findById(UserRequestDto user, Long id) throws IOException, URISyntaxException;
    UserRequestDto apiGetUserByToken(String actualAuthToken);
    UserRequestDto apiGetUserById(String actualAuthToken, Long id);

    Boolean numberCardExistException(String number);
}
