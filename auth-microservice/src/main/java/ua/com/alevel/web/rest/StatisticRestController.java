package ua.com.alevel.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.UserFacade;

@RestController
@RequestMapping("/statistic/user")
public class StatisticRestController {

    private final UserFacade userFacade;


    public StatisticRestController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/getNumbersOfUsers")
    public ResponseEntity<?> getNumbersOfUsers(){
        return ResponseEntity.ok(userFacade.getNumbersOfUsers());
    }
}
