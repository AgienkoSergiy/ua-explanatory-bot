package ua.org.skyrvr.uaexplanatorybot.connectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DictConnector {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${explanatory-service.sum-in-ua.url}")
    private String dictionaryServiceUrl;

    public String getWordExplanation(String enteredWord) {
        String serviceCallUrl = dictionaryServiceUrl + enteredWord;
        logger.info("Created service call: " + serviceCallUrl);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(serviceCallUrl, String.class);

        logger.info("Response got from " + serviceCallUrl + ": " + response);
        return response;
    }

}
