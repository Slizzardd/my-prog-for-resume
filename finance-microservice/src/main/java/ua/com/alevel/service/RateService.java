package ua.com.alevel.service;

import org.apache.http.HttpEntity;

import java.io.IOException;
import java.net.URISyntaxException;

public interface RateService {

    HttpEntity getCurrentExchangeRate() throws URISyntaxException, IOException;
}
