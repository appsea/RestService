package com.exuberant.rest.survey.service;

import com.exuberant.rest.survey.model.JsonQuestion;

public interface FirebaseService {

    String version();

    void suggest(JsonQuestion jsonQuestion);

    String getUrl(String app);
}
