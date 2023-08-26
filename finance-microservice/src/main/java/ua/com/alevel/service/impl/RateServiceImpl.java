package ua.com.alevel.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import ua.com.alevel.service.RateService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RateServiceImpl implements RateService {

    private static final String URL_API_PRIVAT_BANK = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=11";
    @Override
    public HttpEntity getCurrentExchangeRate() throws URISyntaxException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpUriRequest httpget = RequestBuilder.get()
                .setUri(new URI(URL_API_PRIVAT_BANK))
                .build();

        CloseableHttpResponse response = httpclient.execute(httpget);
        return response.getEntity();
    }
}
