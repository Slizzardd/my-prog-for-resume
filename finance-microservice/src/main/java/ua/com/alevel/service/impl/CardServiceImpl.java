package ua.com.alevel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.Card;
import ua.com.alevel.persistence.repository.CardRepository;
import ua.com.alevel.service.CardService;
import ua.com.alevel.web.dto.request.UserRequestDto;
import ua.com.alevel.web.dto.request.type.Role;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {

    private static final String API_GET_CURR_USER = "http://localhost:8082/api/user/getUserByToken";

    private final ObjectMapper objectMapper;
    private final CardRepository cardRepository;

    public CardServiceImpl(ObjectMapper objectMapper, CardRepository cardRepository) {
        this.objectMapper = objectMapper;
        this.cardRepository = cardRepository;
    }

    @Override
    public Card create(Card card) throws IOException, URISyntaxException, EntityExistException {
        return cardRepository.save(card);
    }

    @Override
    public Card update(Card card, String actualAuthToken) throws IOException, URISyntaxException {
        return null;
    }

    @Override
    public void delete(Long id, String actualAuthToken) throws EntityNotFoundException {

    }

    @Override
    public List<Card> findCardsByUserId(Long userId) throws IOException, URISyntaxException {
        return cardRepository.findCardsByOwnerId(userId.toString());
    }

    @Override
    public List<Card> findAll(String actualAuthToken) throws AccessException {
        return null;
    }

    @Override
    public Card findById(UserRequestDto user, Long id) throws IOException, URISyntaxException {
        Card card = cardRepository.findById(id).orElse(null);
        checkExist(id);

        if (user.getId().toString().equals(Objects.requireNonNull(card).getOwnerId()) ||
                user.getRole() == Role.ADMIN){
            return card;
        }else {
            throw new AccessException("you not have permission for this data");
        }
    }

    @Override
    public Boolean numberCardExistException(String number) {
        return null;
    }

    @Override
    public UserRequestDto getUserByAPI(String actualAuthToken) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpUriRequest httpget = RequestBuilder.get()
                    .setUri(new URI(API_GET_CURR_USER))
                    .setHeader("Authorization", "Bearer " + actualAuthToken)
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(httpget)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                    return objectMapper.readValue(response.getEntity().getContent(), UserRequestDto.class);
                } else {
                    return null;
                }
            }
        } catch (IOException | URISyntaxException e) {
            return null;
        }
    }

    private void checkExist(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new EntityNotFoundException("this entity is not found");
        }
    }
}
