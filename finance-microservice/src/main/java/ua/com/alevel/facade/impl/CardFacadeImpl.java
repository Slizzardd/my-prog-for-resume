package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.CardFacade;
import ua.com.alevel.persistence.entity.Card;
import ua.com.alevel.service.CardService;
import ua.com.alevel.util.GenerateDataCard;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.response.CardResponseDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardFacadeImpl implements CardFacade {

    private final CardService cardService;

    public CardFacadeImpl(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public CardResponseDto create(String actualAuthToken) throws IOException, URISyntaxException, EntityExistException {
        UserRequestDto user = cardService.apiGetUserByToken(actualAuthToken);
        Card card = new Card();
        card.setNumber(GenerateDataCard.generateCardNumber());
        card.setCvv(GenerateDataCard.generateCardCvv());
        card.setPinCode(GenerateDataCard.generateCardPin());
        card.setOwnerId(user.getId().toString());
        return new CardResponseDto(cardService.create(card), generateFullNameOwner(user));
    }

    @Override
    public List<CardResponseDto> findCardsByUserToken(String actualAuthToken) throws IOException, URISyntaxException {
        UserRequestDto user = cardService.apiGetUserByToken(actualAuthToken);

        List<Card> cards = cardService.findCardsByUserId(user, user.getId());
        if (!cards.isEmpty()) {
            return cards.stream()
                    .map(card -> new CardResponseDto(card, generateFullNameOwner(user)))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    @Override
    public CardResponseDto findCardById(String actualAuthToken, Long cardId) throws IOException, URISyntaxException, AccessException {
        UserRequestDto user = cardService.apiGetUserByToken(actualAuthToken);
        return new CardResponseDto(cardService.findById(user, cardId), generateFullNameOwner(user));
    }

    @Override
    public List<CardResponseDto> findCardsByUserId(String actualAuthToken, Long userId) throws IOException, URISyntaxException, AccessException, EntityNotFoundException {
        UserRequestDto actualUser = cardService.apiGetUserByToken(actualAuthToken);

        UserRequestDto targetUser = cardService.apiGetUserById(actualAuthToken, userId);

        List<Card> cards = cardService.findCardsByUserId(actualUser, userId);
        if (!cards.isEmpty()) {
            return cards.stream()
                    .map(card -> new CardResponseDto(card, generateFullNameOwner(targetUser)))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }


    private String generateFullNameOwner(UserRequestDto user) {
        return user.getFirstName() + " " + user.getLastName();
    }

}
