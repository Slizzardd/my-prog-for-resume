package ua.com.alevel.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.CardFacade;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/admin/card")
public class AdminRestController {

    private final CardFacade cardFacade;

    public AdminRestController(CardFacade cardFacade) {
        this.cardFacade = cardFacade;
    }

    @GetMapping("/getCardsByUserId")
    public ResponseEntity<?> getCardsByUserId(@RequestHeader("Authorization") String actualAuthToken,
                                         @RequestHeader("UserId") Long userId) {
        try{
            return ResponseEntity.ok(cardFacade.findCardsByUserId(actualAuthToken, userId));
        } catch (IOException e) {
            return ResponseEntity.status(503).body("Service Unavailable: " + e.getMessage());
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Invalid URI: " + e.getMessage());
        } catch (AccessException e){
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
