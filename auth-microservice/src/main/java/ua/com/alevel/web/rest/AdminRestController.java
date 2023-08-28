package ua.com.alevel.web.rest;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.exception.AccessException;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.util.ControllerUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class AdminRestController {

    private final UserFacade userFacade;

    public AdminRestController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String actualAuthToken) {
        if (ControllerUtil.authCheck(actualAuthToken)) {
            String token = ControllerUtil.getToken(actualAuthToken);
            try {
                return ResponseEntity.ok(userFacade.findAll(token));
            } catch (ExpiredJwtException e) {
                return ResponseEntity.status(401).body("User not authorized: " + e.getMessage());
            } catch (AccessException e) {
                return ResponseEntity.status(403).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity<?> getUserById(@RequestHeader("Authorization") String actualAuthToken,
                                         @RequestHeader("UserId") Long userId) {
        try {
            String token = ControllerUtil.getToken(actualAuthToken);
            return ResponseEntity.ok(userFacade.findById(userId, token));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (AccessException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

}
