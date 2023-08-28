package ua.com.alevel.facade;

import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.web.dto.request.CardRequestDto;
import ua.com.alevel.web.dto.response.CardResponseDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface CardFacade extends BaseFacade<CardRequestDto, CardResponseDto> {

    CardResponseDto create(String ownerToken) throws IOException, URISyntaxException, EntityExistException;

    List<CardResponseDto> findCardsByUserToken(String ownerToken) throws IOException, URISyntaxException;

    CardResponseDto findCardById(String actualAuthToken, Long cardId) throws IOException, URISyntaxException, AccessException;

    List<CardResponseDto> findCardsByUserId(String actualAuthToken, Long userId) throws IOException, URISyntaxException, AccessException, EntityNotFoundException;
}
