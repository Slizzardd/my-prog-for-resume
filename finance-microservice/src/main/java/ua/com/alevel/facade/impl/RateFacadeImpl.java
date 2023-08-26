package ua.com.alevel.facade.impl;

import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import ua.com.alevel.facade.RateFacade;
import ua.com.alevel.service.RateService;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class RateFacadeImpl implements RateFacade {
    private final RateService rateService;

    public RateFacadeImpl(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public String getCurrentExchangeRate() throws URISyntaxException, IOException {
        return EntityUtils.toString(rateService.getCurrentExchangeRate());
    }
}
