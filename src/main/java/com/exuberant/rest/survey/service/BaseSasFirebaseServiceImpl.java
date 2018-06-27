package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.model.JsonQuestion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BaseSasFirebaseServiceImpl implements FirebaseService {

    @Value("${firebase.base.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String version() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/version.json", String.class);
        return response.getBody();
    }

    @Override
    public void suggest(JsonQuestion jsonQuestion) {
        restTemplate.postForEntity(baseUrl+"/suggestion.json", jsonQuestion, JsonQuestion.class);
    }

    @Override
    public String getUrl(String app) {
        String url = baseUrl + "/url/" + app + ".json";
        log.info("Hitting URL: {}", url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
