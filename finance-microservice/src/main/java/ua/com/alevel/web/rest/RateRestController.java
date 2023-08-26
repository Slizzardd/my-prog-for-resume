package ua.com.alevel.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.alevel.facade.RateFacade;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/finance/rate")
public class RateRestController {

    private final RateFacade rateFacade;


    public RateRestController(RateFacade rateFacade) {
        this.rateFacade = rateFacade;
    }

    @GetMapping("/getActualRate")
    public ResponseEntity<?> getActualRate() {
        try {
            return ResponseEntity.ok(rateFacade.getCurrentExchangeRate());
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body("Invalid URI: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("e = " + e);
            return ResponseEntity.status(503).body("Service Unavailable: " + e.getMessage());
            
        }
    }
}
