package ua.com.alevel.web.rest;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.UserFacade;
import ua.com.alevel.util.ControllerUtil;

@RestController
@RequestMapping("/api/user")
public class APIController {

    private final UserFacade userFacade;

    public APIController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/getUserByToken")
    public ResponseEntity<?> getUserByToken(@RequestHeader("Authorization") String actualAuthToken) {
        if (ControllerUtil.authCheck(actualAuthToken)) {
            String token = ControllerUtil.getToken(actualAuthToken);
            try {
                System.out.println("userFacade = " + userFacade.apiFindUserByToken(token));
                return ResponseEntity.ok(userFacade.apiFindUserByToken(token));
            } catch (ExpiredJwtException e) {
                return ResponseEntity.status(401).body("User not authorized: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
