package ua.com.alevel.facade;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RateFacade {

    String getCurrentExchangeRate() throws URISyntaxException, IOException;
}
