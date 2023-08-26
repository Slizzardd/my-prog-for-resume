package ua.com.alevel.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.facade.CardFacade;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/finance/card")
public class CardRestController {
    private final CardFacade cardFacade;

    public CardRestController(CardFacade cardFacade) {
        this.cardFacade = cardFacade;
    }

    @PostMapping("/createNewCard")
    public ResponseEntity<?> createNewCard(@RequestBody Map<String, String> storedAuthToken) {
        String ownerToken = getTokenByRequest(storedAuthToken);

        try {
            return ResponseEntity.ok(cardFacade.create(ownerToken));
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Invalid URI: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(503).body("Service Unavailable: " + e.getMessage());
        } catch (EntityExistException e){
            return ResponseEntity.status(409).body("Service Unavailable: " + e.getMessage());

        }
    }

    @GetMapping("/getCardsByUser")
    public ResponseEntity<?> getCardsByUser(@RequestHeader("Authorization") String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7); // Get token after "Bearer "
            try {
                return ResponseEntity.ok(cardFacade.findCardsByUserToken(token));
            } catch (IOException e) {
                return ResponseEntity.status(503).body("Service Unavailable: " + e.getMessage());
            } catch (URISyntaxException e) {
                return ResponseEntity.badRequest().body("Invalid URI: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/getCardById")
    public ResponseEntity<?> getCardById(@RequestBody Map<String, String> storedData) {
        String ownerToken = getTokenByRequest(storedData);
        Long cardId = getCardIdByRequest(storedData);
        try {
            return ResponseEntity.ok(cardFacade.findCardById(ownerToken, cardId));
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Invalid URI: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(503).body("Service Unavailable: " + e.getMessage());
        } catch (AccessException e){
            return ResponseEntity.status(403).body(e.getMessage());

        }
    }

    private String getTokenByRequest(Map<String, String> storedData) {
        return storedData.get("storedAuthToken");
    }

    private Long getCardIdByRequest(Map<String, String> storedData) {
        return Long.parseLong(storedData.get("id"));
    }
}
